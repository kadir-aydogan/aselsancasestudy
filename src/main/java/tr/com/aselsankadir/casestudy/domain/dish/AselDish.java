package tr.com.aselsankadir.casestudy.domain.dish;

import tr.com.aselsankadir.casestudy.domain.menucategory.MenuCategoryId;
import tr.com.aselsankadir.casestudy.domain.menuitem.AselMenuItem;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

import java.math.BigDecimal;
import java.util.List;

public class AselDish {
    private DishId id;

    private String title;

    private String ingredients;

    private BigDecimal amount;

    private MenuCategoryId menuCategoryId;
    private RestaurantId restaurantId;
    private List<AselMenuItem> menuItems;
}
