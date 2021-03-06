package me.jacklin213.anticreativepvp.listeners;

import me.jacklin213.anticreativepvp.ACP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
/**
 * AntiCreativePvp (ACP) God Mode Listener
 * @author jacklin213
 *
 */
public class ACPGModeListener implements Listener {
	
	private ACP plugin;
	
	public ACPGModeListener(ACP instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void godMode(EntityDamageEvent event) {
		if ((event.getEntity() instanceof Player)) {
			 Player player = (Player)event.getEntity();
			 String pID = player.getUniqueId().toString();
			 double maxHp = player.getMaxHealth();
			 player.setHealth(maxHp);
			 if (plugin.godModeEnabled.contains(pID)) {
				 event.setCancelled(true);
			 } 
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		String pID = player.getUniqueId().toString();
		if (plugin.godModeEnabled.contains(pID)) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				  public void run() {
					  player.sendMessage(plugin.MSG.chatPluginPrefix + ChatColor.GREEN + " Godmode Enabled.");
				  }
			}, 5);
			
		} 
	}
	
}
