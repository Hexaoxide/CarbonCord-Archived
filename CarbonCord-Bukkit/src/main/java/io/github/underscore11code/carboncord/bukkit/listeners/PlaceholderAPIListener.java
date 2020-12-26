package io.github.underscore11code.carboncord.bukkit.listeners;

import io.github.underscore11code.carboncord.api.events.CarbonFormatEvent;
import io.github.underscore11code.carboncord.api.events.DiscordFormatEvent;
import io.github.underscore11code.carboncord.api.events.misc.CarbonCordEvents;
import me.clip.placeholderapi.PlaceholderAPI;
import net.draycia.carbon.api.users.PlayerUser;
import net.kyori.event.PostOrders;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PlaceholderAPIListener {

  @SuppressWarnings("methodref.receiver.bound.invalid")
  public PlaceholderAPIListener() {
    CarbonCordEvents.register(CarbonFormatEvent.class, PostOrders.NORMAL, false, this::applyPlaceholders);
    CarbonCordEvents.register(DiscordFormatEvent.class, PostOrders.NORMAL, false, this::applyPlaceholders);
  }

  private void applyPlaceholders(final @NonNull CarbonFormatEvent e) {
    e.format(this.applyPlaceholders(e.format(), null));
  }

  private void applyPlaceholders(final @NonNull DiscordFormatEvent e) {
    e.format(this.applyPlaceholders(e.format(), e.user()));
  }

  // Player argument of PlaceholderAPI#setPlaceholders is annoyingly not annotated as @Nullable, despite being nullable
  @SuppressWarnings("argument.type.incompatible")
  private @NonNull String applyPlaceholders(final @NonNull String original, final @Nullable PlayerUser playerUser) {
    OfflinePlayer player = null;
    if (playerUser != null) {
      player = Bukkit.getPlayer(playerUser.uuid());
      if (player == null) {
        player = Bukkit.getOfflinePlayer(playerUser.uuid());
      }
    }
    return PlaceholderAPI.setPlaceholders(player, original);
  }
}
