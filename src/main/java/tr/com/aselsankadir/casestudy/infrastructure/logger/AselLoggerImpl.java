package tr.com.aselsankadir.casestudy.infrastructure.logger;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tr.com.aselsankadir.casestudy.domain.common.IAselLogger;

@Component
public class AselLoggerImpl implements IAselLogger {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("Application");

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void info(String format, Object... args) {
        logger.info(format, args);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void warn(String format, Object... args) {
        logger.warn(format, args);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void error(String format, Object... args) {
        logger.error(format, args);
    }

    @Override
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
