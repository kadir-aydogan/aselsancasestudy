package tr.com.aselsankadir.casestudy.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.aselsankadir.casestudy.domain.menu.MenuId;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AselRestaurantMenuDTO {
    private String id;

    private String title;

    private String description;

    private LocalDate date;

    private List<AselMenuItemDTO> menuItems;

    private String restaurantId;
}
