package pp.ua.shopinglist.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pp.ua.shopinglist.exception.OperationNotAllowedException;
import pp.ua.shopinglist.exception.ResourceNotFoundException;
import pp.ua.shopinglist.persistence.security.entity.Role;
import pp.ua.shopinglist.persistence.security.entity.User;
import pp.ua.shopinglist.persistence.security.repository.UserRepository;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public void deleteUser(Long id, Authentication authentication) {

        User userToDelete = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found: " + id
                        )
                );

        String currentUsername = authentication.getName();

        if (userToDelete.getUsername().equals(currentUsername)) {
            throw new OperationNotAllowedException(
                    "You cannot delete yourself"
            );
        }

        if (userToDelete.getRole() == Role.ADMIN) {
            throw new OperationNotAllowedException(
                    "You cannot delete another administrator"
            );
        }

        userRepository.delete(userToDelete);
    }
}