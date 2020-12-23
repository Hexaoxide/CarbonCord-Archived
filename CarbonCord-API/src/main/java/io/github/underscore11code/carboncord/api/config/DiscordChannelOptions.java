package io.github.underscore11code.carboncord.api.config;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class DiscordChannelOptions {
  @Setting
  private @NonNull String carbonChannelKey = "";

  @Setting
  private @NonNull String discordChannelId = "";

  @Setting
  private @NonNull String key = "default";

  public @NonNull String carbonChannelKey() {
    return this.carbonChannelKey;
  }

  public @NonNull String discordChannelId() {
    return this.discordChannelId;
  }

  public String key() {
    return this.key;
  }
}
