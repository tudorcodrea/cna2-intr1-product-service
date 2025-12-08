package com.example.productservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final SnsService snsService;

    @Autowired
    public ProductController(SnsService snsService) {
        this.snsService = snsService;
    }

    @PostMapping
    public String createProduct(@RequestBody Product product) {
        Product newProduct = new Product(product.getName(), product.getDescription());
        // snsService.publishProductEvent(newProduct);
        return "Product creation event published for " + newProduct.getName();
    }
}
