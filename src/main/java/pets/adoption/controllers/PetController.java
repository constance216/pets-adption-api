package pets.adoption.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pets.adoption.models.Pet;
import pets.adoption.services.PetService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {
    
    private final PetService petService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SHELTER')")
    public ResponseEntity<Pet> createPet(@Valid @RequestBody Pet pet) {
        Pet createdPet = petService.createPet(pet);
        return ResponseEntity.ok(createdPet);
    }
    
    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> pets = petService.getAllPets();
        return ResponseEntity.ok(pets);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        Pet pet = petService.getPetById(id);
        return ResponseEntity.ok(pet);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Pet>> getPetsByStatus(@PathVariable String status) {
        List<Pet> pets = petService.getPetsByStatus(status);
        return ResponseEntity.ok(pets);
    }
    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Pet>> getPetsByCategory(@PathVariable Long categoryId) {
        List<Pet> pets = petService.getPetsByCategory(categoryId);
        return ResponseEntity.ok(pets);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SHELTER')")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @Valid @RequestBody Pet petDetails) {
        Pet updatedPet = petService.updatePet(id, petDetails);
        return ResponseEntity.ok(updatedPet);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{petId}/shelter/{shelterId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SHELTER')")
    public ResponseEntity<Pet> assignShelter(@PathVariable Long petId, @PathVariable Long shelterId) {
        Pet pet = petService.assignShelter(petId, shelterId);
        return ResponseEntity.ok(pet);
    }
    
    @PostMapping("/{petId}/veterinarian/{veterinarianId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIAN')")
    public ResponseEntity<Pet> assignVeterinarian(@PathVariable Long petId, @PathVariable Long veterinarianId) {
        Pet pet = petService.assignVeterinarian(petId, veterinarianId);
        return ResponseEntity.ok(pet);
    }
    
    @PostMapping("/{petId}/adopt/{adopterId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SHELTER')")
    public ResponseEntity<Pet> adoptPet(@PathVariable Long petId, @PathVariable Long adopterId) {
        Pet pet = petService.adoptPet(petId, adopterId);
        return ResponseEntity.ok(pet);
    }
}