package pets.adoption.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pets.adoption.models.Adoption;
import pets.adoption.services.AdoptionService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/adoptions")
@RequiredArgsConstructor
public class AdoptionController {
    
    private final AdoptionService adoptionService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Adoption> createAdoption(@Valid @RequestBody Adoption adoption) {
        Adoption createdAdoption = adoptionService.createAdoption(adoption);
        return ResponseEntity.ok(createdAdoption);
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SHELTER')")
    public ResponseEntity<List<Adoption>> getAllAdoptions() {
        List<Adoption> adoptions = adoptionService.getAllAdoptions();
        return ResponseEntity.ok(adoptions);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SHELTER') or #id == authentication.principal.id")
    public ResponseEntity<Adoption> getAdoptionById(@PathVariable Long id) {
        Adoption adoption = adoptionService.getAdoptionById(id);
        return ResponseEntity.ok(adoption);
    }
    
    @GetMapping("/adopter/{adopterId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SHELTER') or #adopterId == authentication.principal.id")
    public ResponseEntity<List<Adoption>> getAdoptionsByAdopter(@PathVariable Long adopterId) {
        List<Adoption> adoptions = adoptionService.getAdoptionsByAdopter(adopterId);
        return ResponseEntity.ok(adoptions);
    }
    
    @GetMapping("/pet/{petId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SHELTER')")
    public ResponseEntity<List<Adoption>> getAdoptionsByPet(@PathVariable Long petId) {
        List<Adoption> adoptions = adoptionService.getAdoptionsByPet(petId);
        return ResponseEntity.ok(adoptions);
    }
    
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SHELTER')")
    public ResponseEntity<List<Adoption>> getAdoptionsByStatus(@PathVariable String status) {
        List<Adoption> adoptions = adoptionService.getAdoptionsByStatus(status);
        return ResponseEntity.ok(adoptions);
    }
    
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'SHELTER')")
    public ResponseEntity<Adoption> updateAdoptionStatus(@PathVariable Long id, @RequestParam String status) {
        Adoption updatedAdoption = adoptionService.updateAdoptionStatus(id, status);
        return ResponseEntity.ok(updatedAdoption);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SHELTER')")
    public ResponseEntity<Adoption> updateAdoption(@PathVariable Long id, @Valid @RequestBody Adoption adoptionDetails) {
        Adoption updatedAdoption = adoptionService.updateAdoption(id, adoptionDetails);
        return ResponseEntity.ok(updatedAdoption);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAdoption(@PathVariable Long id) {
        adoptionService.deleteAdoption(id);
        return ResponseEntity.noContent().build();
    }
}