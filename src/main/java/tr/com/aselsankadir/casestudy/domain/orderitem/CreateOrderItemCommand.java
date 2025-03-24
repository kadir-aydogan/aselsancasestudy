package tr.com.aselsankadir.casestudy.domain.orderitem;

import tr.com.aselsankadir.casestudy.domain.menuitem.MenuItemId;

public class CreateOrderItemCommand {
    private MenuItemId menuItemId;
    private Integer quantity;

    public MenuItemId getMenuItemId() {
        return menuItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CreateOrderItemCommand(MenuItemId menuItemId, Integer quantity) {
        this.menuItemId = menuItemId;
        this.quantity = quantity;
    }
}
