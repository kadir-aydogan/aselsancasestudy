package tr.com.aselsankadir.casestudy.infrastructure.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tr.com.aselsankadir.casestudy.domain.menu.AselRestaurantMenu;
import tr.com.aselsankadir.casestudy.domain.menu.IAselRestaurantMenuRepository;
import tr.com.aselsankadir.casestudy.domain.menu.MenuId;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public class AselRestaurantMenuJdbcRepository extends AselJdbcRepository<AselRestaurantMenu> implements IAselRestaurantMenuRepository {

    private final RowMapper<AselRestaurantMenu> rowMapper;

    public AselRestaurantMenuJdbcRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.rowMapper = new AselRestaurantMenuRowMapper();
    }

    private static final String SQL_COLUMNS =
            "t.id, " +
            "t.restaurant_id, " +
            "t.title, " +
            "t.description, " +
            "t.date ";

    private static final String SELECT_SQL =
            "SELECT "
            + SQL_COLUMNS
            + "FROM asel_restaurant_menu t ";

    private static final String FIND_ALL_BY_DATE_SQL =
            SELECT_SQL
            + " WHERE t.restaurant_id = ? and t.date = ?";

    @Override
    public Optional<AselRestaurantMenu> findAllByDate(RestaurantId restaurantId, LocalDate date) {
        return super.queryForOptional(FIND_ALL_BY_DATE_SQL, this.rowMapper, restaurantId.getValue(), date);
    }
}
class AselRestaurantMenuRowMapper implements RowMapper<AselRestaurantMenu> {

    @Override
    public AselRestaurantMenu mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AselRestaurantMenu(
                new MenuId(rs.getLong("id")),
                rs.getString("title"),
                rs.getString("description"),
                rs.getObject("date", LocalDate.class),
                new ArrayList<>(),
                new RestaurantId(rs.getLong("restaurant_id"))
        );
    }
}