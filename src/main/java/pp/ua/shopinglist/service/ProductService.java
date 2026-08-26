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
                product.getProductName(),
                product.getUnit(),
                product.getLastPrice()
                );
    }

    public ProductDto getProductByProductName(String productName) {

        Product product = productRepository.findByProductName(productName)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found: " + productName)
                );

        return new ProductDto(
                product.getId(),
                product.getProductName(),
                product.getUnit(),
                product.getLastPrice());
    }

    public List<ProductDto> getAllProductsByProductNameContainingIgnoreCase(String productName) {

        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(productName);

        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            productDtos.add(new ProductDto(
                    product.getId(),
                    product.getProductName(),
                    product.getUnit(),
                    product.getLastPrice()));
        }

        return productDtos;
    }


    public ProductDto createProduct(ProductDto productDto) {

        Product product = Product.builder()
                .productName(productDto.name())
                .unit(productDto.unit())
                .lastPrice(productDto.lastPrice())
                .build();

        Product savedProduct = productRepository.save(product);

        return new ProductDto(
                savedProduct.getId(),
                savedProduct.getProductName(),
                savedProduct.getUnit(),
                savedProduct.getLastPrice()
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

        product.setProductName(productDto.name());
        product.setUnit(productDto.unit());
        product.setLastPrice(productDto.lastPrice());

        Product updatedProduct = productRepository.save(product);

        return new ProductDto(
                updatedProduct.getId(),
                updatedProduct.getProductName(),
                updatedProduct.getUnit(),
                updatedProduct.getLastPrice());
    }

    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product with id " + id + " not found"
                ));

        productRepository.delete(product);

    }

}
