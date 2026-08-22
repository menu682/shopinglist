package pp.ua.shopinglist.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pp.ua.shopinglist.dto.auth.UserResponce;
import pp.ua.shopinglist.exception.ResourceNotFoundException;
import pp.ua.shopinglist.persistence.security.entity.User;
import pp.ua.shopinglist.persistence.security.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponce getUser(Long id, Authentication authentication) throws ResourceNotFoundException {

        if(id === authentication.getName())

        User user = userRepository.findById(id).orElseThrow();

        return new UserResponce(user.getId(), user.getUsername(), user.getRole().toString());

    }


}
