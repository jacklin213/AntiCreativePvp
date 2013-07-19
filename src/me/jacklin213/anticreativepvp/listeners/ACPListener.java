package me.jacklin213.anticreativepvp.listeners;

import me.jacklin213.anticreativepvp.ACP;

import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * AntiCreativePvp (ACP) Player Damage Listener
 * 
 * @author jacklin213
 * 
 */
public class ACPListener implements Listener {

	public static ACP plugin;

	public ACPListener(ACP instance) {
		plugin = instance;
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if ((entity instanceof Player)) {
			if ((event instanceof EntityDamageByEntityEvent)) {
				EntityDamageByEntityEvent damageCause = (EntityDamageByEntityEvent) event;
				if ((damageCause.getDamager() instanceof Player)) {
					Player damager = (Player) damageCause.getDamager();
					if (damager.getGameMode() == GameMode.CREATIVE) {
						if (damager.hasPermission("acp.bypass")) {
							event.setCancelled(false);
						} else {
							event.setCancelled(true);
							damager.sendMessage(plugin.MSG.chatPluginPrefix + plugin.MSG.cantHurtPlayerMelee);
						}

					}

				}

			}

		}
	}

	@EventHandler
	public void onArrowHit(EntityDamageByEntityEvent event) {
		if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
			Projectile projectile = (Projectile) event.getDamager();
			LivingEntity shooter = projectile.getShooter();
			if (((projectile instanceof Arrow))
					&& ((shooter instanceof Player))) {
				Player playerShooter = (Player) shooter;
				if (playerShooter.getGameMode() == GameMode.CREATIVE)
					if (playerShooter.hasPermission("acp.bypass.bow")) {
						event.setCancelled(false);
					} else {
						event.setCancelled(true);
						playerShooter.sendMessage(plugin.MSG.chatPluginPrefix + plugin.MSG.cantHurtPlayerRanged);
					}
			}
		}
	}

}
