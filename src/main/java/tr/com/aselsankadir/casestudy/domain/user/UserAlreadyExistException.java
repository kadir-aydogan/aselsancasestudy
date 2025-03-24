package tr.com.aselsankadir.casestudy.domain.user;

import tr.com.aselsankadir.casestudy.domain.common.AselRuntimeException;

public class UserAlreadyExistException extends AselRuntimeException {
    private static final String message = "Kullanıcı zaten kayıt olmuş.";

    public UserAlreadyExistException() {
        super(message);
    }
}
