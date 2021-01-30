package io.github.underscore11code.carboncord.bukkit;

import cloud.commandframework.CommandManager;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import io.github.underscore11code.carboncord.api.CarbonCordProvider;
import io.github.underscore11code.carboncord.api.misc.PlatformInfo;
import io.github.underscore11code.carboncord.bukkit.listeners.AdvancementListener;
import io.github.underscore11code.carboncord.bukkit.listeners.BukkitListener;
import io.github.underscore11code.carboncord.bukkit.listeners.PlaceholderAPIListener;
import io.github.underscore11code.carboncord.common.CarbonCordBootstrap;
import io.github.underscore11code.carboncord.common.CarbonCordImpl;
import net.draycia.carbon.api.CarbonChatProvider;
import net.draycia.carbon.api.users.CarbonUser;
import net.draycia.carbon.api.users.PlayerUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("initialization.fields.uninitialized")
public class CarbonCordBukkit extends JavaPlugin implements CarbonCordBootstrap {
  @SuppressWarnings({"assignment.type.incompatible", "argument.type.incompatible"})
  private final CarbonCordImpl carbonCord = new CarbonCordImpl(this);
  private final Set<BukkitListener> bukkitListeners = new HashSet<>();

  @Override
  public void onLoad() {
    CarbonCordProvider.register(this.carbonCord);
  }

  @Override
  public void onEnable() {
    this.carbonCord.enable();
    new PlaceholderAPIListener();
    this.bukkitListeners.add(new AdvancementListener(this));
  }

  @Override
  public void onDisable() {
    this.carbonCord.disable();
    this.bukkitListeners.forEach(BukkitListener::unregister);
  }

  @Override
  public void disable() {
    this.carbonCord.disable();
    this.setEnabled(false);
  }

  @Override
  @SuppressWarnings("return.type.incompatible")
  public CommandManager<CarbonUser> commandManager() throws Exception {
    return new PaperCommandManager<>(this,
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
  }

  @Override
  public File dataFolder() {
    return this.getDataFolder();
  }

  @Override
  public @NonNull PlatformInfo platformInfo() {
    final CarbonCordBukkit cc = this;
    return new PlatformInfo() {
      @Override
      public @NonNull String serverVersion() {
        return cc.getServer().getVersion();
      }

      @Override
      public @NonNull String serverBrand() {
        return cc.getServer().getName();
      }

      @Override
      public @NonNull String minecraftVersion() {
        return cc.getServer().getMinecraftVersion();
      }

      @Override
      public @NonNull Platform platform() {
        return Platform.BUKKIT;
      }

      @Override
      public @NonNull Map<String, String> otherInfo() {
        final Map<String, String> info = new HashMap<>();
        info.put("PlaceholderAPI ", this.pluginVersion("PlaceholderAPI"));
        info.put("LuckPerms ", this.pluginVersion("LuckPerms"));
        return info;
      }

      private @NonNull String pluginVersion(final @NonNull String name) {
        final Plugin plugin = cc.getServer().getPluginManager().getPlugin(name);
        if (plugin == null) {
          return "null";
        }
        return plugin.getDescription().getVersion();
      }
    };
  }

  @Override
  public String version() {
    return this.getDescription().getVersion();
  }
}
