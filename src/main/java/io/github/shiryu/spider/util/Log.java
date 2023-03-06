package io.github.shiryu.spider.util;

import lombok.experimental.UtilityClass;

import javax.annotation.Nonnull;
import java.util.logging.Level;
import java.util.logging.Logger;

@UtilityClass
public class Log {

    private final Logger LOGGER = Logger.getLogger("minecraft");

    public void info(@Nonnull String s) {
        LOGGER.info(s);
    }

    public void warn(@Nonnull String s) {
        LOGGER.warning(s);
    }

    public void severe(@Nonnull String s) {
        LOGGER.severe(s);
    }

    public void warn(@Nonnull String s, Throwable t) {
        LOGGER.log(Level.WARNING, s, t);
    }

    public void severe(@Nonnull String s, Throwable t) {
        LOGGER.log(Level.SEVERE, s, t);
    }
}
