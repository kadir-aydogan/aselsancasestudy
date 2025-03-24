package tr.com.aselsankadir.casestudy.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.*;
import tr.com.aselsankadir.casestudy.domain.user.Role;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AselUserEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToOne(mappedBy = "manager")
    @JoinColumn(name = "restaurant_id")
    private AselRestaurantEntity restaurant;
    @Column(name = "restaurant_id", updatable = false, insertable = false)
    private Long restaurantId;

    @OneToMany(mappedBy = "customer")
    private List<AselOrderEntity> customers;

    @Enumerated(EnumType.STRING)
    private Role role;

}
