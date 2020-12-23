package io.github.underscore11code.carbonchat.common.listeners;

import io.github.underscore11code.carbonchat.common.util.PlaceholderUtil;
import io.github.underscore11code.carboncord.api.CarbonCord;
import io.github.underscore11code.carboncord.api.channels.DiscordChannel;
import io.github.underscore11code.carboncord.api.events.DiscordFormatEvent;
import io.github.underscore11code.carboncord.api.events.DiscordPostMessageEvent;
import io.github.underscore11code.carboncord.api.events.misc.CarbonCordEvents;
import net.draycia.carbon.api.events.PreChatFormatEvent;
import net.draycia.carbon.api.events.misc.CarbonEvents;
import net.kyori.event.PostOrders;

public class CarbonMessageListener {
  private final CarbonCord carbonCord;

  @SuppressWarnings("methodref.receiver.bound.invalid")
  public CarbonMessageListener(final CarbonCord carbonCord) {
    this.carbonCord = carbonCord;

    CarbonEvents.register(PreChatFormatEvent.class, PostOrders.LAST, false, this::onCarbonMessage);
  }

  private void onCarbonMessage(final PreChatFormatEvent e) {
    this.carbonCord.logger().debug("Message {}", e.message());
    this.carbonCord.logger().error("testing testing");
    // Shadow muted check since the event still goes through
    if (e.user().shadowMuted()) {
      this.carbonCord.logger().debug("Not handling message {} because the user was shadow muted", e.message());
    }

    // Is the message to a linked channel?
    final String key = this.carbonCord.discordChannelRegistry().ofB(e.channel());
    if (key == null) {
      this.carbonCord.logger().debug("Not handling message {} to a null DiscordChannel key", e.message());
      return;
    }

    // Is there a valid channel?
    final DiscordChannel channel = this.carbonCord.discordChannelRegistry().get(key);
    if (channel == null) {
      throw new IllegalStateException("Unreachable code, in theory?");
    }
    if (channel.textChannel() == null) {
      this.carbonCord.logger().debug("Not handling message {} to a null GuildChannel", e.message());
      return;
    }

    // Fire off an event
    final DiscordFormatEvent discordFormatEvent = new DiscordFormatEvent(e.user(), channel, /*todo*/"**<username>**: <message>", e.message());
    CarbonCordEvents.post(discordFormatEvent);
    if (discordFormatEvent.cancelled() || discordFormatEvent.message().equals("")) {
      this.carbonCord.logger().debug("Not handling message {} because the IngamePreFormatEvent was cancelled.", e.message());
      return;
    }

    final String finalMessage = PlaceholderUtil.setPlaceholders(discordFormatEvent.format(),
      "username", e.user().name(),
      "message", discordFormatEvent.message());

    channel.textChannel().sendMessage(finalMessage).queue(message -> {
      CarbonCordEvents.post(new DiscordPostMessageEvent(e.user(), message, channel, finalMessage, discordFormatEvent.format()));
    });
  }
}
