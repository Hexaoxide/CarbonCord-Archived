package io.github.underscore11code.carboncord.api;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import io.github.underscore11code.carboncord.api.channels.DiscordChannelRegistry;
import io.github.underscore11code.carboncord.api.channels.notifications.NotificationChannelRegistry;
import io.github.underscore11code.carboncord.api.config.CarbonCordSettings;
import io.github.underscore11code.carboncord.api.misc.PlatformInfo;
import io.github.underscore11code.carboncord.api.util.PrettyUtil;
import net.draycia.carbon.api.users.CarbonUser;
import net.dv8tion.jda.api.JDA;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.awt.Color;
import java.nio.file.Path;

public interface CarbonCord {
  void reload();

  @NonNull Logger logger();

  @NonNull Path dataFolder();

  @NonNull JDA jda();

  @NonNull CarbonCordSettings carbonCordSettings();

  @NonNull CommandManager<CarbonUser> commandManager();

  @NonNull DiscordChannelRegistry discordChannelRegistry();

  @NonNull NotificationChannelRegistry notificationChannelRegistry();

  default Command.Builder<CarbonUser> baseCommand() {
    return this.commandManager().commandBuilder("carboncord", "ccord", "discord");
  }

  @NonNull String version();

  @NonNull PlatformInfo platformInfo();

  static TextComponent prefix() {
    return Component.text("[CCord] ",
      TextColor.color(PrettyUtil.colorToRgbLike(CarbonCord.blurple())),
      TextDecoration.BOLD);
  }

  static Color blurple() {
    return new Color(114, 137, 218);
  }
}
