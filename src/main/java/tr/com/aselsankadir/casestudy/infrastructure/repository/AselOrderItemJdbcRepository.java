package tr.com.aselsankadir.casestudy.infrastructure.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tr.com.aselsankadir.casestudy.domain.menuitem.MenuItemId;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderItemDetail;
import tr.com.aselsankadir.casestudy.domain.order.OrderId;
import tr.com.aselsankadir.casestudy.domain.orderitem.AselOrderItem;
import tr.com.aselsankadir.casestudy.domain.orderitem.IAselOrderItemRepository;
import tr.com.aselsankadir.casestudy.domain.orderitem.OrderItemId;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AselOrderItemJdbcRepository extends AselJdbcRepository<AselOrderItem> implements IAselOrderItemRepository {

    private final RowMapper<AselOrderItem> rowMapper;

    private final RowMapper<AselOrderItemDetail> detailRowMapper;

    public AselOrderItemJdbcRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.rowMapper = new AselOrderItemRowMapper();
        this.detailRowMapper = new AselOrderItemDetailDTORowMapper();
    }

    private static final String SQL_COLUMNS =
            "t.id, " +
            "t.asel_order_id, " +
            "t.asel_restaurant_id, " +
            "t.asel_menu_item_id, " +
            "t.quantity, " +
            "t.amount ";

    private static final String SELECT_SQL =
            "SELECT "
            + SQL_COLUMNS
            + " FROM ASEL_ORDER_ITEM_ENTITY t "
            + " JOIN ASEL_MENU_ITEM mi ON mi.id = t.asel_menu_item_id ";

    private static final String FIND_ALL_BY_ORDER_ID =
            SELECT_SQL + " WHERE t.asel_restaurant_id = ? AND t.asel_order_id = ?";

    private static final String DETAIL_SELECT_SQL =
            "SELECT "
            + SQL_COLUMNS
            + ", d.title "
            + """ 
                FROM ASEL_ORDER_ITEM_ENTITY t 
                JOIN ASEL_MENU_ITEM mi ON mi.id = t.asel_menu_item_id 
                JOIN ASEL_DISH d ON mi.asel_dish_id = d.id
            """;
    @Override
    public List<AselOrderItemDetail> findAllByOrderId(RestaurantId restaurantId, OrderId orderId) {
        return jdbcTemplate.query(DETAIL_SELECT_SQL + " WHERE t.asel_restaurant_id = ? and t.asel_order_id = ? ", detailRowMapper, restaurantId.getValue(), orderId.getId());
    }

    @Override
    public List<AselOrderItemDetail> findAllByOrderId(OrderId orderId) {
        return jdbcTemplate.query(DETAIL_SELECT_SQL + " WHERE t.asel_order_id = ? ", detailRowMapper, orderId.getId());
    }

    private static final String INSERT_SQL =
            "INSERT INTO ASEL_ORDER_ITEM_ENTITY (" +
                    " id, " +
                    " asel_order_id, " +
                    " asel_restaurant_id, " +
                    " quantity, " +
                    " asel_menu_item_id, " +
                    " amount " +
                    ") VALUES (?, ?, ?, ?, ?, ?)";

    @Override
    public Long insert(AselOrderItem item) {
        Long id = SequenceIdGenerator.next(jdbcTemplate, "ASEL_ORDER_ITEM_ENTITY_SEQ");

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL);
            ps.setLong(1, id); //
            ps.setLong(2, item.getOrderId().getId());
            ps.setLong(3, item.getRestaurantId().getValue());
            ps.setInt(4, item.getQuantity());
            ps.setLong(5, item.getMenuItemId().getValue());
            ps.setBigDecimal(6, item.getAmount());
            return ps;
        });

        return id;
    }
}
class AselOrderItemRowMapper implements RowMapper<AselOrderItem> {

    @Override
    public AselOrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AselOrderItem.restore(
                new OrderItemId(rs.getLong("id")),
                new OrderId(rs.getLong("asel_order_id")),
                new MenuItemId(rs.getLong("asel_menu_item_id")),
                new RestaurantId(rs.getLong("asel_restaurant_id")),
                rs.getInt("quantity"),
                rs.getBigDecimal("amount")
        );
    }
}
class AselOrderItemDetailDTORowMapper implements RowMapper<AselOrderItemDetail> {
    @Override
    public AselOrderItemDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AselOrderItemDetail(
                rs.getLong("id"),
                rs.getLong("asel_order_id"),
                rs.getLong("asel_menu_item_id"),
                rs.getLong("asel_restaurant_id"),
                rs.getString("title"),
                rs.getInt("quantity"),
                rs.getBigDecimal("amount")
        );
    }
}
