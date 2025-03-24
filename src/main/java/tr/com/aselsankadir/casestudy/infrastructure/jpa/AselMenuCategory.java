package tr.com.aselsankadir.casestudy.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AselMenuCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="restaurant_id", nullable=false)
    private AselRestaurantEntity restaurant;
    @Column(name = "restaurant_id", updatable = false, insertable = false, nullable = false)
    private Long restaurantId;

    @OneToMany(mappedBy = "menuCategory")
    private List<AselDishEntity> aselDishes;

    private String description;
}
