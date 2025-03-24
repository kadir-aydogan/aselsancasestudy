package tr.com.aselsankadir.casestudy.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AselOrderItemDTO {
    private String id;
    private String orderId;
    private String menuItemId;
    private String menuItemTitle;
    private int quantity;
    private BigDecimal amount;
}
