package tr.com.aselsankadir.casestudy.infrastructure.repository;

import org.springframework.jdbc.core.JdbcTemplate;

public class SequenceIdGenerator {
    public static Long next(JdbcTemplate jdbc, String seqName) {
        return jdbc.queryForObject("SELECT NEXT VALUE FOR " + seqName, Long.class);
    }
}

