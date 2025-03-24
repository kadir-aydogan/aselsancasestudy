package tr.com.aselsankadir.casestudy.infrastructure.jpa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AselOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private AselOrderStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private AselUserEntity customer;
    @Column(name = "customer_id", nullable = false, insertable = false, updatable = false)
    private Long customerId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonIgnoreProperties(value = {"orders"})
    private AselRestaurantEntity restaurant;
    @Column(name = "restaurant_id", nullable = false, insertable = false, updatable = false)
    private Long restaurantId;
    @Transient
    private String restaurantName;

    @OneToMany(mappedBy = "order")
    private List<AselOrderItemEntity> orderItems;

    private LocalDateTime createdTime;

    private LocalDateTime updateTime;
}
