package tr.com.aselsankadir.casestudy.domain.restaurant;

import tr.com.aselsankadir.casestudy.domain.common.AselId;

import java.util.Objects;

public class RestaurantId implements AselId {
    private final Long id;

    public RestaurantId(Long id) {
        this.id = id;
    }

    @Override
    public Long getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantId that = (RestaurantId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
