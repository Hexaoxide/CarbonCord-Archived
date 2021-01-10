package io.github.underscore11code.carboncord.api;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import io.github.underscore11code.carboncord.api.channels.DiscordChannelRegistry;
import io.github.underscore11code.carboncord.api.channels.console.ConsoleChannel;
import io.github.underscore11code.carboncord.api.channels.notifications.NotificationChannelRegistry;
import io.github.underscore11code.carboncord.api.config.CarbonCordSettings;
import io.github.underscore11code.carboncord.api.misc.PlatformInfo;
import net.draycia.carbon.api.users.CarbonUser;
import net.dv8tion.jda.api.JDA;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

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

  @NonNull ConsoleChannel consoleChannel();

  void runConsoleCommand(String command);

  default Command.Builder<CarbonUser> baseCommand() {
    return this.commandManager().commandBuilder("carboncord", "ccord", "discord");
  }

  @NonNull String version();

  @NonNull PlatformInfo platformInfo();
}
