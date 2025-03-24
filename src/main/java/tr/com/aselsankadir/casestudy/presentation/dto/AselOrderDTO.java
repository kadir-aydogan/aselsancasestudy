package tr.com.aselsankadir.casestudy.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AselOrderDTO {

    private String id;
    private String customerId;

    private String customerEmail;
    private String restaurantId;
    private String restaurantName;
    private BigDecimal totalAmount;
    private AselOrderStatus status;
    private List<AselOrderItemDTO> items;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
}
