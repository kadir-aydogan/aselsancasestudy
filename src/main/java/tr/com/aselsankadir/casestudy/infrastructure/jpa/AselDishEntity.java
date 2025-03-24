package tr.com.aselsankadir.casestudy.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "asel_dish")
@Getter
@Setter
@NoArgsConstructor
public class AselDishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    private String ingredients;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "asel_menu_category_id")
    private AselMenuCategory menuCategory;
    @Column(name = "asel_menu_category_id", updatable = false, insertable = false, nullable = false)
    private Long menuCategoryId;
    @Transient
    private String menuCategoryName;

    @ManyToOne
    @JoinColumn(name = "asel_restaurant_id")
    private AselRestaurantEntity restaurant;
    @Column(name = "asel_restaurant_id", insertable = false, updatable = false, nullable = false)
    private Long restaurantId;

    @OneToMany(mappedBy = "dish")
    private List<AselMenuItemEntity> menuItems;
}
