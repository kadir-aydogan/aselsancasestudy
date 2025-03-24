package tr.com.aselsankadir.casestudy.infrastructure.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tr.com.aselsankadir.casestudy.domain.order.AselOrder;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderStatus;
import tr.com.aselsankadir.casestudy.domain.order.IAselOrderRepository;
import tr.com.aselsankadir.casestudy.domain.order.OrderId;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;
import tr.com.aselsankadir.casestudy.domain.user.UserId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class AselOrderJdbcRepository extends AselJdbcRepository<AselOrder> implements IAselOrderRepository {


    private final AselOrderRowMapper rowMapper;

    public AselOrderJdbcRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.rowMapper = new AselOrderRowMapper();
    }

    private static final String SQL_COLUMNS = """
            t.id,
            t.total_amount,
            t.status,
            t.customer_id,
            t.restaurant_id,
            t.created_time,
            t.update_time
            """;

    private static final String SELECT_SQL = """
            SELECT %s
            FROM asel_order_entity t
            """.formatted(SQL_COLUMNS);

    private static final String FIND_ALL_BY_CUSTOMER_ID_SQL =
            SELECT_SQL + " WHERE t.customer_id = ?";

    @Override
    public List<AselOrder> findAllByCustomerId(UserId customerId) {
        return jdbcTemplate.query(
                FIND_ALL_BY_CUSTOMER_ID_SQL,
                new Object[]{customerId.getId()},
                rowMapper
        );
    }

    private static final String FIND_BY_CUSTOMER_ID_AND_ORDER_ID_SQL =
            SELECT_SQL + " WHERE t.customer_id = ? AND t.id = ?";

    private static final String FIND_BY_RESTAURANT_ID_AND_ORDER_ID_SQL =
            SELECT_SQL + " WHERE t.restaurant_id = ? AND t.id = ?";

    @Override
    public Optional<AselOrder> findByRestaurantId(RestaurantId restaurantId, OrderId orderId) {
        return jdbcTemplate.query(
                FIND_BY_RESTAURANT_ID_AND_ORDER_ID_SQL,
                new Object[]{restaurantId.getValue(), orderId.getId()},
                rowMapper
        ).stream().findFirst();
    }
    @Override
    public Optional<AselOrder> findByCustomerId(UserId customerId, OrderId orderId) {
        return jdbcTemplate.query(
                FIND_BY_CUSTOMER_ID_AND_ORDER_ID_SQL,
                new Object[]{customerId.getId(), orderId.getId()},
                rowMapper
        ).stream().findFirst();
    }

    private static final String FIND_ALL_BY_RESTAURANT_ID_SQL =
            SELECT_SQL + " WHERE t.restaurant_id = ?";

    @Override
    public List<AselOrder> findAllByRestaurantId(RestaurantId restaurantId) {
        return jdbcTemplate.query(
                FIND_ALL_BY_RESTAURANT_ID_SQL,
                new Object[]{restaurantId.getValue()},
                rowMapper
        );
    }


    private static final String UPDATE_SQL =
            "UPDATE asel_order_entity SET status = ?, update_time = CURRENT_TIMESTAMP() WHERE restaurant_id = ? AND id = ?";

    @Override
    public int updateStatus(RestaurantId restaurantId, OrderId orderId, AselOrderStatus status) {
        return jdbcTemplate.update(
                UPDATE_SQL,
                status.name(),
                restaurantId.getValue(),
                orderId.getId()
        );
    }

    private static final String INSERT_SQL = """
            INSERT INTO ASEL_ORDER_ENTITY (
                id,
                total_amount,
                status,
                customer_id,
                restaurant_id,
                created_time
            ) VALUES (?, ?, ?, ?, ?, NOW())
            """;

    @Override
    public OrderId insert(AselOrder order) {

        jdbcTemplate.update(INSERT_SQL,
                order.getId().getId(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getCustomerId().getId(),
                order.getRestaurantId().getValue()
        );

        return order.getId();
    }
}
class AselOrderRowMapper implements RowMapper<AselOrder> {

    @Override
    public AselOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AselOrder.restore(
                new OrderId(rs.getLong("id")),
                UserId.restore(rs.getLong("customer_id")),
                new RestaurantId(rs.getLong("restaurant_id")),
                rs.getBigDecimal("total_amount"),
                AselOrderStatus.valueOf(rs.getString("status")),
                rs.getTimestamp("created_time").toLocalDateTime(),
                rs.getTimestamp("update_time") != null ? rs.getTimestamp("update_time").toLocalDateTime() : null
        );
    }
}



