package pp.ua.shopinglist.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pp.ua.shopinglist.dto.auth.UserResponce;
import pp.ua.shopinglist.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;


    @GetMapping("/{id}")
    public UserResponce getUser(Long id, Authentication authentication) {
        return userService.getUser(id, authentication);
    }

}
