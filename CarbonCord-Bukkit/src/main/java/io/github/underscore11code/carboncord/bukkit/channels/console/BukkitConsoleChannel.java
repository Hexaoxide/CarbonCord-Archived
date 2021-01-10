package io.github.underscore11code.carboncord.bukkit.channels.console;

import io.github.underscore11code.carboncord.bukkit.CarbonCordBukkit;
import io.github.underscore11code.carboncord.common.console.AbstractConsoleChannel;
import org.checkerframework.checker.nullness.qual.NonNull;

public class BukkitConsoleChannel extends AbstractConsoleChannel {
  private final CarbonCordBukkit carbonCordBukkit;

  public BukkitConsoleChannel(final @NonNull CarbonCordBukkit carbonCordBukkit) {
    super(carbonCordBukkit);

    this.carbonCordBukkit = carbonCordBukkit;
  }

  @Override
  public void runConsoleCommand(final @NonNull String command) {
    this.carbonCordBukkit.getServer().getScheduler().runTask(this.carbonCordBukkit, () ->
      this.carbonCordBukkit.getServer().dispatchCommand(this.carbonCordBukkit.getServer().getConsoleSender(), command)
    );
  }
}
