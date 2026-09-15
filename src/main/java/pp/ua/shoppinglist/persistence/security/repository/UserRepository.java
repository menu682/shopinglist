package pp.ua.shoppinglist.persistence.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pp.ua.shoppinglist.persistence.security.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}