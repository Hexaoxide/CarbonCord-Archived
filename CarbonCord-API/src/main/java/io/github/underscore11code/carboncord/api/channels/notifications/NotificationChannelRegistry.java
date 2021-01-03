package io.github.underscore11code.carboncord.api.channels.notifications;

import net.kyori.registry.Registry;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NotificationChannelRegistry implements Registry<String, NotificationChannel> {
  private final Map<String, NotificationChannel> channelMap = new HashMap<>();

  @Override
  public @NonNull NotificationChannel register(final @NonNull String key, final @NonNull NotificationChannel value) {
    this.channelMap.put(key, value);
    return value;
  }

  @Override
  public @Nullable NotificationChannel get(final @NonNull String key) {
    return this.channelMap.get(key);
  }

  @Override
  public @Nullable String key(final @NonNull NotificationChannel value) {
    for (final Map.Entry<String, NotificationChannel> entry : this.channelMap.entrySet()) {
      if (entry.getValue().equals(value)) return entry.getKey();
    }
    return null;
  }

  @Override
  public @NonNull Set<String> keySet() {
    return (Set<String>) this.channelMap.keySet(); // https://github.com/typetools/checker-framework/issues/3638
  }

  @Override
  public @NonNull Iterator<NotificationChannel> iterator() {
    return this.channelMap.values().iterator();
  }

  public Set<NotificationChannel> forType(final @NonNull String type) {
    final Set<NotificationChannel> channels = new HashSet<>();
    for (final NotificationChannel channel : this.channelMap.values()) {
      if (channel.channelOptions().type().equalsIgnoreCase(type)) {
        channels.add(channel);
      }
    }

    return channels;
  }
}
