package tr.com.aselsankadir.casestudy.domain.menuitem;

import tr.com.aselsankadir.casestudy.domain.menu.MenuId;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

import java.util.List;
import java.util.Optional;

public interface IAselMenuItemRepository {
    Optional<AselMenuItem> findById(RestaurantId restaurantId, MenuItemId menuItemId);

    List<AselMenuItem> findAllByMenuId(RestaurantId restaurantId, MenuId menuId);
}
