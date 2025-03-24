package tr.com.aselsankadir.casestudy.domain.restaurant;

public class AselRestaurant {

    private RestaurantId id;

    private String name;

    private String description;

    public AselRestaurant(RestaurantId id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public RestaurantId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
