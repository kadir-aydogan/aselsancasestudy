package tr.com.aselsankadir.casestudy.domain.order;

import java.math.BigDecimal;

public class AselOrderItemDetail {
    private Long id;

    private Long orderId;

    private Long menuItemId;
    private String menuItemTitle;
    private int quantity;
    private BigDecimal amount;

    public AselOrderItemDetail() {

    }

    public AselOrderItemDetail(Long id, Long orderId, Long menuItemId, Long restaurantId, String menuItemTitle, int quantity, BigDecimal amount) {
        this.id = id;
        this.menuItemId = menuItemId;
        this.menuItemTitle = menuItemTitle;
        this.quantity = quantity;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuItemTitle() {
        return menuItemTitle;
    }

    public void setMenuItemTitle(String menuItemTitle) {
        this.menuItemTitle = menuItemTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
