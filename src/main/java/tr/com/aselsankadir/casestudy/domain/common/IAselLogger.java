package tr.com.aselsankadir.casestudy.domain.common;

public interface IAselLogger {
    void info(String message);
    void info(String format, Object... args);

    void warn(String message);
    void warn(String format, Object... args);

    void error(String message);
    void error(String format, Object... args);
    void error(String message, Throwable throwable);
}
