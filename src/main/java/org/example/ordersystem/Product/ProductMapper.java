package org.example.ordersystem.Product;

public class ProductMapper {

    public static ProductDetailDTO toProductDetailDTO(Product product) {
        return new ProductDetailDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getProvider()
        );
    }
}
