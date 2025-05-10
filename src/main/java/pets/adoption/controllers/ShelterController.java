package pets.adoption.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pets.adoption.dto.pet.PetResponse;
import pets.adoption.dto.user.UserResponse;
import pets.adoption.models.Pet;
import pets.adoption.models.User;
import pets.adoption.services.ShelterService;
import pets.adoption.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shelters")
@RequiredArgsConstructor
public class ShelterController {
    
    private final ShelterService shelterService;
    private final MapperUtil mapperUtil;
    
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllShelters() {
        List<User> shelters = shelterService.getAllShelters();
        List<UserResponse> responses = shelters.stream()
            .map(shelter -> mapperUtil.map(shelter, UserResponse.class))
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getShelterById(@PathVariable Long id) {
        User shelter = shelterService.getShelterById(id);
        return ResponseEntity.ok(mapperUtil.map(shelter, UserResponse.class));
    }
    
    @GetMapping("/{id}/pets")
    public ResponseEntity<List<PetResponse>> getPetsByShelter(@PathVariable Long id) {
        List<Pet> pets = shelterService.getPetsByShelter(id);
        List<PetResponse> responses = pets.stream()
            .map(pet -> mapperUtil.map(pet, PetResponse.class))
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> createShelter(@Valid @RequestBody User shelterData) {
        User createdShelter = shelterService.createShelter(shelterData);
        return ResponseEntity.ok(mapperUtil.map(createdShelter, UserResponse.class));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('SHELTER') and #id == authentication.principal.id)")
    public ResponseEntity<UserResponse> updateShelter(@PathVariable Long id, @Valid @RequestBody User shelterDetails) {
        User updatedShelter = shelterService.updateShelter(id, shelterDetails);
        return ResponseEntity.ok(mapperUtil.map(updatedShelter, UserResponse.class));
    }
    
    @PostMapping("/{shelterId}/pets/{petId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SHELTER')")
    public ResponseEntity<PetResponse> assignPetToShelter(@PathVariable Long petId, @PathVariable Long shelterId) {
        Pet pet = shelterService.assignPetToShelter(petId, shelterId);
        return ResponseEntity.ok(mapperUtil.map(pet, PetResponse.class));
    }
    
    @DeleteMapping("/{shelterId}/pets/{petId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SHELTER')")
    public ResponseEntity<Void> removePetFromShelter(@PathVariable Long petId, @PathVariable Long shelterId) {
        shelterService.removePetFromShelter(petId, shelterId);
        return ResponseEntity.noContent().build();
    }
}