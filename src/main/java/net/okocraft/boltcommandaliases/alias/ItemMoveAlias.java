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

public class ItemMoveAlias extends AbstractModifyAlias {

    public ItemMoveAlias() {
        super("citemmove");
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

        boolean add;

        var arg = args[0].toLowerCase(Locale.ENGLISH);
        if (ADD.contains(arg)) {
            add = true;
        } else if (REMOVE.contains(arg)) {
            add = false;
        } else {
            this.sendHelp(sender);
            return true;
        }

        this.originalCommand.execute(sender, new Arguments(add ? "add" : "remove", "normal", SourceTypes.BLOCK, Permission.WITHDRAW, Permission.DEPOSIT));

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
        sender.sendMessage(Component.text("Usage: /citemmove <allow/deny>", NamedTextColor.GRAY));
    }
}
