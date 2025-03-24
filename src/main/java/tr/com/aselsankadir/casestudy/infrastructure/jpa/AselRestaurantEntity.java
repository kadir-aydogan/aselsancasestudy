package tr.com.aselsankadir.casestudy.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "asel_restaurant")
@Getter
@Setter
@NoArgsConstructor
public class AselRestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String description;

    @OneToOne(optional = false)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private AselUserEntity manager;

    @OneToMany(mappedBy = "restaurant")
    private List<AselMenuCategory> menuItems;

    @OneToMany(mappedBy = "restaurant")
    private List<AselRestaurantMenuEntity> menus;

    @OneToMany(mappedBy = "restaurant")
    private List<AselOrderItemEntity> orderItems;

    @OneToMany(mappedBy = "restaurant")
    private List<AselOrderEntity> orders;

    @OneToMany(mappedBy = "restaurant")
    private List<AselDishEntity> dishes;
}
