package pets.adoption.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        Map<String, Object> details = new HashMap<>();
        details.put("timestamp", LocalDateTime.now());
        details.put("message", ex.getMessage());
        details.put("details", request.getDescription(false));
        
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<?> handleDuplicateResourceException(
            DuplicateResourceException ex, WebRequest request) {
        Map<String, Object> details = new HashMap<>();
        details.put("timestamp", LocalDateTime.now());
        details.put("message", ex.getMessage());
        details.put("details", request.getDescription(false));
        
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(
            BadCredentialsException ex, WebRequest request) {
        Map<String, Object> details = new HashMap<>();
        details.put("timestamp", LocalDateTime.now());
        details.put("message", "Invalid username or password");
        details.put("details", request.getDescription(false));
        
        return new ResponseEntity<>(details, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request) {
        Map<String, Object> details = new HashMap<>();
        details.put("timestamp", LocalDateTime.now());
        details.put("message", "Access denied");
        details.put("details", request.getDescription(false));
        
        return new ResponseEntity<>(details, HttpStatus.FORBIDDEN);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(
            Exception ex, WebRequest request) {
        Map<String, Object> details = new HashMap<>();
        details.put("timestamp", LocalDateTime.now());
        details.put("message", ex.getMessage());
        details.put("details", request.getDescription(false));
        
        return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
