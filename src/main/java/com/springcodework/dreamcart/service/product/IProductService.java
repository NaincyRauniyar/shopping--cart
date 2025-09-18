package com.springcodework.dreamcart.service.product;

import com.springcodework.dreamcart.dto.ProductDto;
import com.springcodework.dreamcart.model.Product;
import com.springcodework.dreamcart.request.AddProductRequest;
import com.springcodework.dreamcart.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest product);

    Product getProductById (Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product,Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category,String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand,String name);
    Long countProductsByBrandAndName(String brand,String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
