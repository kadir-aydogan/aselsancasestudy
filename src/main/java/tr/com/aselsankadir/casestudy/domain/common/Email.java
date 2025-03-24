package tr.com.aselsankadir.casestudy.domain.common;

public record Email(String email) {

    public Email {
        if (email == null || !email.contains("@") || email.length() < 6) {
            throw new AselRuntimeException("Invalid email!");
        }
    }
}
