package io.github.underscore11code.carboncord.api.events;

import io.github.underscore11code.carboncord.api.channels.DiscordChannel;
import io.github.underscore11code.carboncord.api.events.misc.CarbonCordEvent;
import net.draycia.carbon.api.users.CarbonUser;
import net.dv8tion.jda.api.entities.Message;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Fired after a message is sent to Discord
 */
public class DiscordPostMessageEvent implements CarbonCordEvent {
  private final @NonNull CarbonUser user;
  private final @NonNull Message message;
  private final @NonNull DiscordChannel channel;
  private final @NonNull String originalMessage;
  private final @NonNull String format;

  public DiscordPostMessageEvent(final @NonNull CarbonUser user,
                                 final @NonNull Message message,
                                 final @NonNull DiscordChannel channel,
                                 final @NonNull String originalMessage,
                                 final @NonNull String format) {
    this.user = user;
    this.message = message;
    this.channel = channel;
    this.originalMessage = originalMessage;
    this.format = format;
  }

  public @NonNull CarbonUser user() {
    return this.user;
  }

  public @NonNull Message message() {
    return this.message;
  }

  public @NonNull DiscordChannel channel() {
    return this.channel;
  }

  public @NonNull String originalMessage() {
    return this.originalMessage;
  }

  public @NonNull String format() {
    return this.format;
  }
}
