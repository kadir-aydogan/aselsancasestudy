package tr.com.aselsankadir.casestudy.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderDetail;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderStatus;
import tr.com.aselsankadir.casestudy.domain.order.IAselOrderDetailRepository;
import tr.com.aselsankadir.casestudy.domain.order.OrderId;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;
import tr.com.aselsankadir.casestudy.domain.user.UserId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AselOrderDetailJdbcRepository implements IAselOrderDetailRepository {
    AselOrderDetailDTORowMapper rowMapper = new AselOrderDetailDTORowMapper();
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_COLUMNS = """
        t.id,
        t.customer_id,
        c.email,
        t.restaurant_id,
        r.name, 
        t.total_amount, 
        t.status,
        t.created_time,
        t.update_time
    """;

    private static final String SELECT_SQL =
            "SELECT " + SQL_COLUMNS
            + " FROM asel_order_entity t "
            + " LEFT JOIN asel_user_entity c on t.customer_id = c.id "
            + " LEFT JOIN asel_restaurant r on t.restaurant_id = r.id ";


    private static final String FIND_BY_CUSTOMER_ID_AND_ORDER_ID_SQL =
            SELECT_SQL + " WHERE t.customer_id = ? AND t.id = ?";

    private static final String FIND_BY_RESTAURANT_ID_AND_ORDER_ID_SQL =
            SELECT_SQL + " WHERE t.restaurant_id = ? AND t.id = ?";

    @Override
    public Optional<AselOrderDetail> findByRestaurantId(RestaurantId restaurantId, OrderId orderId) {
        return jdbcTemplate.query(
                FIND_BY_RESTAURANT_ID_AND_ORDER_ID_SQL,
                rowMapper,
                restaurantId.getValue(), orderId.getId()
        ).stream().findFirst();
    }

    private static final String FIND_BY_CUSTOMER_ID =
            SELECT_SQL + " WHERE t.customer_id = ? ";
    @Override
    public List<AselOrderDetail> findAllByCustomerId(UserId userId) {
        return jdbcTemplate.query(
                FIND_BY_CUSTOMER_ID,
                rowMapper,
                userId.getId()
        );
    }

    private static final String FIND_BY_RESTAURANT_ID =
            SELECT_SQL + " WHERE t.restaurant_id = ? ";

    @Override
    public List<AselOrderDetail> findAllByRestaurantId(RestaurantId restaurantId) {
        return jdbcTemplate.query(
                FIND_BY_RESTAURANT_ID,
                rowMapper,
                restaurantId.getValue()
        );
    }

    @Override
    public Optional<AselOrderDetail> findByCustomerId(UserId customerId, OrderId orderId) {
        return jdbcTemplate.query(
                FIND_BY_CUSTOMER_ID_AND_ORDER_ID_SQL,
                rowMapper,
                customerId.getId(), orderId.getId()
                ).stream().findFirst();
    }
}
class AselOrderDetailDTORowMapper implements RowMapper<AselOrderDetail> {

    @Override
    public AselOrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        AselOrderDetail dto = new AselOrderDetail();
        dto.setId(rs.getLong("id"));
        dto.setCustomerId(rs.getLong("customer_id"));
        dto.setCustomerEmail(rs.getString("email"));
        dto.setRestaurantId(rs.getLong("restaurant_id"));
        dto.setRestaurantName(rs.getString("name"));
        dto.setTotalAmount(rs.getBigDecimal("total_amount"));
        dto.setStatus(AselOrderStatus.valueOf(rs.getString("status")));
        dto.setCreatedTime(rs.getTimestamp("created_time").toLocalDateTime());
        Timestamp updateTime = rs.getTimestamp("update_time");
        if (updateTime != null) {
            dto.setUpdateTime(updateTime.toLocalDateTime());
        }

        return dto;
    }
}

