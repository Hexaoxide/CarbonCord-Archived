package io.github.underscore11code.carboncord.api.channels;

import io.github.underscore11code.carboncord.api.config.DiscordChannelOptions;
import net.draycia.carbon.api.channels.ChatChannel;
import net.draycia.carbon.api.users.PlayerUser;
import net.dv8tion.jda.api.entities.TextChannel;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface DiscordChannel {
  @NonNull TextChannel textChannel();

  @NonNull ChatChannel chatChannel();

  @NonNull DiscordChannelOptions channelOptions();

  void channelOptions(@NonNull DiscordChannelOptions channelOptions);

  void handleFromCarbon(@NonNull PlayerUser user, @NonNull String message);
}
