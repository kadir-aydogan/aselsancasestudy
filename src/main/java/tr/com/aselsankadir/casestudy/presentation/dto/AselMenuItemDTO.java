package tr.com.aselsankadir.casestudy.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AselMenuItemDTO {
    private String id;
    private BigDecimal amount;
    private String restaurantId;

    private String dishId;
    private String dishTitle;
    private String ingredients;

    private String restaurantMenuId;
    private String menuCategoryId;
    private String menuCategoryTitle;
}
