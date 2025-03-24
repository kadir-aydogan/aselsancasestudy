package tr.com.aselsankadir.casestudy.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "asel_menu_item")
@Getter
@Setter
@NoArgsConstructor
public class AselMenuItemEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private AselRestaurantEntity restaurant;
    @Column(name = "restaurant_id", nullable = false, insertable = false, updatable = false)
    private Long restaurantId;

    @ManyToOne
    @JoinColumn(name = "asel_dish_id")
    private AselDishEntity dish;
    @Column(name = "asel_dish_id", nullable = false, insertable = false, updatable = false)
    private Long dishId;
    @Transient
    private String dishTitle;
    @Transient
    private String ingredients;

    @ManyToOne
    @JoinColumn(name = "asel_restaurant_menu_id")
    private AselRestaurantMenuEntity restaurantMenu;
    @Column(name = "asel_restaurant_menu_id", nullable = false, insertable = false, updatable = false)
    private Long restaurantMenuId;

    @Transient
    private Long menuCategoryId;
    @Transient
    private String menuCategoryTitle;

    @OneToMany(mappedBy = "menuItem")
    private List<AselOrderItemEntity> orderItems;
}
