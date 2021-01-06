package io.github.underscore11code.carbonchat.common.listeners;

import io.github.underscore11code.carboncord.api.channels.DiscordChannel;
import io.github.underscore11code.carboncord.api.events.CarbonFormatEvent;
import io.github.underscore11code.carboncord.api.misc.ForwardingBus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.kyori.event.PostOrders;
import org.checkerframework.checker.nullness.qual.NonNull;

public class UserFormattingListener {
  @SuppressWarnings("methodref.receiver.bound.invalid")
  public UserFormattingListener() {
    ForwardingBus.carbonCord().register(CarbonFormatEvent.class, PostOrders.NORMAL, false, this::checkFormatting);
  }

  @SuppressWarnings("AvoidEscapedUnicodeCharacters") // What, you want me to just have a zero-width space floating around?
  private void checkFormatting(final @NonNull CarbonFormatEvent e) {
    if (canUserFormat(e.member(), e.channel())) return;

    // Sticks a zero-width space immediately after any section symbols or ampersands to escape it
    e.message(e.message().replace("&", "&\u200B").replace("\u00A7", "\u00A7\u200B"));
  }

  private static boolean canUserFormat(final @NonNull Member member, final @NonNull DiscordChannel discordChannel) {
    return member.isOwner() || member.hasPermission(Permission.ADMINISTRATOR);
  }
}
