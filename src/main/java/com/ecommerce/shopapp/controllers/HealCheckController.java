package com.ecommerce.shopapp.controllers;

import com.ecommerce.shopapp.entity.Category;
import com.ecommerce.shopapp.responses.ResponseObject;
import com.ecommerce.shopapp.services.category.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/healthcheck")
@AllArgsConstructor

public class HealCheckController {
    private final CategoryService categoryService;

    @GetMapping("/health")
    public ResponseEntity<ResponseObject> healthCheck() throws Exception{
        List<Category> categories = categoryService.getAllCategories();
        // Get the computer name

        String computerName = InetAddress.getLocalHost().getHostName();
        return ResponseEntity.ok(ResponseObject
                .builder()
                .message("ok, Computer Name: " + computerName)
                .status(HttpStatus.OK)
                .build());

    }


}
