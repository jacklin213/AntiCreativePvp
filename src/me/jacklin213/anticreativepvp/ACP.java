package me.jacklin213.anticreativepvp;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

import me.jacklin213.anticreativepvp.listeners.ACPFModeListener;
import me.jacklin213.anticreativepvp.listeners.ACPGModeListener;
import me.jacklin213.anticreativepvp.listeners.ACPListener;
import me.jacklin213.anticreativepvp.utils.ACPDataHandler;
import me.jacklin213.anticreativepvp.utils.MessageHandler;
import me.jacklin213.anticreativepvp.utils.UpdateChecker;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * AntiCreativePvp main class, plugin handler
 * @author jacklin213
 *
 */
public class ACP extends JavaPlugin  {
	
	public static ACP plugin;
	
	public Logger log = Logger.getLogger("Minecraft");
	public ACPListener acpl = new ACPListener(this);
	public ACPGModeListener acpgml = new ACPGModeListener(this);
	public ACPFModeListener acpfml = new ACPFModeListener(this);
	public MessageHandler MSG = new MessageHandler(this);
	public UpdateChecker updateChecker;
	protected ACPDataHandler acpdh;
	
	public ArrayList<String> godModeEnabled = new ArrayList<String>();
	public ArrayList<String> flyModeEnabled = new ArrayList<String>();

	public void onEnable() {
		
		createConfig();
		String pluginFolder = this.getDataFolder().getAbsolutePath();
		(new File(pluginFolder)).mkdirs();
		this.acpdh = new ACPDataHandler(new File(pluginFolder + File.separator + "flymode.data"), new File(pluginFolder + File.separator + "godmode.data"), this);
		
		acpdh.loadData();
		MSG.getMessages();
		
		//Update Check
		Boolean updateCheck = Boolean.valueOf(getConfig().getBoolean("UpdateCheck"));
		 
		this.updateChecker = new UpdateChecker(this, "http://dev.bukkit.org/server-mods/anticreativepvp/files.rss");

		if ((updateCheck) && (this.updateChecker.updateNeeded())) {
			log.info(String.format("[%s] A new update is avalible, Version: %s", getDescription().getName(), this.updateChecker.getVersion()));
			log.info(String.format("[%s] Get it now from: %s", getDescription().getName(), this.updateChecker.getLink()));
		}
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.acpl, this);
		pm.registerEvents(this.acpgml, this);
		pm.registerEvents(this.acpfml, this);
		
		log.info(String.format("[%s] Version %s by jacklin213 has been Enabled!",
				getDescription().getName(), getDescription().getVersion()));
	}

	public void onDisable() {
		acpdh.saveData();
		
		log.info(String.format("[%s] Disabled Version %s", getDescription()
				.getName(), getDescription().getVersion()));
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[]){
		if (commandLabel.equalsIgnoreCase("anticreativepvp") || commandLabel.equalsIgnoreCase("acp")){
			if (args.length == 1){
				if (args[0].equalsIgnoreCase("god")){
					godMode(sender);
					return true;
				} 
				if (args[0].equalsIgnoreCase("fly")){
					flyMode(sender);
					return true;
				} 
				if (args[0].equalsIgnoreCase("reload")){
					reloadConfig();
					MSG.getMessages();
					sender.sendMessage(MSG.chatPluginPrefix + ChatColor.GREEN + "Configuration Reloaded!");
					return true;
				}
				if (args[0].equalsIgnoreCase("help")){
					MSG.pluginHelp(sender);
					return true;
				}
				else {
					sender.sendMessage(MSG.notValidArgs);
					return true;
				}
			}
			MSG.pluginInfo(sender);
			return true;
		}
		
		return false;
	}
	
	public void createConfig(){
		File configFile = new File(getDataFolder() + File.separator
				+ "config.yml");
		if (!configFile.exists()) {
			// Tells console its creating a config.yml
			this.getLogger().info("Cannot find config.yml, Generating now....");
			this.getLogger().info("Config generated !");
			this.getConfig().options().copyDefaults(true);
			this.saveDefaultConfig();
		}
	}
	
	
	public void godMode(CommandSender sender){
		if (sender instanceof Player){
			Player player = (Player) sender;
			String playerName = player.getName();
			if (player.hasPermission("acp.god")){
				if (this.godModeEnabled.contains(playerName)){
					this.godModeEnabled.remove(playerName);
					player.sendMessage(MSG.chatPluginPrefix + ChatColor.RED + " Godmode is now Disabled.");
					return;
				} else {
					this.godModeEnabled.add(playerName);
					player.sendMessage(MSG.chatPluginPrefix + ChatColor.GREEN + " Godmode is now Enabled.");
					return;
				}
			} else {
				player.sendMessage(MSG.noPermMessage);
				return;
			}
		} else {
			sender.sendMessage(MSG.playerOnlyMessage);
			return;
		}
	}
	
	public void flyMode(CommandSender sender){
		if (sender instanceof Player){
			Player player = (Player) sender;
			String playerName = player.getName();
			if (player.hasPermission("acp.fly")) {
				if (this.flyModeEnabled.contains(playerName)){
					this.flyModeEnabled.remove(playerName);
					player.setFlying(false);
					player.setAllowFlight(false);
					player.sendMessage(MSG.chatPluginPrefix + ChatColor.RED + " Flymode is now Disabled.");
					return;
				} else {
					this.flyModeEnabled.add(playerName);
					player.setAllowFlight(true);
					player.setFlySpeed(0.1F);
					player.sendMessage(MSG.chatPluginPrefix + ChatColor.GREEN + " Flymode is now Enabled.");
					return;
				}
			} else {
				player.sendMessage(MSG.noPermMessage);
				return;
			}
		} else {
			sender.sendMessage(MSG.playerOnlyMessage);
			return;
		}
	}

}
