package pets.adoption.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pets.adoption.models.Breed;
import pets.adoption.models.Category;
import pets.adoption.repositories.BreedRepository;
import pets.adoption.repositories.CategoryRepository;
import pets.adoption.exceptions.ResourceNotFoundException;
import pets.adoption.exceptions.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BreedService {
    
    private final BreedRepository breedRepository;
    private final CategoryRepository categoryRepository;
    
    public Breed createBreed(Breed breed) {
        // Validate category
        Category category = categoryRepository.findById(breed.getCategory().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        
        // Check for duplicate breed in the same category
        if (breedRepository.existsByNameAndCategory_Id(breed.getName(), category.getId())) {
            throw new DuplicateResourceException("Breed already exists with name: " + breed.getName() + " in category: " + category.getName());
        }
        
        breed.setCategory(category);
        return breedRepository.save(breed);
    }
    
    public Breed getBreedById(Long id) {
        return breedRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Breed not found with id: " + id));
    }
    
    public Breed getBreedByName(String name) {
        return breedRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("Breed not found with name: " + name));
    }
    
    public List<Breed> getAllBreeds() {
        return breedRepository.findAll();
    }
    
    public List<Breed> getBreedsByCategory(Long categoryId) {
        return breedRepository.findByCategory_Id(categoryId);
    }
    
    public Breed updateBreed(Long id, Breed breedDetails) {
        Breed breed = getBreedById(id);
        
        // Update category if changed
        if (!breed.getCategory().getId().equals(breedDetails.getCategory().getId())) {
            Category category = categoryRepository.findById(breedDetails.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            breed.setCategory(category);
        }
        
        // Check for duplicate breed in the same category if name changed
        if (!breed.getName().equals(breedDetails.getName()) && 
            breedRepository.existsByNameAndCategory_Id(breedDetails.getName(), breed.getCategory().getId())) {
            throw new DuplicateResourceException("Breed already exists with name: " + breedDetails.getName() + " in category: " + breed.getCategory().getName());
        }
        
        breed.setName(breedDetails.getName());
        breed.setDescription(breedDetails.getDescription());
        
        return breedRepository.save(breed);
    }
    
    public void deleteBreed(Long id) {
        Breed breed = getBreedById(id);
        
        // Check if there are pets with this breed
        if (!breed.getPets().isEmpty()) {
            throw new IllegalStateException("Cannot delete breed with existing pets");
        }
        
        breedRepository.delete(breed);
    }
}