package io.github.underscore11code.carboncord.api.channels;

import io.github.underscore11code.carboncord.api.misc.BiRegistryGetter;
import net.draycia.carbon.api.channels.ChatChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.kyori.registry.Registry;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DiscordChannelRegistry implements Registry<String, DiscordChannel>, BiRegistryGetter<String, TextChannel, ChatChannel> {
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

  @Override
  public @Nullable String ofA(final @NonNull TextChannel textChannel) {
    for (final Map.Entry<String, DiscordChannel> entry : this.channelMap.entrySet()) {
      if (entry.getValue().channelOptions().discordChannelId().equals(textChannel.getId())) {
        return entry.getKey();
      }
    }
    return null;
  }

  @Override
  public @Nullable String ofB(final @NonNull ChatChannel chatChannel) {
    for (final Map.Entry<String, DiscordChannel> entry : this.channelMap.entrySet()) {
      if (entry.getValue().channelOptions().carbonChannelKey().equals(chatChannel.key())) {
        return entry.getKey();
      }
    }
    return null;
  }

  @Override
  public @Nullable TextChannel a(final @NonNull String key) {
    final DiscordChannel channel = this.get(key);
    if (channel != null) {
      return channel.textChannel();
    }
    return null;
  }

  @Override
  public @Nullable ChatChannel b(final @NonNull String key) {
    final DiscordChannel channel = this.get(key);
    if (channel != null) {
      return channel.chatChannel();
    }
    return null;
  }

  @Override
  public @Nullable TextChannel toA(final @NonNull ChatChannel chatChannel) {
    final String key = this.ofB(chatChannel);
    if (key != null) {
      return this.a(key);
    }
    return null;
  }

  @Override
  public @Nullable ChatChannel toB(final @NonNull TextChannel textChannel) {
    final String key = this.ofA(textChannel);
    if (key != null) {
      return this.b(key);
    }
    return null;
  }
}
