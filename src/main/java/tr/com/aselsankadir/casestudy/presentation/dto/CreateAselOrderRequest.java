package tr.com.aselsankadir.casestudy.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAselOrderRequest {
    @NotNull(message = "Lütfen restorant seçiniz.")
    private Long restaurantId;

    @NotEmpty(message = "Lütfen sepetinize ürün ekleyin.")
    @Valid
    private List<@Valid CreateMenuItemRequest> dishIds;
}
