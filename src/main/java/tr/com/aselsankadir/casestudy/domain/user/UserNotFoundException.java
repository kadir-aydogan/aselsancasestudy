package tr.com.aselsankadir.casestudy.domain.user;

import tr.com.aselsankadir.casestudy.domain.common.AselRuntimeException;

public class UserNotFoundException extends AselRuntimeException {

    private static final String message = "Kullanıcı bulunamadı!";

    public UserNotFoundException() {
        super(message);
    }
}
