package pp.ua.shoppinglist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pp.ua.shoppinglist.dto.core.ProductDto;
import pp.ua.shoppinglist.exception.ResourceAlreadyExistsException;
import pp.ua.shoppinglist.exception.ResourceNotFoundException;
import pp.ua.shoppinglist.persistence.model.entity.Product;
import pp.ua.shoppinglist.persistence.model.repository.ProductRepository;

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
                product.getName()
                );
    }

    public ProductDto getProductByProductName(String productName) {

        Product product = productRepository.findByNameIgnoreCase(productName)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found: " + productName)
                );

        return new ProductDto(
                product.getId(),
                product.getName());
    }

    public List<ProductDto> getAllProductsByProductNameContainingIgnoreCase(String productName) {

        List<Product> products = productRepository.findByNameContainingIgnoreCase(productName);

        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            productDtos.add(new ProductDto(
                    product.getId(),
                    product.getName()
                    )
            );
        }

        return productDtos;
    }


    public ProductDto createProduct(ProductDto productDto) {

        if (productRepository.findByNameIgnoreCase(productDto.name()).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    "Product already exists: " + productDto.name()
            );
        }

        Product product = Product.builder()
                .name(productDto.name())
                .build();

        Product savedProduct = productRepository.save(product);

        return new ProductDto(
                savedProduct.getId(),
                savedProduct.getName()
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

        product.setName(productDto.name());

        Product updatedProduct = productRepository.save(product);

        return new ProductDto(
                updatedProduct.getId(),
                updatedProduct.getName());
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
