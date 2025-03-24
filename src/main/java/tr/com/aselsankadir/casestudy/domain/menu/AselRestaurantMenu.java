package tr.com.aselsankadir.casestudy.domain.menu;

import tr.com.aselsankadir.casestudy.domain.menuitem.AselMenuItem;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AselRestaurantMenu {

    private final MenuId id;

    private final String title;

    private final String description;

    private final LocalDate date;

    private final List<AselMenuItem> menuItems;

    private final RestaurantId restaurantId;

    public AselRestaurantMenu(MenuId id, String title, String description, LocalDate date, List<AselMenuItem> menuItems, RestaurantId restaurantId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.menuItems = menuItems != null ? menuItems : new ArrayList<>();
        this.restaurantId = restaurantId;
    }

    public void addMenuItem(AselMenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public void addAllMenuItem(List<AselMenuItem> items) {
        items.forEach(this::addMenuItem);
    }

    public MenuId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<AselMenuItem> getMenuItems() {
        return menuItems;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }
}
