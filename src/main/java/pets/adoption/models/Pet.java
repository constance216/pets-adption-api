package pets.adoption.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String breed;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    @Column(nullable = false)
    private Integer age;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private String image;
    
    @Column(nullable = false)
    private String gender;
    
    @Column(nullable = false)
    private String status = "ACTIVE";
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private User shelter;
    
    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    private User veterinarian;
    
    @ManyToOne
    @JoinColumn(name = "adopted_by")
    private User adoptedBy;
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}