package tr.com.aselsankadir.casestudy.domain.user;

import tr.com.aselsankadir.casestudy.domain.common.AselRuntimeException;

public class AselBadCredentialsException extends AselRuntimeException {
    private static final String message = "Yanlış kullanıcı adı ya da şifre";

    public AselBadCredentialsException() {
        super(message);
    }
}
