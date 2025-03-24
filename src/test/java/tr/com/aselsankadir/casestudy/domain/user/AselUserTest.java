package tr.com.aselsankadir.casestudy.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tr.com.aselsankadir.casestudy.domain.common.AselDomainException;
import tr.com.aselsankadir.casestudy.domain.common.Email;
import tr.com.aselsankadir.casestudy.domain.common.PasswordHasher;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AselUserTest {

    private final UserId id = UserId.create();
    private final Email email = new Email("kadir@test.com");
    private final String rawPassword = "secure123";
    private final String hashedPassword = "hashed_secure123";

    Role role = Role.ROLE_MANAGER;

    private PasswordHasher hasher;
    private UniqueEmailChecker emailChecker;

    @BeforeEach
    void setUp() {
        hasher = new PasswordHasher() {
            @Override
            public String hash(String rawPassword) {
                return "hashed_" + rawPassword;
            }

            @Override
            public boolean matches(String rawPassword, String hashedPassword) {
                return hashedPassword.equals("hashed_" + rawPassword);
            }
        };

        emailChecker = mock(UniqueEmailChecker.class);
    }

    @Test
    void create_shouldReturnUserWithHashedPasswordAndGivenRole() {

        when(emailChecker.isEmailAvailable(email)).thenReturn(true);

        AselUser user = AselUser.create(email, rawPassword, role, hasher, emailChecker);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(hashedPassword, user.getHashedPassword());
        assertEquals(role, user.getRole());
        assertNotNull(user.getId());
        assertNull(user.getRestaurantId());
    }

    @Test
    void create_shouldThrowExceptionForWeakPassword() {
        AselDomainException ex = assertThrows(AselDomainException.class, () ->
                AselUser.create(email, "123", Role.ROLE_MANAGER, hasher, emailChecker)
        );
        assertEquals("Şifre 6 haneden küçük olamaz.", ex.getMessage());
    }

    @Test
    void create_shouldThrowExceptionIfEmailAlreadyExists() {
        when(emailChecker.isEmailAvailable(email)).thenReturn(false);

        AselDomainException ex = assertThrows(AselDomainException.class, () ->
                AselUser.create(email, rawPassword, role, hasher, emailChecker)
        );
        assertEquals("Bu email ile kayıtlı bir kullanıcı bulunmakta", ex.getMessage());
    }

    @Test
    void restore_shouldRecreateUserFromStoredData() {
        RestaurantId restaurantId = new RestaurantId(100L);

        AselUser user = AselUser.restore(id, email, hashedPassword, role, restaurantId);

        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(hashedPassword, user.getHashedPassword());
        assertEquals(role, user.getRole());
        assertEquals(restaurantId, user.getRestaurantId());
    }

    @Test
    void restore_shouldThrowExceptionWhenRoleIsNull() {
        AselDomainException ex = assertThrows(AselDomainException.class, () ->
                AselUser.restore(id, email, hashedPassword, null, null)
        );
        assertEquals("Kullanıcının rolü boş olamaz.", ex.getMessage());
    }

    @Test
    void authenticate_shouldReturnTrueIfPasswordMatches() {
        when(emailChecker.isEmailAvailable(email)).thenReturn(true);
        AselUser user = AselUser.create(email, rawPassword, role, hasher, emailChecker);

        assertTrue(user.authenticate(hasher, rawPassword));
    }

    @Test
    void authenticate_shouldReturnFalseIfPasswordDoesNotMatch() {
        when(emailChecker.isEmailAvailable(email)).thenReturn(true);
        AselUser user = AselUser.create(email, rawPassword, role, hasher, emailChecker);

        assertFalse(user.authenticate(hasher, "wrongpass"));
    }
}
