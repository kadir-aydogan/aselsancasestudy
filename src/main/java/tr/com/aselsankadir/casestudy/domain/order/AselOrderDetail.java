package tr.com.aselsankadir.casestudy.domain.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class AselOrderDetail {
    private Long id;
    private Long customerId;

    private String customerEmail;
    private Long restaurantId;
    private String restaurantName;
    private BigDecimal totalAmount;
    private AselOrderStatus status;
    private List<AselOrderItemDetail> items;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;

    public AselOrderDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long orderId) {
        this.id = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public AselOrderStatus getStatus() {
        return status;
    }

    public void setStatus(AselOrderStatus status) {
        this.status = status;
    }

    public List<AselOrderItemDetail> getItems() {
        return items;
    }

    public void setItems(List<AselOrderItemDetail> items) {
        this.items = items;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
