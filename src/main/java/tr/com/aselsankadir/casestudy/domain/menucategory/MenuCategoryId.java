package tr.com.aselsankadir.casestudy.domain.menucategory;

import tr.com.aselsankadir.casestudy.domain.common.AselId;
import tr.com.aselsankadir.casestudy.domain.common.RandomLongGenerator;

public class MenuCategoryId implements AselId {
    private final Long id;

    public MenuCategoryId(Long id) {
        this.id = id;
    }

    @Override
    public Long getValue() {
        return id;
    }

    protected static MenuCategoryId newMenuCategoryId() {
        return new MenuCategoryId(RandomLongGenerator.getLong());
    }
}
