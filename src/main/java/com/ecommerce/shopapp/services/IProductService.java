package com.ecommerce.shopapp.services;

import com.ecommerce.shopapp.dtos.request.ProductDTO;
import com.ecommerce.shopapp.dtos.request.ProductImageDTO;
import com.ecommerce.shopapp.entity.Product;
import com.ecommerce.shopapp.entity.ProductImage;
import com.ecommerce.shopapp.responses.ProductResponse;
import org.springframework.data.domain.*;
public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;
    Product getProductById(long id) throws Exception;
    Page<ProductResponse> getAllProducts(
//            String keyword,
//                                         Long categoryId,
                                 PageRequest pageRequest);
    Product updateProduct(long id, ProductDTO productDTO) throws Exception;
    void deleteProduct(long id);
    boolean existsByName(String name);
    ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception;

//    List<Product> findProductsByIds(List<Long> productIds);
//    //String storeFile(MultipartFile file) throws IOException; //chuyển sang FileUtils
//    //void deleteFile(String filename) throws IOException;
//
//    Product likeProduct(Long userId, Long productId) throws Exception;
//    Product unlikeProduct(Long userId, Long productId) throws Exception;
//    List<ProductResponse> findFavoriteProductsByUserId(Long userId) throws Exception;
//    void generateFakeLikes() throws Exception;
}
