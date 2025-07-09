package net.okocraft.boltcommandaliases.alias;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.popcraft.bolt.command.Arguments;
import org.popcraft.bolt.lang.Translation;
import org.popcraft.bolt.lib.net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.popcraft.bolt.util.BoltComponents;
import org.popcraft.bolt.util.Mode;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

public class ModeAlias extends AbstractBoltCommandAlias {

    private static final Set<String> ON = Set.of("on", "true");
    private static final Set<String> OFF = Set.of("off", "false");

    private final Mode mode;

    public ModeAlias(@NotNull Mode mode) {
        super("c" + mode.name().toLowerCase(Locale.ENGLISH), "mode");
        this.mode = mode;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            BoltComponents.sendMessage(sender, Translation.COMMAND_PLAYER_ONLY);
            return true;
        }

        if (!this.checkPermission(sender)) {
            return true;
        }

        Boolean on;

        if (0 < args.length) {
            var arg = args[0].toLowerCase(Locale.ENGLISH);
            if (ON.contains(arg)) {
                on = true;
            } else if (OFF.contains(arg)) {
                on = false;
            } else {
                on = null;
            }
        } else {
            on = null;
        }

        if (on == null) { // toggle
            this.originalCommand.execute(sender, new Arguments(this.mode.name()));
            return true;
        }

        var boltPlayer = this.bolt().player(player);
        boolean hasMode = boltPlayer.hasMode(this.mode);

        if (on ^ hasMode) {
            this.originalCommand.execute(sender, new Arguments(this.mode.name()));
        } else {
            BoltComponents.sendMessage(
                    player,
                    hasMode ? Translation.MODE_ENABLED : Translation.MODE_DISABLED,
                    Placeholder.component(Translation.Placeholder.MODE, BoltComponents.resolveTranslation("mode_%s".formatted(this.mode.name().toLowerCase()), player))
            );
        }

        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String @NotNull [] args) throws IllegalArgumentException {
        if (args.length == 1 && this.checkPermissionSilently(sender)) {
            var filter = args[0].toLowerCase(Locale.ENGLISH);
            return Stream.of("on", "off").filter(value -> value.startsWith(filter)).toList();
        } else {
            return Collections.emptyList();
        }
    }
}
