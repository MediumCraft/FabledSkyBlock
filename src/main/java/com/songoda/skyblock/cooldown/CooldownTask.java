package com.songoda.skyblock.cooldown;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class CooldownTask extends BukkitRunnable {

    private final CooldownManager cooldownManager;

    public CooldownTask(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @Override
    public void run() {
        for (CooldownType cooldownType : CooldownType.getTypes()) {
            List<CooldownPlayer> cooldownPlayers = cooldownManager.getCooldownPlayers(cooldownType);

            if (cooldownPlayers == null) return;

            for (int i = 0; i < cooldownPlayers.size(); i++) {
                CooldownPlayer cooldownPlayer = cooldownPlayers.get(i);
                Cooldown cooldown = cooldownPlayer.getCooldown();

                cooldown.setTime(cooldown.getTime() - 1);

                if (cooldown.getTime() <= 0) {
                    cooldownManager.deletePlayer(cooldownType, Bukkit.getServer().getOfflinePlayer(cooldownPlayer.getUUID()));
                }
            }

        }
    }
}
