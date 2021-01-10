package io.github.underscore11code.carboncord.common.commands;

import cloud.commandframework.context.CommandContext;
import io.github.underscore11code.carboncord.api.CarbonCord;
import net.draycia.carbon.api.CarbonChatProvider;
import net.draycia.carbon.api.users.CarbonUser;
import net.dv8tion.jda.api.JDAInfo;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DebugCommand {
  private final CarbonCord carbonCord;

  @SuppressWarnings("methodref.receiver.bound.invalid")
  public DebugCommand(final CarbonCord carbonCord) {
    this.carbonCord = carbonCord;
    // /carboncord info
    this.carbonCord.commandManager().command(this.carbonCord.baseCommand()
      .literal("info", "debug", "i")
      .permission("carboncord.info")
      .handler(this::info)
    );
  }

  private void info(final CommandContext<CarbonUser> c) {
    final List<Component> components = new ArrayList<>();
    components.add(kv("CarbonCord ", this.carbonCord.version()));
    components.add(kv("CarbonChat ", CarbonChatProvider.carbonChat().version()));
    components.add(kv("JDA ", JDAInfo.VERSION));
    components.add(Component.text("Server").color(NamedTextColor.DARK_AQUA).decorate(TextDecoration.BOLD));
    components.add(kv("- Name ", this.carbonCord.platformInfo().serverBrand()));
    components.add(kv("- Version ", this.carbonCord.platformInfo().serverVersion()));
    components.add(kv("- Minecraft ", this.carbonCord.platformInfo().minecraftVersion()));
    components.add(kv("- Platform ", this.carbonCord.platformInfo().platform().name()));
    components.add(Component.text("Other").color(NamedTextColor.DARK_AQUA).decorate(TextDecoration.BOLD));
    components.addAll(info(this.carbonCord.platformInfo().otherInfo()));

    final Component component = Component.join(Component.newline(), components);
    c.getSender().sendMessage(Identity.nil(), component);
  }

  private static Component kv(final String k, final String v) {
    return Component.text(k).color(NamedTextColor.DARK_AQUA)
      .append(Component.text(v).color(NamedTextColor.AQUA));
  }

  private static List<Component> info(final Map<String, String> info) {
    final List<Component> components = new ArrayList<>();
    for (final Map.Entry<String, String> entry : info.entrySet()) {
      components.add(kv("- " + entry.getKey(), entry.getValue()));
    }
    return components;
  }
}
