package pets.adoption.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pets.adoption.models.Breed;
import pets.adoption.services.BreedService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/breeds")
@RequiredArgsConstructor
public class BreedController {
    
    private final BreedService breedService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Breed> createBreed(@Valid @RequestBody Breed breed) {
        Breed createdBreed = breedService.createBreed(breed);
        return ResponseEntity.ok(createdBreed);
    }
    
    @GetMapping
    public ResponseEntity<List<Breed>> getAllBreeds() {
        List<Breed> breeds = breedService.getAllBreeds();
        return ResponseEntity.ok(breeds);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Breed> getBreedById(@PathVariable Long id) {
        Breed breed = breedService.getBreedById(id);
        return ResponseEntity.ok(breed);
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<Breed> getBreedByName(@PathVariable String name) {
        Breed breed = breedService.getBreedByName(name);
        return ResponseEntity.ok(breed);
    }
    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Breed>> getBreedsByCategory(@PathVariable Long categoryId) {
        List<Breed> breeds = breedService.getBreedsByCategory(categoryId);
        return ResponseEntity.ok(breeds);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Breed> updateBreed(@PathVariable Long id, @Valid @RequestBody Breed breedDetails) {
        Breed updatedBreed = breedService.updateBreed(id, breedDetails);
        return ResponseEntity.ok(updatedBreed);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBreed(@PathVariable Long id) {
        breedService.deleteBreed(id);
        return ResponseEntity.noContent().build();
    }
}