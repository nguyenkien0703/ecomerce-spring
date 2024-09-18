package com.ecommerce.shopapp.controllers;

import com.ecommerce.shopapp.dtos.request.CategoryDTO;
import com.ecommerce.shopapp.entity.Category;
import com.ecommerce.shopapp.services.impl.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("")
    public ResponseEntity<?> insertCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) {
            List<String> errorMessage =  bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("insert category");
    }






    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit

    ) {

        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO
    ) {
        categoryService.updateCategory(id, categoryDTO);
//        return ResponseEntity.ok(ResponseObject
//                .builder()
//                .data(categoryService.getCategoryById(id))
//                .message(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_CATEGORY_SUCCESSFULLY))
//                .build());
        return ResponseEntity.ok("update category");

    }
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) throws Exception{
        categoryService.deleteCategory(id);
//        return ResponseEntity.ok(
//                ResponseObject.builder()
//                        .status(HttpStatus.OK)
//                        .message("Delete category successfully")
//                        .build());
        return ResponseEntity.ok("delete category");
    }


}
