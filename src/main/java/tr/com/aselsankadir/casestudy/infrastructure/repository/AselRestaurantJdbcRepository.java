package tr.com.aselsankadir.casestudy.infrastructure.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tr.com.aselsankadir.casestudy.domain.restaurant.AselRestaurant;
import tr.com.aselsankadir.casestudy.domain.restaurant.IRestaurantRepository;

@Repository
public class AselRestaurantJdbcRepository extends AselJdbcRepository<AselRestaurant> implements IRestaurantRepository {

    public AselRestaurantJdbcRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Long save(AselRestaurant aselRestaurant) {
        return null;
    }
}
