package tr.com.aselsankadir.casestudy.infrastructure.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tr.com.aselsankadir.casestudy.domain.common.AselRuntimeException;
import tr.com.aselsankadir.casestudy.domain.user.AselUser;

public class AuthenticatedUserUtil {

    public static AselUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof AselUserDetails details)) {
            throw new AselRuntimeException("Kullanıcı doğrulanmamış veya geçersiz");
        }

        return details.getDomainUser();
    }

    public static String getCurrentEmail() {
        return getCurrentUser().getEmail().email();
    }
}
