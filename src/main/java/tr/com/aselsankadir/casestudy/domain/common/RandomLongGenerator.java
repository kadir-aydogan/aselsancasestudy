package tr.com.aselsankadir.casestudy.domain.common;

import java.util.UUID;

public class RandomLongGenerator {
    public static Long getLong() {
        return Math.abs(UUID.randomUUID().getLeastSignificantBits());
    }
}
