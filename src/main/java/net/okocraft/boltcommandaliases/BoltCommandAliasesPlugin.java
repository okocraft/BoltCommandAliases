package net.okocraft.boltcommandaliases;

import net.okocraft.boltcommandaliases.alias.AccessAlias;
import net.okocraft.boltcommandaliases.alias.AutoCloseAlias;
import net.okocraft.boltcommandaliases.alias.ItemMoveAlias;
import net.okocraft.boltcommandaliases.alias.ModeAlias;
import net.okocraft.boltcommandaliases.alias.RedstoneAlias;
import net.okocraft.boltcommandaliases.alias.SimpleAlias;
import net.okocraft.boltcommandaliases.listener.UnlockListener;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.popcraft.bolt.util.Mode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BoltCommandAliasesPlugin extends JavaPlugin {

    private static final String FALLBACK_PREFIX = "bolt";

    private final List<String> registeredCommandLabels = new ArrayList<>();

    @Override
    public void onEnable() {
        Stream.of(Mode.values()).map(ModeAlias::new).forEach(this::registerCommand);
        Stream.of("transfer", "info", "modify").map(SimpleAlias::new).forEach(this::registerCommand);
        Stream.of(new AccessAlias(), new AutoCloseAlias(), new ItemMoveAlias(), new RedstoneAlias()).forEach(this::registerCommand);
        this.getServer().getPluginManager().registerEvents(new UnlockListener(), this);
    }

    @Override
    public void onDisable() {
        this.registeredCommandLabels.forEach(label -> this.getServer().getCommandMap().getKnownCommands().remove(FALLBACK_PREFIX + ":" + label));
    }

    private void registerCommand(@NotNull Command command) {
        this.registeredCommandLabels.add(command.getName());
        this.getServer().getCommandMap().register(FALLBACK_PREFIX, command);
    }
}
