package tr.com.aselsankadir.casestudy.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AselOrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asel_order_id")
    private AselOrderEntity order;
    @Column(name = "asel_order_id", updatable = false, insertable = false,nullable = false)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "asel_restaurant_id")
    private AselRestaurantEntity restaurant;
    @Column(name = "asel_restaurant_id", nullable = false, insertable = false, updatable = false)
    private Long restaurantId;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "asel_menu_item_id")
    private AselMenuItemEntity menuItem;
    @Column(name = "asel_menu_item_id", updatable = false, insertable = false, nullable = false)
    private Long menuItemId;
    @Transient
    private String menuItemTitle;

    private BigDecimal amount;
}
