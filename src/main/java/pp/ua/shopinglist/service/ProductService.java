package pp.ua.shopinglist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pp.ua.shopinglist.dto.core.ProductDto;
import pp.ua.shopinglist.exception.ResourceNotFoundException;
import pp.ua.shopinglist.persistence.model.entity.Product;
import pp.ua.shopinglist.persistence.model.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found: " + id)
                );

        return new ProductDto(
                product.getId(),
                product.getProductName()
                );
    }

    public ProductDto getProductByProductName(String productName) {

        Product product = productRepository.findByProductName(productName)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found: " + productName)
                );

        return new ProductDto(
                product.getId(),
                product.getProductName());
    }

    public List<ProductDto> getAllProductsByProductNameContainingIgnoreCase(String productName) {

        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(productName);

        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            productDtos.add(new ProductDto(
                    product.getId(),
                    product.getProductName()
                    )
            );
        }

        return productDtos;
    }


    public ProductDto createProduct(ProductDto productDto) {

        Product product = Product.builder()
                .productName(productDto.name())
                .build();

        Product savedProduct = productRepository.save(product);

        return new ProductDto(
                savedProduct.getId(),
                savedProduct.getProductName()
        );
    }

    public ProductDto updateProduct(ProductDto productDto) {

        if(productDto.id() == null) {
            throw new IllegalArgumentException("Product id is required");
        }

        Product product = productRepository.findById(productDto.id())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found: " + productDto.name())
        );

//TODO зробити перевірку перед зміною

//        if (shoppingListItemRepository.existsByProductId(productDto.id())) {
//            throw new IllegalStateException(
//                    "Product is already used in shopping lists and cannot be modified"
//            );
//        }

        product.setProductName(productDto.name());

        Product updatedProduct = productRepository.save(product);

        return new ProductDto(
                updatedProduct.getId(),
                updatedProduct.getProductName());
    }

    public boolean deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product with id " + id + " not found"
                ));

//TODO зробити перевірку перед видаленням

//        if (shoppingListItemRepository.existsByProductId(productDto.id())) {
//            throw new IllegalStateException(
//                    "Product is already used in shopping lists and cannot be deleted"
//            );
//        }

        productRepository.delete(product);
        return true;
    }

}
