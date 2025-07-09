package net.okocraft.boltcommandaliases.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UnlockListener implements Listener {

    private static final Component WARN_JP = MiniMessage.miniMessage().deserialize("""
            <red>警告!</red> <gray>ブロック保護の解除は他プレイヤーによる窃盗などのリスクがあります!</gray>
            <gray>以下のコマンドの使用を検討してください。続ける場合は再度 /unlock を実行します。</gray>
            <aqua>/caccess <プレイヤー名></aqua> <gray>プレイヤーの保護へのアクセスを許可する</gray>
            <aqua>/citemmove allow</aqua> <gray>ホッパーによるアイテム移動を許可する</gray>
            <gray>その他のコマンド・詳細な仕様: <yellow>https://wiki.okocraft.net/ja/bolt</yellow>
            """);

    private static final Component WARN_EN = MiniMessage.miniMessage().deserialize("""
            <red>WARNING!</red> <gray>Unlocking block protection is risky, such as theft by other players!</gray>
            <gray>Please consider using the following command. To continue, run /unlock again.</gray>
            <aqua>/caccess <player name></aqua> <gray>Allow players access to the protection</gray>
            <aqua>/citemmove allow</aqua> <gray>Allow hoppers to move items</gray>
            <gray>Other commands and details: <yellow>https://wiki.okocraft.net/ja/bolt</yellow>
            """);

    private static final Set<String> UNLOCK_COMMANDS = Set.of("/unlock", "/bolt unlock");
    private static final long CONFIRMATION_INTERVAL = Duration.ofHours(1).toMillis();

    private final Map<UUID, Long> lastConfirmationMap = new ConcurrentHashMap<>();

    @EventHandler
    public void onCommand(@NotNull PlayerCommandPreprocessEvent event) {
        var player = event.getPlayer();

        if (!UNLOCK_COMMANDS.contains(event.getMessage()) || player.hasPermission("bolt.mod")) {
            return;
        }

        Long lastConfirmation = this.lastConfirmationMap.get(player.getUniqueId());

        if (lastConfirmation != null && (System.currentTimeMillis() - lastConfirmation) < CONFIRMATION_INTERVAL) {
            return;
        }

        var language = player.locale().getLanguage();

        if (language.equals(Locale.JAPANESE.getLanguage())) {
            player.sendMessage(WARN_JP);
        } else {
            player.sendMessage(WARN_EN);
        }

        event.setCancelled(true);
        this.lastConfirmationMap.put(player.getUniqueId(), System.currentTimeMillis());
    }
}
