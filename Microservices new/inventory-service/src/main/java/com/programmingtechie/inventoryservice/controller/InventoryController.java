package com.programmingtechie.inventoryservice.controller;

import com.programmingtechie.inventoryservice.dto.InventoryResponse;
import com.programmingtechie.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

   //  http://localhost:8082/api/inventory?skucode=iphone-13&skucode=iphone13-red
    private final InventoryService inventoryService;
    @GetMapping
    public List<InventoryResponse> inStock(@RequestParam List<String> skuCode){
        return inventoryService.inStock(skuCode);
    }
}
