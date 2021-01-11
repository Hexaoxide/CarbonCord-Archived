package io.github.underscore11code.carboncord.api.config;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class ConsoleChannelOptions {
  @Setting
  private @NonNull String channelId = "";

  @Setting
  private @NonNull String format = "```[<level>|<logger-name>] <message>```";

  public String channelId() {
    return this.channelId;
  }

  public void channelId(final @NonNull String channelId) {
    this.channelId = channelId;
  }

  public String format() {
    return this.format;
  }

  public void format(final @NonNull String format) {
    this.format = format;
  }
}
