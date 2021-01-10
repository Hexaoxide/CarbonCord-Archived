package io.github.underscore11code.carboncord.common.listeners;

import io.github.underscore11code.carboncord.api.CarbonCord;
import io.github.underscore11code.carboncord.api.channels.DiscordChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class DiscordMessageListener extends ListenerAdapter {
  private final CarbonCord carbonCord;

  @SuppressWarnings("argument.type.incompatible")
  public DiscordMessageListener(final CarbonCord carbonCord) {
    this.carbonCord = carbonCord;
    this.carbonCord.jda().addEventListener(this);
  }

  @Override
  @SuppressWarnings("argument.type.incompatible")
  public void onGuildMessageReceived(final @NotNull GuildMessageReceivedEvent e) {
    // Discord convention dictates bots should ignore other bots.
    if (e.getAuthor().isBot() || e.isWebhookMessage()) return;

    final Set<DiscordChannel> discordChannels = this.carbonCord.discordChannelRegistry().channelsFor(e.getChannel());
    final Member member = e.getMember();
    if (member == null) {
      this.carbonCord.logger().error("Null member despite listening to the GuildMessageReceivedEvent. This shouldn't be possible");
      this.carbonCord.logger().error("Message {} ({}), User {}, Channel {}",
        e.getMessage(),
        e.getMessageId(),
        e.getAuthor().getId(),
        e.getChannel().getId());
      return;
    }

    for (final DiscordChannel discordChannel : discordChannels) {
      discordChannel.handleFromDiscord(e.getMember(), e.getMessage().getContentDisplay());
    }
  }
}
