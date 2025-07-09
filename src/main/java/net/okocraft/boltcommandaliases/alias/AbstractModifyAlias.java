package net.okocraft.boltcommandaliases.alias;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public abstract class AbstractModifyAlias extends AbstractBoltCommandAlias {

    protected static final Set<String> ADD = Set.of("on", "true", "allow");
    protected static final Set<String> REMOVE = Set.of("off", "false", "deny");

    protected AbstractModifyAlias(@NotNull String name) {
        super(name, "modify");
    }
}
