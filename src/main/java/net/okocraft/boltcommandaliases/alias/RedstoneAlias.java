package net.okocraft.boltcommandaliases.alias;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.popcraft.bolt.command.Arguments;
import org.popcraft.bolt.source.SourceTypes;
import org.popcraft.bolt.util.Permission;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class RedstoneAlias extends AbstractModifyAlias {

    public RedstoneAlias() {
        super("credstone");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!this.checkPermission(sender)) {
            return true;
        }

        if (args.length == 0) {
            this.sendHelp(sender);
            return true;
        }

        boolean allow;

        var arg = args[0].toLowerCase(Locale.ENGLISH);
        if (ADD.contains(arg)) {
            allow = true;
        } else if (REMOVE.contains(arg)) {
            allow = false;
        } else {
            this.sendHelp(sender);
            return true;
        }

        boolean denyByDefault = this.bolt().getConfig().getBoolean("redstone.deny-by-default", false);
        boolean add = denyByDefault == allow;

        this.originalCommand.execute(sender, new Arguments(add ? "add" : "remove", "normal", SourceTypes.REDSTONE, Permission.REDSTONE));

        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String @NotNull [] args) throws IllegalArgumentException {
        if (args.length == 1 && this.checkPermissionSilently(sender)) {
            var filter = args[0].toLowerCase(Locale.ENGLISH);
            return Stream.of("allow", "deny").filter(value -> value.startsWith(filter)).toList();
        } else {
            return Collections.emptyList();
        }
    }

    private void sendHelp(@NotNull CommandSender sender) {
        sender.sendMessage(Component.text("Usage: /credstone <allow/deny>", NamedTextColor.GRAY));
    }
}
