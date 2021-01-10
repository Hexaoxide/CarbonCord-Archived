package io.github.underscore11code.carboncord.common.events;

import io.github.underscore11code.carboncord.api.CarbonCord;
import io.github.underscore11code.carboncord.api.events.misc.CarbonCordEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

public class StartupEvent implements CarbonCordEvent {
  private final CarbonCord carbonCord;

  public StartupEvent(final @NonNull CarbonCord carbonCord) {
    this.carbonCord = carbonCord;
  }

  public @NonNull CarbonCord carbonCord() {
    return this.carbonCord;
  }
}
