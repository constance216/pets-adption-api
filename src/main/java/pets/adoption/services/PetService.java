package pets.adoption.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pets.adoption.models.Pet;
import pets.adoption.models.User;
import pets.adoption.models.Category;
import pets.adoption.repositories.PetRepository;
import pets.adoption.repositories.UserRepository;
import pets.adoption.repositories.CategoryRepository;
import pets.adoption.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PetService {
    
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    
    public Pet createPet(Pet pet) {
        // Validate category
        if (pet.getCategory() != null && pet.getCategory().getId() != null) {
            Category category = categoryRepository.findById(pet.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            pet.setCategory(category);
        }
        
        // Validate owner
        if (pet.getOwner() != null && pet.getOwner().getId() != null) {
            User owner = userRepository.findById(pet.getOwner().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
            pet.setOwner(owner);
        }
        
        return petRepository.save(pet);
    }
    
    public Pet getPetById(Long id) {
        return petRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + id));
    }
    
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
    
    public List<Pet> getPetsByStatus(String status) {
        return petRepository.findByStatus(status);
    }
    
    public List<Pet> getPetsByCategory(Long categoryId) {
        return petRepository.findByCategory_Id(categoryId);
    }
    
    public Pet updatePet(Long id, Pet petDetails) {
        Pet pet = getPetById(id);
        
        pet.setName(petDetails.getName());
        pet.setBreed(petDetails.getBreed());
        pet.setAge(petDetails.getAge());
        pet.setDescription(petDetails.getDescription());
        pet.setImage(petDetails.getImage());
        pet.setGender(petDetails.getGender());
        pet.setStatus(petDetails.getStatus());
        
        if (petDetails.getCategory() != null) {
            pet.setCategory(petDetails.getCategory());
        }
        
        return petRepository.save(pet);
    }
    
    public void deletePet(Long id) {
        Pet pet = getPetById(id);
        petRepository.delete(pet);
    }
    
    public Pet assignShelter(Long petId, Long shelterId) {
        Pet pet = getPetById(petId);
        User shelter = userRepository.findById(shelterId)
            .orElseThrow(() -> new ResourceNotFoundException("Shelter not found"));
        
        pet.setShelter(shelter);
        return petRepository.save(pet);
    }
    
    public Pet assignVeterinarian(Long petId, Long veterinarianId) {
        Pet pet = getPetById(petId);
        User veterinarian = userRepository.findById(veterinarianId)
            .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found"));
        
        pet.setVeterinarian(veterinarian);
        return petRepository.save(pet);
    }
    
    public Pet adoptPet(Long petId, Long adopterId) {
        Pet pet = getPetById(petId);
        User adopter = userRepository.findById(adopterId)
            .orElseThrow(() -> new ResourceNotFoundException("Adopter not found"));
        
        pet.setAdoptedBy(adopter);
        pet.setStatus("ADOPTED");
        return petRepository.save(pet);
    }
}
