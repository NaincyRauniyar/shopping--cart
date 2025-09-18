package com.springcodework.dreamcart.service.product;
import com.springcodework.dreamcart.dto.ImageDto;
import com.springcodework.dreamcart.dto.ProductDto;
import com.springcodework.dreamcart.model.Image;
import com.springcodework.dreamcart.repository.CategoryRepository;
import com.springcodework.dreamcart.repository.ImageRepository;
import com.springcodework.dreamcart.repository.ProductRepository;
import com.springcodework.dreamcart.exceptions.ProductNotFoundException;
import com.springcodework.dreamcart.model.Product;
import com.springcodework.dreamcart.request.AddProductRequest;
import com.springcodework.dreamcart.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.springcodework.dreamcart.model.Category;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;
    @Override
    public Product addProduct(AddProductRequest request) {
        //check if category is found in db:y then set it as new product category if n then save it
        Category category = Optional.ofNullable(
                categoryRepository.findByName(request.getCategory().getName())
        ).orElseGet(() -> {
            Category newCategory = new Category(request.getCategory().getName());
            return categoryRepository.save(newCategory);
        });

       request.setCategory(category);
       return productRepository.save(createProduct(request,category));

    }

    private Product createProduct(AddProductRequest request,Category category){
    return new Product(
            request.getName(),
            request.getBrand(),
            request.getPrice(),
            request.getInventory(),
            request.getDescription(),
            category
    );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                () -> {throw new ProductNotFoundException("product not found");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(()-> new ProductNotFoundException("product not found!"));
    }
    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory (category);
        return existingProduct;

    }

    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        try {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            List<Image> images = Optional.ofNullable(imageRepository.findByProductId(product.getId()))
                    .orElse(Collections.emptyList());

            List<ImageDto> imageDtos = images.stream()
                    .map(image -> modelMapper.map(image, ImageDto.class))
                    .toList();
            productDto.setImages(imageDtos);
            return productDto;
        } catch (Exception e) {
            System.err.println("Error converting product to DTO: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to convert product to DTO");
        }
    }



}
