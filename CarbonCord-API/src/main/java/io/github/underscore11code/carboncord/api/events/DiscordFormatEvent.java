package io.github.underscore11code.carboncord.api.events;

import io.github.underscore11code.carboncord.api.channels.DiscordChannel;
import io.github.underscore11code.carboncord.api.events.misc.CarbonCordEvent;
import net.draycia.carbon.api.users.PlayerUser;
import net.kyori.event.Cancellable;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Event for formatting a message Carbon -> Discord
 */
public class DiscordFormatEvent implements CarbonCordEvent, Cancellable {
  private boolean cancelled = false;
  private final @NonNull PlayerUser user;
  private @NonNull DiscordChannel channel;
  private @NonNull String format;
  private @NonNull String message;

  public DiscordFormatEvent(final @NonNull PlayerUser user,
                            final @NonNull DiscordChannel channel,
                            final @NonNull String format,
                            final @NonNull String message) {
    this.user = user;
    this.channel = channel;
    this.format = format;
    this.message = message;
  }

  @Override
  public boolean cancelled() {
    return this.cancelled;
  }

  public @NonNull PlayerUser user() {
    return this.user;
  }

  public @NonNull DiscordChannel channel() {
    return this.channel;
  }

  public @NonNull String format() {
    return this.format;
  }

  public @NonNull String message() {
    return this.message;
  }

  @Override
  public void cancelled(final boolean cancelled) {
    this.cancelled = cancelled;
  }

  public void channel(final @NonNull DiscordChannel channel) {
    this.channel = channel;
  }

  public void format(final @NonNull String format) {
    this.format = format;
  }

  public void message(final @NonNull String message) {
    this.message = message;
  }
}
