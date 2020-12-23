package io.github.underscore11code.carboncord.api.channels;

import io.github.underscore11code.carboncord.api.config.DiscordChannelOptions;
import net.draycia.carbon.api.channels.ChatChannel;
import net.dv8tion.jda.api.entities.TextChannel;

public interface DiscordChannel {
  TextChannel textChannel();

  ChatChannel chatChannel();

  DiscordChannelOptions channelOptions();

  void channelOptions(DiscordChannelOptions channelOptions);
}
