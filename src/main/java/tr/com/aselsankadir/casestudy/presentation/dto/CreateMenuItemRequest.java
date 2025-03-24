package tr.com.aselsankadir.casestudy.presentation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMenuItemRequest {
    @NotNull(message = "Menüden ürün seçiniz.")
    private Long menuItemId;

    @NotNull(message = "Adet belirtilmelidir.")
    @Min(value = 1, message = "Adet en az 1 olmalıdır.")
    private Integer quantity;
}
