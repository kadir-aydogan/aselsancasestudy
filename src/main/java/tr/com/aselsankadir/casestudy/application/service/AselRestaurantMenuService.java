package tr.com.aselsankadir.casestudy.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.aselsankadir.casestudy.domain.menu.AselRestaurantMenu;
import tr.com.aselsankadir.casestudy.domain.menu.IAselRestaurantMenuRepository;
import tr.com.aselsankadir.casestudy.domain.menuitem.AselMenuItem;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AselRestaurantMenuService {

    private final AselMenuItemService menuItemService;
    private final IAselRestaurantMenuRepository restaurantMenuRepository;

    public Optional<AselRestaurantMenu> findByDate(RestaurantId restaurantId, LocalDate date) {

        Optional<AselRestaurantMenu> restaurantMenuOptional = restaurantMenuRepository.findAllByDate(restaurantId, date);

        if (restaurantMenuOptional.isEmpty()) {
            return restaurantMenuOptional;
        }
        AselRestaurantMenu aselRestaurantMenu = restaurantMenuOptional.get();

        List<AselMenuItem> menuItems = menuItemService.findAllByMenuId(restaurantId, aselRestaurantMenu.getId());

        aselRestaurantMenu.addAllMenuItem(menuItems);

        return Optional.of(aselRestaurantMenu);
    }

}
