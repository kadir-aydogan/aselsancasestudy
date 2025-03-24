package tr.com.aselsankadir.casestudy.infrastructure.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tr.com.aselsankadir.casestudy.domain.dish.DishId;
import tr.com.aselsankadir.casestudy.domain.menu.MenuId;
import tr.com.aselsankadir.casestudy.domain.menucategory.MenuCategoryId;
import tr.com.aselsankadir.casestudy.domain.menuitem.AselMenuItem;
import tr.com.aselsankadir.casestudy.domain.menuitem.IAselMenuItemRepository;
import tr.com.aselsankadir.casestudy.domain.menuitem.MenuItemId;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class AselMenuItemJdbcRepository extends AselJdbcRepository<AselMenuItem> implements IAselMenuItemRepository {

    private final RowMapper<AselMenuItem> rowMapper;

    public AselMenuItemJdbcRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.rowMapper = new MenuItemRowMapper();
    }

    private static final String SQL_COLUMNS =
            "t.id, "
            + "t.asel_restaurant_menu_id, "
            + "t.restaurant_id, "
            + "COALESCE(t.amount, d.amount) AS amount, "
            + "t.asel_dish_id, "
            + "d.title, "
            + "d.ingredients, "
            + "mc.id as menu_category_id, "
            + "mc.title as menu_category_title ";

    private static final String SELECT_SQL =
            "SELECT "
            + SQL_COLUMNS
            + "FROM asel_menu_item t "
//            + "JOIN asel_restaurant_menu rm " +
//                    "ON (rm.restaurant_id = t.restaurant_id " +
//                    "AND rm.id = t.asel_restaurant_menu_id) "
            + "JOIN asel_dish d ON "
            + "     (t.restaurant_id = d.asel_restaurant_id "
            + "     AND t.asel_dish_id = d.id) "
            + "JOIN asel_menu_category mc ON "
            + "     (mc.restaurant_id = d.asel_restaurant_id "
            + "     AND d.asel_menu_category_id = mc.id) ";

    private static final String FIND_BY_ID_SQL =
            SELECT_SQL
            + "WHERE t.restaurant_id = ? and t.id = ? ";

    @Override
    public Optional<AselMenuItem> findById(RestaurantId restaurantId, MenuItemId menuItemId) {
        return super.queryForOptional(FIND_BY_ID_SQL, rowMapper, restaurantId.getValue(), menuItemId.getValue());
    }

    private static final String FIND_ALL_BY_MENU_ID_SQL =
            SELECT_SQL
            + "WHERE t.restaurant_id = ? AND t.asel_restaurant_menu_id = ? ";

    @Override
    public List<AselMenuItem> findAllByMenuId(RestaurantId restaurantId, MenuId menuId) {
        return jdbcTemplate.query(FIND_ALL_BY_MENU_ID_SQL, this.rowMapper, restaurantId.getValue(), menuId.getValue());
    }
}

class MenuItemRowMapper implements RowMapper<AselMenuItem> {

    @Override
    public AselMenuItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AselMenuItem(
                new MenuItemId(rs.getLong("id")),
                rs.getBigDecimal("amount"),
                new RestaurantId(rs.getLong("restaurant_id")),
                new DishId(rs.getLong("asel_dish_id")),
                rs.getString("title"),                        // dishTitle
                rs.getString("ingredients"),
                new MenuId(rs.getLong("asel_restaurant_menu_id")),
                new MenuCategoryId(rs.getLong("menu_category_id")),
                rs.getString("menu_category_title")
        );
    }
}


