package com.abdullayev.demoshops.services.product;

import com.abdullayev.demoshops.dto.ProductDto;
import com.abdullayev.demoshops.models.Product;
import com.abdullayev.demoshops.requests.product.AddProductRequest;
import com.abdullayev.demoshops.requests.product.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    Product updateProduct(UpdateProductRequest product, Long id);
    void deleteProductById(Long id);

    List<Product> getAllProducts();
    Product getProductById(Long productId);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
