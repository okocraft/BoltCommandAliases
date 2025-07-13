package net.okocraft.boltcommandaliases.locale;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

import static org.popcraft.bolt.lang.Translator.translate;

public final class NativeBoltComponents {

    private static void sendMessage(final CommandSender sender, final Component component) {
        if (!component.equals(Component.empty())) {
            sender.sendMessage(component);
        }
    }

    public static void sendMessage(final CommandSender sender, String key, TagResolver... placeholders) {
        sendMessage(sender, resolveTranslation(key, sender, placeholders));
    }

    public static Component resolveTranslation(final String key, final CommandSender sender, TagResolver... placeholders) {
        return MiniMessage.miniMessage().deserialize(translateRaw(key, sender), placeholders);
    }

    public static String translateRaw(final String key, final CommandSender sender) {
        return translate(key, getLocaleOf(sender));
    }

    public static Locale getLocaleOf(CommandSender sender) {
        if (sender instanceof Player player) {
            return player.locale();
        } else {
            return new Locale("");
        }
    }
}
