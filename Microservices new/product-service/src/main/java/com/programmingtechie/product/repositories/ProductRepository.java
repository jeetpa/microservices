package com.programmingtechie.product.repositories;

import com.programmingtechie.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends MongoRepository<Product,String>,CrudRepository<Product,String> {
}
