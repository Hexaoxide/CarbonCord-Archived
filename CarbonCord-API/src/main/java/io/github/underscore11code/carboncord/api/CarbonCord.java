package io.github.underscore11code.carboncord.api;

import cloud.commandframework.CommandManager;
import io.github.underscore11code.carboncord.api.channels.DiscordChannelRegistry;
import io.github.underscore11code.carboncord.api.config.CarbonCordSettings;
import net.draycia.carbon.api.users.CarbonUser;
import net.draycia.carbon.api.users.PlayerUser;
import net.dv8tion.jda.api.JDA;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
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

  default @NonNull String setPlatformPlaceholders(final @NonNull String text, final @Nullable PlayerUser user) {
    return text;
  }
}
