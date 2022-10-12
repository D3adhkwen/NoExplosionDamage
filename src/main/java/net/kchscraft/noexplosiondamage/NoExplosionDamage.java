package net.kchscraft.noexplosiondamage;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class NoExplosionDamage extends JavaPlugin implements Listener {

    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        config.addDefault("enabledWorlds", Arrays.asList("exampleWorld1", "exampleWorld2"));
        config.options().copyDefaults(true);
        saveConfig();
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        // Check whether damage source is end crystal or tnt minecart
        if (!(event.getDamager() instanceof EnderCrystal) || !(event.getDamager() instanceof ExplosiveMinecart)) {
            return;
        }

        // Check whether victim is player
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        // Only cancel damage if victim in specified worlds
        for (String world : config.getStringList("enabledWorlds")) {
            if (event.getEntity().getWorld().getName().equals(world)) {
                event.setCancelled(true);
            }
        }
    }

}
