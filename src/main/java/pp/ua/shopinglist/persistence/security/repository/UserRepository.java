package pp.ua.shopinglist.persistence.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pp.ua.shopinglist.persistence.security.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}