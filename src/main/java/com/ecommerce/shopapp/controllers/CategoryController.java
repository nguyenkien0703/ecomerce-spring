package com.ecommerce.shopapp.controllers;

import com.ecommerce.shopapp.dtos.request.CategoryDTO;
import com.ecommerce.shopapp.entity.Category;
import com.ecommerce.shopapp.responses.ResponseObject;
import com.ecommerce.shopapp.services.category.CategoryService;
import com.ecommerce.shopapp.utils.LocalizationUtils;
import com.ecommerce.shopapp.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
//@Validated// for @Valid use of method
public class CategoryController {

    private final CategoryService categoryService;
    private final LocalizationUtils localizationUtils;
//    private final KafkaTemplate<String, Object> kafkaTemplate;


    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    //Nếu tham số truyền vào là 1 object thì sao ? => Data Transfer Object = Request Object
    public ResponseEntity<ResponseObject> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) {
            List<String> errorMessages =  bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.ok().body(ResponseObject.builder()
                            .message(errorMessages.toString())
                            .status(HttpStatus.BAD_REQUEST)
                            .data(null)
                    .build());
        }
        Category category = categoryService.createCategory(categoryDTO);
//        this.kafkaTemplate.send("insert-a-category", category);
//        this.kafkaTemplate.setMessageConverter(new CategoryMessageConverter());
        return ResponseEntity.ok().body(ResponseObject.builder()
                        .message("Category created")
                        .status(HttpStatus.OK)
                        .data(category)
                .build());

    }

    //Hiện tất cả các categories
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit

    ) {

        List<Category> categories = categoryService.getAllCategories();
//        this.kafkaTemplate.send("get-all-categories", categories);

        return ResponseEntity.ok(ResponseObject.builder()
                .message("Get list of categories successfully")
                .status(HttpStatus.OK)
                .data(categories)
                .build());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCategoryById(
            @PathVariable("id") Long categoryId
    ) {

        Category existingCategory = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(ResponseObject.builder()
                .data(existingCategory)
                .message("Get category information successfully")
                .status(HttpStatus.OK)
                .build());

    }



    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO
    ) {
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(ResponseObject
                .builder()
                .data(categoryService.getCategoryById(id))
                .message(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_CATEGORY_SUCCESSFULLY))
                .build());

    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> deleteCategory(@PathVariable Long id) throws Exception{
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .message("Delete category successfully")
                        .build());
    }


}
