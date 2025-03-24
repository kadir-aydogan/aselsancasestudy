package tr.com.aselsankadir.casestudy.domain.menuitem;

import tr.com.aselsankadir.casestudy.domain.dish.DishId;
import tr.com.aselsankadir.casestudy.domain.menu.MenuId;
import tr.com.aselsankadir.casestudy.domain.menucategory.MenuCategoryId;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

import java.math.BigDecimal;

public class AselMenuItem {
    private MenuItemId id;
    private BigDecimal amount;
    private RestaurantId restaurantId;

    private DishId dishId;
    private String dishTitle;
    private String ingredients;

    private MenuId restaurantMenuId;
    private MenuCategoryId menuCategoryId;
    private String menuCategoryTitle;

    public AselMenuItem(MenuItemId id, BigDecimal amount, RestaurantId restaurantId, DishId dishId, String dishTitle, String ingredients, MenuId restaurantMenuId, MenuCategoryId menuCategoryId, String menuCategoryTitle) {
        this.id = id;
        this.amount = amount;
        this.restaurantId = restaurantId;
        this.dishId = dishId;
        this.dishTitle = dishTitle;
        this.ingredients = ingredients;
        this.restaurantMenuId = restaurantMenuId;
        this.menuCategoryId = menuCategoryId;
        this.menuCategoryTitle = menuCategoryTitle;
    }

    public AselMenuItem(MenuItemId id, BigDecimal amount, RestaurantId restaurantId, DishId dishId, MenuId restaurantMenuId) {
        this.id = id;
        this.amount = amount;
        this.restaurantId = restaurantId;
        this.dishId = dishId;
        this.restaurantMenuId = restaurantMenuId;
    }

    public MenuItemId getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public DishId getDishId() {
        return dishId;
    }

    public String getDishTitle() {
        return dishTitle;
    }

    public String getIngredients() {
        return ingredients;
    }

    public MenuId getRestaurantMenuId() {
        return restaurantMenuId;
    }

    public MenuCategoryId getMenuCategoryId() {
        return menuCategoryId;
    }

    public String getMenuCategoryTitle() {
        return menuCategoryTitle;
    }
}
