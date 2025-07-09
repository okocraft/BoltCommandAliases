package net.okocraft.boltcommandaliases.alias;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.popcraft.bolt.BoltPlugin;
import org.popcraft.bolt.command.BoltCommand;
import org.popcraft.bolt.lang.Translation;
import org.popcraft.bolt.util.BoltComponents;

import java.util.Objects;

public abstract class AbstractBoltCommandAlias extends Command {

    private static final String COMMAND_PERMISSION_KEY = "bolt.command."; // See BoltPlugin#COMMAND_PERMISSION_KEY

    private final String originalCommandName;
    protected final BoltCommand originalCommand;

    protected AbstractBoltCommandAlias(@NotNull String name, @NotNull String originalCommandName) {
        super(name);
        this.originalCommandName = originalCommandName;
        this.originalCommand = this.findCommand(originalCommandName);
    }

    protected final @NotNull BoltPlugin bolt() {
        return JavaPlugin.getPlugin(BoltPlugin.class);
    }

    protected final boolean checkPermission(@NotNull CommandSender sender) {
        if (sender.hasPermission(COMMAND_PERMISSION_KEY + this.originalCommandName)) {
            return true;
        } else {
            BoltComponents.sendMessage(sender, Translation.COMMAND_NO_PERMISSION);
            return false;
        }
    }

    protected final boolean checkPermissionSilently(@NotNull CommandSender sender) {
        return sender.hasPermission(COMMAND_PERMISSION_KEY + this.originalCommandName);
    }

    private @NotNull BoltCommand findCommand(@NotNull String name) {
        return Objects.requireNonNull(this.bolt().commands().get(name), name + " is not found");
    }
}
