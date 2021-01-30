package io.github.underscore11code.carboncord.common.commands;

import io.github.underscore11code.carboncord.api.CarbonCord;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CommandRegistrar {

  private CommandRegistrar() {

  }

  public static void registerCommands(final @NonNull CarbonCord carbonCord) {
    new DebugCommand(carbonCord);
    new ReloadCommand(carbonCord);
  }
}
