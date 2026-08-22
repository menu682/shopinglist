package pp.ua.shopinglist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pp.ua.shopinglist.service.AdminService;

@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminController {

    private final AdminService userService;

    public AdminController(AdminService userService) {
        this.userService = userService;
    }





    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            Authentication authentication) {

        userService.deleteUser(id, authentication);

        return ResponseEntity.noContent().build();
    }
}