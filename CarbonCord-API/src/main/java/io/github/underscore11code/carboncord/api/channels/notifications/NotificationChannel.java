package io.github.underscore11code.carboncord.api.channels.notifications;

import io.github.underscore11code.carboncord.api.CarbonCordProvider;
import io.github.underscore11code.carboncord.api.config.NotificationChannelOptions;
import net.dv8tion.jda.api.entities.TextChannel;
import net.kyori.registry.Registry;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

public abstract class NotificationChannel {
  private static final @NonNull Registry<String, Class<? extends NotificationChannel>> typeRegistry = Registry.create();
  private NotificationChannelOptions channelOptions;

  public NotificationChannel(final @NonNull NotificationChannelOptions channelOptions) {
    this.channelOptions = channelOptions;
  }

  public static @NonNull Registry<String, Class<? extends NotificationChannel>> typeRegistry() {
    return typeRegistry;
  }

  public static @Nullable NotificationChannel of(final @NonNull NotificationChannelOptions channelOptions) throws Exception {
    final Class<? extends NotificationChannel> clazz = typeRegistry().get(channelOptions.type());

    if (clazz == null) {
      return null;
    }

    return clazz.getConstructor(NotificationChannelOptions.class).newInstance(channelOptions);
  }

  // --- Static Getters ---

  public @NotNull NotificationChannelOptions channelOptions() {
    return this.channelOptions;
  }

  public void channelOptions(final @NotNull NotificationChannelOptions channelOptions) {
    this.channelOptions = channelOptions;
  }

  public @NonNull TextChannel textChannel() throws IllegalArgumentException {
    try {
      final TextChannel textChannel = CarbonCordProvider.carbonCord().jda().getTextChannelById(this.channelOptions.channelId());
      if (textChannel == null) {
        throw new IllegalArgumentException("Unknown channel: " + this.channelOptions.channelId());
      }
      return textChannel;

    } catch (final Exception e) {
      throw new IllegalArgumentException("Unknown channel: " + this.channelOptions.channelId(), e);
    }
  }
}
