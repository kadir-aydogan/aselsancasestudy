package tr.com.aselsankadir.casestudy.domain.order;

import tr.com.aselsankadir.casestudy.domain.orderitem.CreateOrderItemCommand;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;
import tr.com.aselsankadir.casestudy.domain.user.UserId;

import java.util.List;

public class PlaceOrderCommand {

    private UserId userId;
    private RestaurantId restaurantId;
    private List<CreateOrderItemCommand> dishIds;

    public PlaceOrderCommand(UserId userId, RestaurantId restaurantId, List<CreateOrderItemCommand> dishIds) {
        this.restaurantId = restaurantId;
        this.dishIds = dishIds;
        this.userId = userId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public UserId getUserId() {
        return userId;
    }

    public List<CreateOrderItemCommand> getDishIds() {
        return dishIds;
    }
}
