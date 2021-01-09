package io.github.underscore11code.carboncord.bukkit.listeners;

import io.github.underscore11code.carboncord.api.CarbonCordProvider;
import io.github.underscore11code.carboncord.api.channels.console.ConsoleLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;

// https://logging.apache.org/log4j/2.x/manual/extending.html#Appenders

@Plugin(name = "Stub", category = "Core", elementType = "appender", printObject = true)
public class LoggerListener extends AbstractAppender {

  @SuppressWarnings({"argument.type.incompatible", "deprecation"})
  public LoggerListener() {
    super("CarbonCord-Console", null, PatternLayout.newBuilder().withPattern("[%d{HH:mm:ss} %level]: %msg").build(), false);

    final Logger rootLogger = (Logger) LogManager.getRootLogger();
    rootLogger.addAppender(this);
  }

  @Override
  public void append(final LogEvent event) {
    ConsoleLine.Level level = null;
    switch (event.getLevel().getStandardLevel()) {
      case ERROR:
      case FATAL:
        level = ConsoleLine.Level.ERROR;
        break;
      case WARN:
        level = ConsoleLine.Level.WARN;
        break;
      case DEBUG:
      case TRACE:
      case ALL:
        level = ConsoleLine.Level.DEBUG;
        break;
      case INFO:
        level = ConsoleLine.Level.INFO;
        break;
    }

    if (level == null) return;

    CarbonCordProvider.carbonCord().consoleChannel().lineQueue().offer(new ConsoleLine(
      event.getLoggerName().replaceAll("net\\.minecraft\\.server\\.v.*\\.", ""),
      event.getMessage().getFormattedMessage(),
      level,
      event.getTimeMillis())
    );
  }

  @Override
  public boolean isStarted() {
    return true;
  }
}
