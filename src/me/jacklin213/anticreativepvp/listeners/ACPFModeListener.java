package me.jacklin213.anticreativepvp.listeners;

import me.jacklin213.anticreativepvp.ACP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
/**
 * AntiCreativePvp (ACP) Fly Mode Listener
 * @author jacklin213
 *
 */
public class ACPFModeListener implements Listener {
	
	private ACP plugin;
	
	public ACPFModeListener(ACP instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		String pID = player.getUniqueId().toString();
		if (plugin.flyModeEnabled.contains(pID)) {
			player.setAllowFlight(true);
			player.setFlying(true);
			player.setFlySpeed(0.1F);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				  public void run() {
					  player.sendMessage(plugin.MSG.chatPluginPrefix + ChatColor.GREEN + " Flymode Enabled.");
				  }
			}, 5);
			
		} 
	}
	
}