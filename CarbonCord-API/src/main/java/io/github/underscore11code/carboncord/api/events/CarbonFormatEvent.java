package io.github.underscore11code.carboncord.api.events;

import io.github.underscore11code.carboncord.api.channels.DiscordChannel;
import io.github.underscore11code.carboncord.api.events.misc.CarbonCordEvent;
import net.dv8tion.jda.api.entities.Member;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Event for formatting a message Discord -> Carbon
 */
public class CarbonFormatEvent implements CarbonCordEvent {
  private boolean cancelled = false;
  private final @NonNull Member member;
  private final @NonNull DiscordChannel channel;
  private @NonNull String format;
  private @NonNull String message;

  public CarbonFormatEvent(final @NonNull Member member,
                           final @NonNull DiscordChannel channel,
                           final @NonNull String format,
                           final @NonNull String message) {
    this.member = member;
    this.channel = channel;
    this.format = format;
    this.message = message;
  }

  public boolean cancelled() {
    return this.cancelled;
  }

  public void cancelled(final boolean cancelled) {
    this.cancelled = cancelled;
  }

  public Member member() {
    return this.member;
  }

  public DiscordChannel channel() {
    return this.channel;
  }

  public String format() {
    return this.format;
  }

  public void format(final String format) {
    this.format = format;
  }

  public String message() {
    return this.message;
  }

  public void message(final String message) {
    this.message = message;
  }
}
