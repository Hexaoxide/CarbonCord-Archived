package io.github.underscore11code.carboncord.api.config;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class NotificationChannelOptions {

  @Setting
  @NonNull
  private String type = "";

  @Setting
  @NonNull
  private String channelId = "";

  @Setting
  @NonNull
  private String format = "";

  @Setting
  @NonNull
  private String key = "";

  public @NonNull String type() {
    return this.type;
  }

  public void type(final @NonNull String type) {
    this.type = type;
  }

  public @NonNull String channelId() {
    return this.channelId;
  }

  public void channelId(final @NonNull String channelId) {
    this.channelId = channelId;
  }

  public @NonNull String format() {
    return this.format;
  }

  public void format(final @NonNull String format) {
    this.format = format;
  }

  public @NonNull String key() {
    return this.key;
  }

  public void key(final @NonNull String key) {
    this.key = key;
  }
}
