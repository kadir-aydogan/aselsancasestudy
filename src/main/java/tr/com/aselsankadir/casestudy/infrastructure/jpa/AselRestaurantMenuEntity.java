package tr.com.aselsankadir.casestudy.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "asel_restaurant_menu")
@Getter
@Setter
@NoArgsConstructor
public class AselRestaurantMenuEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    private String description;

    private LocalDate date;

    @OneToMany(mappedBy = "restaurantMenu")
    private List<AselMenuItemEntity> menuItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="restaurant_id", nullable=false)
    private AselRestaurantEntity restaurant;
    @Column(name = "restaurant_id", updatable = false, insertable = false, nullable = false)
    private Long restaurantId;
}
