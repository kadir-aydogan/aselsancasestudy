package tr.com.aselsankadir.casestudy.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.aselsankadir.casestudy.domain.menu.MenuId;
import tr.com.aselsankadir.casestudy.domain.menuitem.AselMenuItem;
import tr.com.aselsankadir.casestudy.domain.menuitem.IAselMenuItemRepository;
import tr.com.aselsankadir.casestudy.domain.menuitem.MenuItemId;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AselMenuItemService {

    private final IAselMenuItemRepository menuItemRepository;

    public Optional<AselMenuItem> findByRestaurantId(RestaurantId restaurantId, MenuItemId menuItemId) {
        return menuItemRepository.findById(restaurantId, menuItemId);
    }

    public List<AselMenuItem> findAllByMenuId(RestaurantId restaurantId, MenuId menuId) {
        return menuItemRepository.findAllByMenuId(restaurantId, menuId);
    }

}
