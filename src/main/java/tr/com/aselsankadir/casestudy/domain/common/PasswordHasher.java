package tr.com.aselsankadir.casestudy.domain.common;

public interface PasswordHasher {
    boolean matches(String raw, String hashed);
    String hash(String has);
}
