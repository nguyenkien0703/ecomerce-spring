package com.ecommerce.shopapp.responses;

import com.ecommerce.shopapp.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse{
    private Long id;
    private String name;
    private Float price;
    private String thumbnail;
    private String description;
    @JsonProperty("category_id")
    private Long categoryId;

    public static ProductResponse fromProduct(Product product) {
//        List<Comment> comments = product.getComments()
//                .stream()
//                .sorted(Comparator.comparing(Comment::getCreatedAt).reversed()) // Sort comments by createdAt in descending order
//                .collect(Collectors.toList());
//        List<Favorite> favorites = product.getFavorites();
//        ProductResponse productResponse = ProductResponse.builder()
//                .id(product.getId())
//                .name(product.getName())
//                .price(product.getPrice())
//                .thumbnail(product.getThumbnail())
//                .comments(comments.stream().map(CommentResponse::fromComment).toList()) // Collect sorted comments into a list
//                .favorites(favorites.stream().map(FavoriteResponse::fromFavorite).toList())
//                .description(product.getDescription())
//                .categoryId(product.getCategory().getId())
//                .productImages(product.getProductImages())
//                .totalPages(0)
//                .build();
//        productResponse.setCreatedAt(product.getCreatedAt());
//        productResponse.setUpdatedAt(product.getUpdatedAt());
//        return productResponse;
        ProductResponse productResponse =  ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .build();

        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;

    }
}
