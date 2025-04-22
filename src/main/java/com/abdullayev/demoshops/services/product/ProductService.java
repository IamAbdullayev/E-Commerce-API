package com.abdullayev.demoshops.services.product;

import com.abdullayev.demoshops.dto.ImageDto;
import com.abdullayev.demoshops.dto.ProductDto;
import com.abdullayev.demoshops.exceptions.ProductNotFoundException;
import com.abdullayev.demoshops.models.Category;
import com.abdullayev.demoshops.models.Image;
import com.abdullayev.demoshops.models.Product;
import com.abdullayev.demoshops.repositories.ImageRepository;
import com.abdullayev.demoshops.repositories.ProductRepository;
import com.abdullayev.demoshops.requests.category.AddCategoryRequest;
import com.abdullayev.demoshops.requests.product.AddProductRequest;
import com.abdullayev.demoshops.requests.product.UpdateProductRequest;
import com.abdullayev.demoshops.services.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final ICategoryService categoryService;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        // Check if the category found in the DB
        // If Yes, set it us the new product category
        // If No, the save it as a new product
        // The set as the new product category

        Category category = Optional.ofNullable(categoryService.getCategoryByName(request.getCategory().getName()))
               .orElseGet(()-> categoryService.addCategory(new AddCategoryRequest(request.getCategory().getName())));
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));

//        Category category = categoryService.getCategoryByName(request.getCategory().getName());
//        if (category == null) {
//            category = categoryService.addCategory(new AddCategoryRequest(request.getCategory().getName()));
//        }
//        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
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
    public Product updateProduct(UpdateProductRequest request, Long productId) {
        // СПОСОБ №1
//        Product existingProduct = productRepository.findById(productId)
//                .orElseThrow(()-> new ProductNotFoundException("Product not found!"));
//        return productRepository.save(updateExistingProduct(existingProduct, request));

        // СПОСОБ №2
        return Optional.ofNullable(getProductById(productId))
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository :: save)
                .orElseThrow(()-> new ProductNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
        if (request.getName() != null) existingProduct.setName(request.getName());
        if (request.getBrand() != null) existingProduct.setBrand(request.getBrand());
        if (request.getPrice() != null) existingProduct.setPrice(request.getPrice());
        if (request.getInventory() != null) existingProduct.setInventory(request.getInventory());
        if (request.getDescription() != null) existingProduct.setDescription(request.getDescription());

        if (request.getCategory() != null && request.getCategory().getName() != null) {
            Category category = categoryService.getCategoryByName(request.getCategory().getName().trim());
            existingProduct.setCategory(category);
        }

        return existingProduct;
    }

    @Override
    public void deleteProductById(Long id) {
//        СТАРЫЙ СПОСОБ
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
//        productRepository.delete(product);

        // Более современный способ
        productRepository.findById(id).ifPresentOrElse(
                productRepository::delete,
                ()-> { throw new ProductNotFoundException("Product not found!"); }
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found!"));
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
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        // METHOD 1
//        List<ProductDto> productDtoList = new ArrayList<>();
//        for (Product product : products) {
//            ProductDto productDto = convertToDto(product);
//            productDtoList.add(productDto);
//        }
//        return productDtoList;

        // METHOD 2
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProduct_Id(product.getId());
        List<ImageDto> imageDtoList = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtoList);
        return productDto;
    }
}
