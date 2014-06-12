package me.jacklin213.anticreativepvp;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

import me.jacklin213.anticreativepvp.listeners.ACPFModeListener;
import me.jacklin213.anticreativepvp.listeners.ACPGModeListener;
import me.jacklin213.anticreativepvp.listeners.ACPListener;
import me.jacklin213.anticreativepvp.utils.ACPDataHandler;
import me.jacklin213.anticreativepvp.utils.MessageHandler;
import me.jacklin213.anticreativepvp.utils.Updater;
import me.jacklin213.anticreativepvp.utils.Updater.UpdateResult;
import me.jacklin213.anticreativepvp.utils.Updater.UpdateType;

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
	
	public Logger log;
	public ACPListener acpl = new ACPListener(this);
	public ACPGModeListener acpgml = new ACPGModeListener(this);
	public ACPFModeListener acpfml = new ACPFModeListener(this);
	public MessageHandler MSG = new MessageHandler(this);
	public Updater updater;
	protected ACPDataHandler acpdh;
	
	public ArrayList<String> godModeEnabled = new ArrayList<String>();
	public ArrayList<String> flyModeEnabled = new ArrayList<String>();

	public void onEnable() {
		this.setLogger();
		createConfig();
		String pluginFolder = this.getDataFolder().getAbsolutePath();
		(new File(pluginFolder)).mkdirs();
		this.acpdh = new ACPDataHandler(new File(pluginFolder + File.separator + "flymode.data"), new File(pluginFolder + File.separator + "godmode.data"), this);
		
		acpdh.loadData();
		MSG.getMessages();
		
		//Update Check
		Boolean updateCheck = Boolean.valueOf(getConfig().getBoolean("UpdateCheck"));
		Boolean autoUpdate = Boolean.valueOf(getConfig().getBoolean("AutoUpdate"));
		this.updateCheck(updateCheck, autoUpdate, 47121);
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.acpl, this);
		pm.registerEvents(this.acpgml, this);
		pm.registerEvents(this.acpfml, this);
		
		log.info(String.format("Version %s by jacklin213 has been Enabled!", getDescription().getVersion()));
	}

	public void onDisable() {
		acpdh.saveData();
		
		log.info(String.format("Disabled Version %s", getDescription().getVersion()));
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
					if (sender.hasPermission("acp.reload")){
						reloadConfig();
						MSG.getMessages();
						sender.sendMessage(MSG.chatPluginPrefix + ChatColor.GREEN + "Configuration Reloaded!");
						return true;
					} else {
						sender.sendMessage(MSG.chatPluginPrefix + MSG.noPermMessage);
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("help")){
					MSG.pluginHelp(sender);
					return true;
				} else {
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
		File configFile = new File(getDataFolder() + File.separator	+ "config.yml");
		if (!configFile.exists()) {
			// Tells console its creating a config.yml
			log.info("Cannot find config.yml, Generating now....");
			log.info("Config generated !");
			this.saveDefaultConfig();
		}
	}
	
	
	public void godMode(CommandSender sender){
		if (sender instanceof Player){
			Player player = (Player) sender;
			String pID = player.getUniqueId().toString();
			if (player.hasPermission("acp.god")){
				if (this.godModeEnabled.contains(pID)){
					this.godModeEnabled.remove(pID);
					player.sendMessage(MSG.chatPluginPrefix + ChatColor.RED + " Godmode is now Disabled.");
					acpdh.saveData();
					return;
				} else {
					this.godModeEnabled.add(pID);
					player.sendMessage(MSG.chatPluginPrefix + ChatColor.GREEN + " Godmode is now Enabled.");
					acpdh.saveData();
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
			String pID = player.getUniqueId().toString();
			if (player.hasPermission("acp.fly")) {
				if (this.flyModeEnabled.contains(pID)){
					this.flyModeEnabled.remove(pID);
					player.setFlying(false);
					player.setAllowFlight(false);
					player.sendMessage(MSG.chatPluginPrefix + ChatColor.RED + " Flymode is now Disabled.");
					acpdh.saveData();
					return;
				} else {
					this.flyModeEnabled.add(pID);
					player.setAllowFlight(true);
					player.setFlySpeed(0.1F);
					player.sendMessage(MSG.chatPluginPrefix + ChatColor.GREEN + " Flymode is now Enabled.");
					acpdh.saveData();
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
	
	private void updateCheck(boolean updateCheck, boolean autoUpdate, int ID){
		if(updateCheck && (autoUpdate == false)){
			updater = new Updater(this, ID, this.getFile(), UpdateType.NO_DOWNLOAD, true);
			if (updater.getResult() == UpdateResult.UPDATE_AVAILABLE) {
			    log.info("New version available! " + updater.getLatestName());
			}
			if (updater.getResult() == UpdateResult.NO_UPDATE){
				log.info(String.format("You are running the latest version of %s", getDescription().getName()));
			}
		}
		if(autoUpdate && (updateCheck == false)){
			updater = new Updater(this, ID, this.getFile(), UpdateType.NO_VERSION_CHECK, true);
		} 
		if(autoUpdate && updateCheck){
			updater = new Updater(this, ID, this.getFile(), UpdateType.DEFAULT, true);
			if (updater.getResult() == UpdateResult.UPDATE_AVAILABLE) {
			    log.info("New version available! " + updater.getLatestName());
			}
			if (updater.getResult() == UpdateResult.NO_UPDATE){
				log.info(String.format("You are running the latest version of %s", getDescription().getName()));
			}
		}
	}
	
	private void setLogger(){
		log = getLogger();
	}

}
