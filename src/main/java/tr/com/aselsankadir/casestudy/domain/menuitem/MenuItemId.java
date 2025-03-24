package tr.com.aselsankadir.casestudy.domain.menuitem;

import tr.com.aselsankadir.casestudy.domain.common.AselId;

public class MenuItemId implements AselId {
    private final Long id;

    public MenuItemId(Long id) {
        this.id = id;
    }

    @Override
    public Long getValue() {
        return id;
    }
}
