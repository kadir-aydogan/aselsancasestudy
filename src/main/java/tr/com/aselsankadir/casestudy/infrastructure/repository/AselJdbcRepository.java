package tr.com.aselsankadir.casestudy.infrastructure.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

public abstract class AselJdbcRepository<T> {
    protected final JdbcTemplate jdbcTemplate;

    public AselJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<T> queryForOptional(String sql, RowMapper rowMapper, Object... args) {
        List<T> results = jdbcTemplate.query(sql, rowMapper, args);
        if (results.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(results.getFirst());
    }
}
