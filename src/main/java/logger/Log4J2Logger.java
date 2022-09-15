package logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4J2Logger implements ILogger {
    private Logger logger;

    public Log4J2Logger() {
        this.logger = LogManager.getRootLogger();
    }

    @Override
    public void logInfo(String message) {
        logger.info(message);
    }

    @Override
    public void logError(String message) {
        logger.error(message);
    }
}
