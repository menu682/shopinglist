package pp.ua.shopinglist.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pp.ua.shopinglist.dto.auth.UserResponce;
import pp.ua.shopinglist.exception.ResourceNotFoundException;
import pp.ua.shopinglist.persistence.security.entity.User;
import pp.ua.shopinglist.persistence.security.repository.UserRepository;
import pp.ua.shopinglist.security.CustomUserDetails;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponce getUser(Long id, Authentication authentication) throws ResourceNotFoundException {

        if (!(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new ResourceNotFoundException("User authentication not found");
        }

        if (!id.equals(userDetails.getId()) && !userDetails.isAdmin()) {
            throw new AccessDeniedException("Access denied");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found: " + id)
                );

        return new UserResponce(
                user.getId(),
                user.getUsername(),
                user.getRole().name()
        );
    }
}
