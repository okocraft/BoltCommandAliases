package net.okocraft.boltcommandaliases.alias;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.popcraft.bolt.command.Arguments;

import java.util.Collections;
import java.util.List;

public class SimpleAlias extends AbstractBoltCommandAlias {

    public SimpleAlias(@NotNull String name) {
        super("c" + name, name);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String @NotNull [] args) {
        if (this.checkPermission(sender)) {
            this.originalCommand.execute(sender, new Arguments(args));
        }
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String @NotNull [] args) throws IllegalArgumentException {
        if (this.checkPermission(sender)) {
            return this.originalCommand.suggestions(sender, new Arguments(args));
        }
        return Collections.emptyList();
    }
}
