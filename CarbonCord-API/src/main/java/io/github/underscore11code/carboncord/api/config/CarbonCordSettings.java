package io.github.underscore11code.carboncord.api.config;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.objectmapping.meta.Setting;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.HashMap;
import java.util.Map;

@ConfigSerializable
public final class CarbonCordSettings {

  private static final ObjectMapper<CarbonCordSettings> MAPPER;

  static {
    try {
      MAPPER = ObjectMapper.factory().get(CarbonCordSettings.class);
    } catch (final SerializationException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  public static CarbonCordSettings loadFrom(final CommentedConfigurationNode node) throws SerializationException {
    return MAPPER.load(node);
  }

  public void saveTo(final CommentedConfigurationNode node) throws SerializationException {
    MAPPER.save(this, node);
  }

  @Setting
  private @NonNull String botToken = "BOTTOKEN";

  @Setting
  private @NonNull Map<@NonNull String, @NonNull DiscordChannelOptions> discordChannelSettings = new HashMap<>();

  public @NonNull String botToken() {
    return this.botToken;
  }

  public @NonNull Map<@NonNull String, @NonNull DiscordChannelOptions> discordChannelSettings() {
    return this.discordChannelSettings;
  }

}
