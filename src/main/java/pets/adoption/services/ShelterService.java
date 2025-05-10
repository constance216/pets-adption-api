package pets.adoption.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pets.adoption.models.User;
import pets.adoption.models.Pet;
import pets.adoption.repositories.UserRepository;
import pets.adoption.repositories.PetRepository;
import pets.adoption.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShelterService {
    
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    
    public List<User> getAllShelters() {
        return userRepository.findAll().stream()
            .filter(user -> "SHELTER".equals(user.getRole()))
            .collect(Collectors.toList());
    }
    
    public User getShelterById(Long id) {
        User shelter = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Shelter not found with id: " + id));
        
        if (!"SHELTER".equals(shelter.getRole())) {
            throw new IllegalArgumentException("User is not a shelter");
        }
        
        return shelter;
    }
    
    public List<Pet> getPetsByShelter(Long shelterId) {
        getShelterById(shelterId); // Validate shelter exists
        return petRepository.findByShelter_Id(shelterId);
    }
    
    public User createShelter(User shelterData) {
        shelterData.setRole("SHELTER");
        return userRepository.save(shelterData);
    }
    
    public User updateShelter(Long id, User shelterDetails) {
        User shelter = getShelterById(id);
        
        shelter.setFullName(shelterDetails.getFullName());
        shelter.setEmail(shelterDetails.getEmail());
        
        if (shelterDetails.getPassword() != null && !shelterDetails.getPassword().isEmpty()) {
            shelter.setPassword(shelterDetails.getPassword());
        }
        
        return userRepository.save(shelter);
    }
    
    public Pet assignPetToShelter(Long petId, Long shelterId) {
        Pet pet = petRepository.findById(petId)
            .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        
        User shelter = getShelterById(shelterId);
        
        pet.setShelter(shelter);
        return petRepository.save(pet);
    }
    
    public void removePetFromShelter(Long petId, Long shelterId) {
        Pet pet = petRepository.findById(petId)
            .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        
        if (pet.getShelter() != null && pet.getShelter().getId().equals(shelterId)) {
            pet.setShelter(null);
            petRepository.save(pet);
        }
    }
}