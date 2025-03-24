package tr.com.aselsankadir.casestudy.infrastructure.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tr.com.aselsankadir.casestudy.infrastructure.jpa.AselDishEntity;
import tr.com.aselsankadir.casestudy.infrastructure.jpa.IAselDishRepository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class AselDishJdbcRepository extends AselJdbcRepository<AselDishEntity> implements IAselDishRepository {

    private final RowMapper<AselDishEntity> rowMapper;

    public AselDishJdbcRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.rowMapper = new AselDishMapper();
    }

    private static final String SQL_COLUMNS =
            "d.id, "
            + "d.restaurant_id, "
            + "d.title, "
            + "d.ingredients, "
            + "d.amount, "
            + "d.asel_menu_category_id, "
            + "mc.title as menu_category_title";

    private static final String SELECT_SQL =
            "SELECT "
            + SQL_COLUMNS
            + "FROM asel_dish d "
            + "JOIN asel_menu_category mc ON d.asel_menu_category_id = mc.id ";


    private static final String FIND_BY_ID_SQL =
            SELECT_SQL
                    + " WHERE d.restaurant_id = ? AND d.id = ? ";
    @Override
    public Optional<AselDishEntity> findById(Long restaurantId, Long dishId) {
        List<AselDishEntity> results = jdbcTemplate.query(FIND_BY_ID_SQL, rowMapper, restaurantId, dishId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }

}

class AselDishMapper implements RowMapper<AselDishEntity> {
    @Override
    public AselDishEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        AselDishEntity dish = new AselDishEntity();
        dish.setId(rs.getLong("id"));
        dish.setRestaurantId(rs.getLong("restaurant_id"));
        dish.setTitle(rs.getString("title"));
        dish.setIngredients(rs.getString("ingredients"));
        dish.setAmount(BigDecimal.valueOf(rs.getDouble("amount")));
        dish.setMenuCategoryId(rs.getLong("asel_menu_category_id"));
        dish.setMenuCategoryName(rs.getString("menu_category_title"));
        return dish;
    }
}

