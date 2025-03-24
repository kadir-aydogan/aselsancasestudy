package tr.com.aselsankadir.casestudy.domain.user;

import tr.com.aselsankadir.casestudy.domain.common.AselDomainException;
import tr.com.aselsankadir.casestudy.domain.common.Email;
import tr.com.aselsankadir.casestudy.domain.common.PasswordHasher;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

public class AselUser {

    private final UserId id;

    private final Email email;

    private final String hashedPassword;

    private final Role role;

    private final RestaurantId restaurantId;

    public boolean authenticate(PasswordHasher passwordHasher, String rawPassword) {
        return passwordHasher.matches(rawPassword, hashedPassword);
    }

    private AselUser(UserId userId, Email email, String hashedPassword, Role role, RestaurantId restaurantId) {
        if (role == null) {
            throw new AselDomainException("Kullanıcının rolü boş olamaz.");
        }
        if (email == null)
            throw new AselDomainException("Kullanıcı emaili boş olamaz.");
        if (userId == null) {
            throw new AselDomainException("Kullanıcı ID boş olamaz.");
        }
        this.id = userId;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.role = role;
        this.restaurantId = restaurantId;
    }

    public static AselUser create(Email email, String rawPassword, Role role, PasswordHasher hasher, UniqueEmailChecker uniqueEmailChecker) {
        if (rawPassword == null || rawPassword.length() < 6)
            throw new AselDomainException("Şifre 6 haneden küçük olamaz.");

        if (!uniqueEmailChecker.isEmailAvailable(email))
            throw new AselDomainException("Bu email ile kayıtlı bir kullanıcı bulunmakta");

        String hashed = hasher.hash(rawPassword);
        return new AselUser(UserId.create(), email, hashed, role, null);
    }

    public static AselUser restore(UserId userId, Email email, String hashedPassword, Role role, RestaurantId restaurantId) {
        return new AselUser(userId, email, hashedPassword, role, restaurantId);
    }


    public UserId getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public Role getRole() {
        return role;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }
}
