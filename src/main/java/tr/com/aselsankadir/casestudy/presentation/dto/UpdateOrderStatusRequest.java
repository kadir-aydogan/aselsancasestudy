package tr.com.aselsankadir.casestudy.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderStatus;

@Getter
@NoArgsConstructor
@Setter
public class UpdateOrderStatusRequest {
    @NotNull(message = "Sipariş Durumu boş olamaz.")
    private AselOrderStatus status;
}


