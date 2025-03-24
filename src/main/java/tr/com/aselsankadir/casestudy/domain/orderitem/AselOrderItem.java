package tr.com.aselsankadir.casestudy.domain.orderitem;

import tr.com.aselsankadir.casestudy.domain.common.AselDomainException;
import tr.com.aselsankadir.casestudy.domain.menuitem.MenuItemId;
import tr.com.aselsankadir.casestudy.domain.order.OrderId;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

import java.math.BigDecimal;

public class AselOrderItem {
    private final OrderItemId id;

    private OrderId orderId;
    private final MenuItemId menuItemId;
    private final RestaurantId restaurantId;
    private final int quantity;
    private final BigDecimal amount;

    private AselOrderItem(OrderItemId id,
                          OrderId orderId,
                          MenuItemId menuItemId,
                          RestaurantId restaurantId,
                          int quantity,
                          BigDecimal amount) {
        this.id = id;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.restaurantId = restaurantId;
        this.quantity = quantity;
        this.amount = amount;
    }

    public static AselOrderItem create(MenuItemId menuItemId, RestaurantId restaurantId, int quantity, BigDecimal amount) {
        if (menuItemId == null)
            throw new AselDomainException("Yemek seçmelisiniz.");

        if (restaurantId == null)
            throw new AselDomainException("Restorant seçiniz.");

        if (quantity <= 0)
            throw new AselDomainException("Adet sayısı sıfır ya da negatif olamaz.");

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new AselDomainException("Toplam tutar negatif olamaz.");

        return new AselOrderItem(OrderItemId.newId(), null, menuItemId, restaurantId, quantity, amount);
    }

    public static AselOrderItem restore(OrderItemId id, OrderId orderId, MenuItemId menuItemId, RestaurantId restaurantId, int quantity, BigDecimal amount) {
        return new AselOrderItem(id, orderId, menuItemId, restaurantId, quantity, amount);
    }

    public void setOrderId(OrderId orderId) {
        this.orderId = orderId;
    }

    public BigDecimal totalAmount() {
        return amount.multiply(BigDecimal.valueOf(quantity));
    }

    public MenuItemId getMenuItemId() {
        return menuItemId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public OrderItemId getId() {
        return id;
    }

    public OrderId getOrderId() {
        return orderId;
    }
}
