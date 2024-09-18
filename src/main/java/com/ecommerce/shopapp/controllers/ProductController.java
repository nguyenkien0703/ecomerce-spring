//package com.ecommerce.shopapp.controllers;
//
//import com.ecommerce.shopapp.dtos.request.ProductDTO;
//import com.ecommerce.shopapp.dtos.request.ProductImageDTO;
//import com.ecommerce.shopapp.entity.Product;
//import com.ecommerce.shopapp.entity.ProductImage;
//import com.ecommerce.shopapp.responses.ProductResponse;
//import com.ecommerce.shopapp.responses.ResponseObject;
//import com.ecommerce.shopapp.services.IProductService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.github.javafaker.Faker;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("${api.prefix}/products")
//@RequiredArgsConstructor
//public class ProductController {
//    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
//
//    private final IProductService productService;
//
//
//
//
//    @GetMapping("")
//    public ResponseEntity<ResponseObject> getProducts(
//            @RequestParam(defaultValue = "") String keyword,
//            @RequestParam(defaultValue = "0", name = "category_id") Long categoryId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int limit
//    ) throws JsonProcessingException {
//        int totalPages = 0;
//        //productRedisService.clear();
//        // Tạo Pageable từ thông tin trang và giới hạn
//        PageRequest pageRequest = PageRequest.of(
//                page, limit,
//                //Sort.by("createdAt").descending()
//                Sort.by("id").ascending()
//        );
//        logger.info(String.format("keyword = %s, category_id = %d, page = %d, limit = %d",
//                keyword, categoryId, page, limit));
//        List<ProductResponse> productResponses = productRedisService
//                .getAllProducts(keyword, categoryId, pageRequest);
//
//        if (productResponses!=null && !productResponses.isEmpty()) {
//            totalPages = productResponses.get(0).getTotalPages();
//        }
//        if(productResponses == null) {
//            Page<ProductResponse> productPage = productService
//                    .getAllProducts(keyword, categoryId, pageRequest);
//            // Lấy tổng số trang
//            totalPages = productPage.getTotalPages();
//            productResponses = productPage.getContent();
//            // Bổ sung totalPages vào các đối tượng ProductResponse
//            for (ProductResponse product : productResponses) {
//                product.setTotalPages(totalPages);
//            }
//            productRedisService.saveAllProducts(
//                    productResponses,
//                    keyword,
//                    categoryId,
//                    pageRequest
//            );
//        }
//        ProductListResponse productListResponse = ProductListResponse
//                .builder()
//                .products(productResponses)
//                .totalPages(totalPages)
//                .build();
//        return ResponseEntity.ok().body(ResponseObject.builder()
//                .message("Get products successfully")
//                .status(HttpStatus.OK)
//                .data(productListResponse)
//                .build());
//    }
//
//
//
////    private String storeFile(MultipartFile file) throws IOException {
////        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
////        // them uuid vao trc ten file de dam bao ten file la duy nhat
////        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
////        // duong dan den thu muc ma ban muon luu file
////        java.nio.file.Path uploadDir = Paths.get("uploads");
////        // kiem tra va tao thu muc neu no khong ton tai
////        if(!Files.exists(uploadDir)) {
////            Files.createDirectory(uploadDir);
////        }
////        // duong dan day du den file
////        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
////        // sao chep file vao thu muc dich
////        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
////        return uniqueFileName;
////
////
////    }
//
//
//
//    @PostMapping(value = "")// consumes la up anh
//    public ResponseEntity<?> createProduct(
//            @Valid @RequestBody ProductDTO productDTO,
////            @RequestPart("file") MultipartFile file,
//            BindingResult bindingResult
//    ) throws IOException {
//        try{
//            if(bindingResult.hasErrors()) {
//                List<String> errorMessage =  bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
//                return ResponseEntity.badRequest().body(errorMessage);
//            }
//            Product newProduct = productService.createProduct(productDTO);
//
//            return ResponseEntity.ok(newProduct);
//        }catch (Exception e ){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//
//
//
//
//    }
//
//
//
//    @PostMapping(value = "uploads/{id}",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
////    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    //POST http://localhost:8088/v1/api/products
//    public ResponseEntity<ResponseObject> uploadImages(
//            @PathVariable("id") Long productId,
//            @ModelAttribute("files") List<MultipartFile> files
//    ) throws Exception {
//        Product existingProduct = productService.getProductById(productId);
//        files = files == null ? new ArrayList<MultipartFile>() : files;
//        if(files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
//            return ResponseEntity.badRequest().body(
//                    ResponseObject.builder()
//                            .message(localizationUtils
//                                    .getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_MAX_5))
//                            .build()
//            );
//        }
//        List<ProductImage> productImages = new ArrayList<>();
//        for (MultipartFile file : files) {
//            if(file.getSize() == 0) {
//                continue;
//            }
//            // Kiểm tra kích thước file và định dạng
//            if(file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
//                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
//                        .body(ResponseObject.builder()
//                                .message(localizationUtils
//                                        .getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_FILE_LARGE))
//                                .status(HttpStatus.PAYLOAD_TOO_LARGE)
//                                .build());
//            }
//            String contentType = file.getContentType();
//            if(contentType == null || !contentType.startsWith("image/")) {
//                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//                        .body(ResponseObject.builder()
//                                .message(localizationUtils
//                                        .getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_FILE_MUST_BE_IMAGE))
//                                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//                                .build());
//            }
//            // Lưu file và cập nhật thumbnail trong DTO
//            String filename = FileUtils.storeFile(file);
//            //lưu vào đối tượng product trong DB
//            ProductImage productImage = productService.createProductImage(
//                    existingProduct.getId(),
//                    ProductImageDTO.builder()
//                            .imageUrl(filename)
//                            .build()
//            );
//            productImages.add(productImage);
//        }
//
//        return ResponseEntity.ok().body(ResponseObject.builder()
//                .message("Upload image successfully")
//                .status(HttpStatus.CREATED)
//                .data(productImages)
//                .build());
//    }
//
//
//
//    //@PostMapping("/generateFakeProducts")
//    private ResponseEntity<ResponseObject> generateFakeProducts() throws Exception {
//        Faker faker = new Faker();
//        for (int i = 0; i < 1_000_000; i++) {
//            String productName = faker.commerce().productName();
//            if(productService.existsByName(productName)) {
//                continue;
//            }
//            ProductDTO productDTO = ProductDTO.builder()
//                    .name(productName)
//                    .price((float)faker.number().numberBetween(10, 90_000_000))
//                    .description(faker.lorem().sentence())
//                    .thumbnail("")
//                    .categoryId((long)faker.number().numberBetween(2, 5))
//                    .build();
//            productService.createProduct(productDTO);
//        }
//        return ResponseEntity.ok(ResponseObject.builder()
//                .message("Insert fake products succcessfully")
//                .data(null)
//                .status(HttpStatus.OK)
//                .build());
//    }
//
//
//    //http://localhost:8088/api/v1/products/6
//    @GetMapping("/{id}")
//    public ResponseEntity<ResponseObject> getProductById(
//            @PathVariable("id") Long productId
//    ) throws Exception {
//        Product existingProduct = productService.getProductById(productId);
//        return ResponseEntity.ok(ResponseObject.builder()
//                .data(ProductResponse.fromProduct(existingProduct))
//                .message("Get detail product successfully")
//                .status(HttpStatus.OK)
//                .build());
//
//    }
//
//
//
//    @DeleteMapping("/{id}")
////    @PreAuthorize("hasRole('ROLE_ADMIN')")
////    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
//    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable long id) {
//        productService.deleteProduct(id);
//        return ResponseEntity.ok(ResponseObject.builder()
//                .data(null)
//                .message(String.format("Product with id = %d deleted successfully", id))
//                .status(HttpStatus.OK)
//                .build());
//    }
//
//
//
//}
