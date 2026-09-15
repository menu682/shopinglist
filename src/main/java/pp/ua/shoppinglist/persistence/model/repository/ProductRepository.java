package pp.ua.shoppinglist.persistence.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pp.ua.shoppinglist.persistence.model.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByNameIgnoreCase(String productName);

    List<Product> findByNameContainingIgnoreCase(String productName);

}
