package pp.ua.shoppinglist.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pp.ua.shoppinglist.dto.auth.UserResponce;
import pp.ua.shoppinglist.service.UserService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponce> getUser(@PathVariable Long id, Authentication authentication) {

        UserResponce userResponce = userService.getUser(id, authentication);

        return ResponseEntity.ok(userResponce);
    }

}
