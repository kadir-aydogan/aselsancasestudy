package tr.com.aselsankadir.casestudy.domain.menu;

import tr.com.aselsankadir.casestudy.domain.common.AselId;
import tr.com.aselsankadir.casestudy.domain.common.RandomLongGenerator;

public class MenuId implements AselId {
    private final Long id;

    public MenuId(Long id) {
        this.id = id;
    }

    @Override
    public Long getValue() {
        return id;
    }

    protected static MenuId newMenuId() {
        return new MenuId(RandomLongGenerator.getLong());
    }
}
