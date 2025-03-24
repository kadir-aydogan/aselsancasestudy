package tr.com.aselsankadir.casestudy.domain.user;

import tr.com.aselsankadir.casestudy.domain.common.RandomLongGenerator;

public class UserId {
    private Long id;

    public Long getId() {
        return id;
    }

    public static UserId create() {
        return new UserId(RandomLongGenerator.getLong());
    }

    public static UserId restore(Long id) {
        return new UserId(id);
    }

    private UserId(Long id ) {
        this.id = id;
    }
}
