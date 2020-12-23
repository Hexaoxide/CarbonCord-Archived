package io.github.underscore11code.carboncord.api.channels;

import net.draycia.carbon.api.channels.ChatChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.kyori.registry.Registry;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

public class DiscordChannelRegistry implements Registry<String, DiscordChannel> {
  private final Map<String, DiscordChannel> channelMap = new HashMap<>();

  @Override
  public @NonNull DiscordChannel register(final @NonNull String key, final @NonNull DiscordChannel value) {
    this.channelMap.put(key, value);
    return value;
  }

  @Override
  public @Nullable DiscordChannel get(final @NonNull String key) {
    return this.channelMap.get(key);
  }

  @Override
  public @Nullable String key(final @NonNull DiscordChannel value) {
    for (final Map.Entry<String, DiscordChannel> entry : this.channelMap.entrySet()) {
      if (entry.getValue().equals(value)) {
        return entry.getKey();
      }
    }
    return null;
  }

  @Override
  public @NonNull Set<String> keySet() {
    return (Set<String>) this.channelMap.keySet(); // https://github.com/typetools/checker-framework/issues/3638
  }

  @Override
  public @NonNull Iterator<DiscordChannel> iterator() {
    return this.channelMap.values().iterator();
  }

  public @NonNull Set<String> keysFor(final @NonNull TextChannel channel) {
    final Set<String> keys = new HashSet<>();

    for (final Map.Entry<String, DiscordChannel> entry : this.channelMap.entrySet()) {
      if (entry.getValue().textChannel().getId().equals(channel.getId())) {
        keys.add(entry.getKey());
      }
    }
    return keys;
  }

  public @NonNull Set<DiscordChannel> channelsFor(final @NonNull TextChannel channel) {
    final Set<DiscordChannel> channels = new HashSet<>();

    for (final String key : this.keysFor(channel)) {
      final DiscordChannel discordChannel = this.get(key);
      if (discordChannel != null) {
        channels.add(discordChannel);
      }
    }

    return channels;
  }

  public @NonNull Set<String> keysFor(final @NonNull ChatChannel channel) {
    final Set<String> keys = new HashSet<>();

    for (final Map.Entry<String, DiscordChannel> entry : this.channelMap.entrySet()) {
      if (entry.getValue().chatChannel().key().equals(channel.key())) {
        keys.add(entry.getKey());
      }
    }
    return keys;
  }

  public @NonNull Set<DiscordChannel> channelsFor(final @NonNull ChatChannel channel) {
    final Set<DiscordChannel> channels = new HashSet<>();

    for (final String key : this.keysFor(channel)) {
      final DiscordChannel discordChannel = this.get(key);
      if (discordChannel != null) {
        channels.add(discordChannel);
      }
    }

    return channels;
  }
}
