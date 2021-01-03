package io.github.underscore11code.carboncord.api.config;

import com.google.common.collect.ImmutableMap;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.Map;

@ConfigSerializable
public class DiscordChannelOptions {
  @Setting
  private @NonNull String carbonChannelKey = "";

  @Setting
  private @NonNull String discordChannelId = "";

  @Setting
  private @NonNull String key = "default";

  @Setting
  private @NonNull Map<String, String> toDiscordFormats = ImmutableMap.of("default", "");

  @Setting
  private @NonNull Map<String, String> toCarbonFormats = ImmutableMap.of("default", "");

  @Setting
  private @NonNull String defaultFormatName = "";

  public String carbonChannelKey() {
    return this.carbonChannelKey;
  }

  public void carbonChannelKey(final String carbonChannelKey) {
    this.carbonChannelKey = carbonChannelKey;
  }

  public String discordChannelId() {
    return this.discordChannelId;
  }

  public void discordChannelId(final String discordChannelId) {
    this.discordChannelId = discordChannelId;
  }

  public String key() {
    return this.key;
  }

  public void key(final String key) {
    this.key = key;
  }

  public Map<String, String> toDiscordFormats() {
    return this.toDiscordFormats;
  }

  public void toDiscordFormats(final Map<String, String> toDiscordFormats) {
    this.toDiscordFormats = toDiscordFormats;
  }

  public Map<String, String> toCarbonFormats() {
    return this.toCarbonFormats;
  }

  public void toCarbonFormats(final Map<String, String> toCarbonFormats) {
    this.toCarbonFormats = toCarbonFormats;
  }

  public String defaultFormatName() {
    return this.defaultFormatName;
  }

  public void defaultFormatName(final String defaultFormatName) {
    this.defaultFormatName = defaultFormatName;
  }
}
