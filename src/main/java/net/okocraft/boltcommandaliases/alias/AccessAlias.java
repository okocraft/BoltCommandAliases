package net.okocraft.boltcommandaliases.alias;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.popcraft.bolt.command.Arguments;
import org.popcraft.bolt.source.SourceTypes;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class AccessAlias extends AbstractModifyAlias {

    public AccessAlias() {
        super("caccess");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!this.checkPermission(sender)) {
            return true;
        }

        if (args.length == 0 || (args[0].startsWith("-") && args[0].length() == 1)) {
            this.sendHelp(sender);
            return true;
        }

        boolean add = !args[0].startsWith("-");
        var playerName = add ? args[0] : args[0].substring(1);

        this.originalCommand.execute(sender, new Arguments(add ? "add" : "remove", "normal", SourceTypes.PLAYER, playerName));

        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String @NotNull [] args) throws IllegalArgumentException {
        if (!this.checkPermissionSilently(sender) || args.length != 1) {
            return Collections.emptyList();
        }

        boolean remove = args[0].startsWith("-");
        String filter;

        if (remove) {
            filter = args[0].length() == 1 ? "" : args[0].substring(1).toLowerCase(Locale.ENGLISH);
        } else {
            filter = args[0].toLowerCase(Locale.ENGLISH);
        }

        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(name -> name.toLowerCase(Locale.ENGLISH).startsWith(filter))
                .map(name -> remove ? "-" + name : name)
                .toList();
    }

    private void sendHelp(@NotNull CommandSender sender) {
        sender.sendMessage(Component.text("Usage: /caccess <player/-player>", NamedTextColor.GRAY));
    }
}
