package com.programmingtechie.orderservice.service;

import com.programmingtechie.orderservice.dto.InventoryResponse;
import com.programmingtechie.orderservice.dto.OrderLineItemsDto;
import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderLineItems;
import com.programmingtechie.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
       List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsList().stream().map(this::mapToDto).toList();
       order.setOrderLineItemsList(orderLineItemsList);
      List<String> skuCodes = order.getOrderLineItemsList().stream().map(orderLineItems -> orderLineItems.getSkuCode()).toList();
          //call inventory service and place order if product in stock
          InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                  .uri("http://inventory-service/api/inventory",
                          uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                  .retrieve()
                  .bodyToMono(InventoryResponse[].class)
                  .block();

        Boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(inventoryResponse -> inventoryResponse.getIsInStock());
     if(allProductsInStock) {
          orderRepository.save(order);
      }else {
          throw new IllegalArgumentException("product not in stock");
      }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
