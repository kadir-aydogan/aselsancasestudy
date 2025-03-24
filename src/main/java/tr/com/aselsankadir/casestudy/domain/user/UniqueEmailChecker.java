package tr.com.aselsankadir.casestudy.domain.user;

import tr.com.aselsankadir.casestudy.domain.common.Email;

public interface UniqueEmailChecker {
    boolean isEmailAvailable(Email email);
}
