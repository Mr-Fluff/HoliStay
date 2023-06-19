package anu.edu.cecs.holistay;

import android.util.Log;

/**
 * Custom Logger
 * @author Nakul Nambiar (u7433687)
 */
public class Logger {
    private static Logger logger = null;

    private Logger() { }

    public static Logger getInstance() {
        if(logger == null) return new Logger();
        else return logger;
    }

    public void info(Class className, String message) {
        Log.println(Log.INFO, className.getSimpleName(), message);
    }

    public void error(Class className, Exception cause) {
        Log.println(Log.ERROR, className.getSimpleName(), Log.getStackTraceString(cause));
    }
}
