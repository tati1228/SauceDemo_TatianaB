package logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLogger {
    private final Logger logger = LogManager.getRootLogger();

    // Write INFO message to log
    public void logInfo(String message) {
        logger.info(message);
    }

    // Write ERROR message to log
    public void logError(String message) {
        logger.error(message);
    }
}
