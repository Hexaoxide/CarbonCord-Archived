package io.github.underscore11code.carboncord.bukkit.listeners;

import io.github.underscore11code.carbonchat.common.events.AdvancementEvent;
import io.github.underscore11code.carboncord.api.events.misc.CarbonCordEvents;
import io.github.underscore11code.carboncord.bukkit.CarbonCordBukkit;
import net.draycia.carbon.api.CarbonChatProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

public class AdvancementListener implements BukkitListener {
  @SuppressWarnings("argument.type.incompatible")
  public AdvancementListener(final @NonNull CarbonCordBukkit carbonCord) {
    carbonCord.getServer().getPluginManager().registerEvents(this, carbonCord);
  }

  @EventHandler
  public void onAdvancement(final @NonNull PlayerAdvancementDoneEvent event) {
    final String advancementName = event.getAdvancement().getKey().getKey();
    CarbonChatProvider.carbonChat().userService().wrapLater(event.getPlayer().getUniqueId()).thenAcceptAsync(playerUser ->
      CarbonCordEvents.post(new AdvancementEvent(playerUser, advancementName))
    );
  }

  @Override
  public void unregister() {
    PlayerAdvancementDoneEvent.getHandlerList().unregister(this);
  }
}
