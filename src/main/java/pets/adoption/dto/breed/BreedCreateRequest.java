package pets.adoption.dto.breed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BreedCreateRequest {
    @NotBlank(message = "Breed name is required")
    private String name;
    
    @NotNull(message = "Category ID is required")
    private Long categoryId;
    
    private String description;
}
