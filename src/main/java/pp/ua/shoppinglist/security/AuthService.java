package pp.ua.shoppinglist.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pp.ua.shoppinglist.dto.auth.AuthResponse;
import pp.ua.shoppinglist.dto.auth.LoginRequest;
import pp.ua.shoppinglist.dto.auth.RegisterRequest;
import pp.ua.shoppinglist.persistence.security.entity.Role;
import pp.ua.shoppinglist.persistence.security.entity.User;
import pp.ua.shoppinglist.persistence.security.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest request) {

        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException(
                    "Username already exists"
            );
        }

        User user = new User(
                null,
                request.username(),
                passwordEncoder.encode(request.password()),
                Role.USER
        );

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        if (!(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new IllegalStateException(
                    "Unexpected authentication principal type"
            );
        }

        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }
}