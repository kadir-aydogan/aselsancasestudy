package tr.com.aselsankadir.casestudy.domain.dish;

import tr.com.aselsankadir.casestudy.domain.common.AselId;
import tr.com.aselsankadir.casestudy.domain.common.RandomLongGenerator;

public class DishId implements AselId {


    private final Long id;

    @Override
    public Long getValue() {
        return this.id;
    }


    public DishId(Long id) {
        this.id = id;
    }


    protected static DishId newDishId() {
        return new DishId(RandomLongGenerator.getLong());
    }
}
