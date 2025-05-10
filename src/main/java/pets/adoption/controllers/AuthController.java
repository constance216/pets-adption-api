package pets.adoption.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pets.adoption.models.User;
import pets.adoption.services.UserService;
import pets.adoption.dto.LoginRequest;
import pets.adoption.dto.SignupRequest;
import pets.adoption.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // In production, you would generate a JWT token here
        // For simplicity, we're returning a success message
        return ResponseEntity.ok(new MessageResponse("User authenticated successfully!"));
    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        user.setFullName(signupRequest.getFullName());
        user.setRole(signupRequest.getRole() != null ? signupRequest.getRole() : "USER");
        
        User createdUser = userService.createUser(user);
        
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}

// DTOs
class LoginRequest {
    private String username;
    private String password;
    
    // getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

class SignupRequest {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String role;
    
    // getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

class MessageResponse {
    private String message;
    
    public MessageResponse(String message) {
        this.message = message;
    }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
