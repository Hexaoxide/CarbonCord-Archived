package io.github.underscore11code.carboncord.bukkit;

import cloud.commandframework.CommandManager;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import io.github.underscore11code.carbonchat.common.channels.ChannelManager;
import io.github.underscore11code.carbonchat.common.commands.CommandRegistrar;
import io.github.underscore11code.carbonchat.common.config.ConfigLoader;
import io.github.underscore11code.carbonchat.common.listeners.ListenerRegistrar;
import io.github.underscore11code.carboncord.api.CarbonCord;
import io.github.underscore11code.carboncord.api.CarbonCordProvider;
import io.github.underscore11code.carboncord.api.channels.DiscordChannelRegistry;
import io.github.underscore11code.carboncord.api.config.CarbonCordSettings;
import me.clip.placeholderapi.PlaceholderAPI;
import net.draycia.carbon.api.CarbonChatProvider;
import net.draycia.carbon.api.users.CarbonUser;
import net.draycia.carbon.api.users.PlayerUser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import javax.security.auth.login.LoginException;
import java.nio.file.Path;

@SuppressWarnings("initialization.fields.uninitialized")
public class CarbonCordBukkit extends JavaPlugin implements CarbonCord {

  private Logger logger;
  private JDA jda;
  private CarbonCordSettings carbonCordSettings;
  private CommandManager<CarbonUser> commandManager;
  private ChannelManager channelManager;

  @Override
  public void onLoad() {
    CarbonCordProvider.register(this);
  }

  @Override
  public void onEnable() {
    this.logger = LoggerFactory.getLogger(this.getName());

    this.loadConfigs();

    final String botToken = this.carbonCordSettings().botToken();
    if (botToken.equals("BOTTOKEN")) { // Default
      this.logger.error("FATAL: BotToken is set to the default! CarbonCord **CANNOT** work without a BotToken!");
      this.logger.error("Not sure what the BotToken is? See the installation instructions at <link>"); // TODO link
      this.setEnabled(false);
      return;
    }

    try {
      this.jda = JDABuilder.createDefault(botToken)
        .addEventListeners()
        .build();
    } catch (final LoginException e) {
      this.logger.error("FATAL: Could not log into Discord with the given BotToken!");
      this.logger.error("Ensure the BotToken is correct!");
      this.setEnabled(false);
      return;
    }

    this.setupCommands();

    this.channelManager = new ChannelManager(this);

    ListenerRegistrar.registerHandlers(this);
    this.logger.info("Done! Enjoy CarbonCord!");
  }

  @Override
  public void onDisable() {
    if (this.jda != null) {
      this.jda.shutdown();
    }
  }

  // --- Public API ---

  @Override
  public void reload() {
    this.loadConfigs();
  }

  @Override
  @SuppressWarnings("argument.type.incompatible")
  // Checkstyle seems to default to @NonNull if not annotated,
  // despite PAPI supporting null OfflinePlayers
  public @NonNull String setPlatformPlaceholders(final @NonNull String text, final @Nullable PlayerUser user) {
    OfflinePlayer offlinePlayer = null;
    if (user != null) {
      offlinePlayer = this.getServer().getOfflinePlayer(user.uuid());
      // apparently internally PAPI downcasts Players to OfflinePlayers, so even if they are online,
      // no point fetching the associated Player
    }

    return PlaceholderAPI.setPlaceholders(offlinePlayer, text);
  }

  // --- Private Util etc ---

  private void loadConfigs() {
    this.getDataFolder().mkdir();

    try {
      final ConfigLoader<YamlConfigurationLoader> loader = new ConfigLoader<>(YamlConfigurationLoader.class);

      this.carbonCordSettings = CarbonCordSettings.loadFrom(loader.loadConfig("config.yml"));
    } catch(final ConfigurateException ex) {
      ex.printStackTrace();
    }
  }

  @SuppressWarnings("return.type.incompatible")
  private void setupCommands() {
    try {
      this.commandManager = new PaperCommandManager<>(this,
        CommandExecutionCoordinator
          .simpleCoordinator(), sender -> {
        if (sender instanceof Player) {
          return CarbonChatProvider.carbonChat().userService().wrap(((Player) sender).getUniqueId());
        } else {
          return CarbonChatProvider.carbonChat().userService().consoleUser();
        }
      }, user -> {
        if (user instanceof PlayerUser) {
          return Bukkit.getPlayer(((PlayerUser) user).uuid());
        } else {
          return Bukkit.getConsoleSender();
        }
      });

      CommandRegistrar.registerCommands(this.commandManager);
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  // --- Getters | Setters ---

  @Override
  public @NonNull CarbonCordSettings carbonCordSettings() {
    return this.carbonCordSettings;
  }

  @Override
  public @NonNull Path dataFolder() {
    return this.getDataFolder().toPath();
  }

  @Override
  public @NonNull JDA jda() {
    return this.jda;
  }

  @Override
  public @NonNull CommandManager<CarbonUser> commandManager() {
    return this.commandManager;
  }

  @Override
  public @NonNull Logger logger() {
    return this.logger;
  }

  @Override
  public @NonNull DiscordChannelRegistry discordChannelRegistry() {
    return this.channelManager.registry();
  }
}
