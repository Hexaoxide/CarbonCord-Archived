package io.github.underscore11code.carboncord.api.channels.console;

import io.github.underscore11code.carboncord.api.util.PlaceholderUtil;
import io.github.underscore11code.carboncord.api.util.PrettyUtil;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ConsoleLine {
  private final @NonNull String logger;
  private final @NonNull String message;
  private final @NonNull Level level;
  private final long timestamp;

  public ConsoleLine(final @NonNull String logger,
                     final @NonNull String message,
                     final @NonNull Level level,
                     final long timestamp) {
    this.logger = logger;
    this.message = message;
    this.level = level;
    this.timestamp = timestamp;
  }

  public String logger() {
    return this.logger;
  }

  public String message() {
    return this.message;
  }

  public Level level() {
    return this.level;
  }

  public long timestamp() {
    return this.timestamp;
  }

  public String beautify(final @NonNull String format) {
    String message = PlaceholderUtil.setPlaceholders(format,
      "level", this.level().display(),
      "logger-name", this.logger(),
      "message", this.message());
    message = PrettyUtil.stripColorCodes(message);
    return message;
  }

  public enum Level {
    DEBUG("DEBUG"),
    INFO("INFO "),
    WARN("WARN "),
    ERROR("ERROR");

    private final String display;

    Level(final String display) {
      this.display = display;
    }

    public String display() {
      return this.display;
    }
  }
}
