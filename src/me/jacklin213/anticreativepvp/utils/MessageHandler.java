package me.jacklin213.anticreativepvp.utils;

import me.jacklin213.anticreativepvp.ACP;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
/**
 * AntiCreativePvp (ACP) Message Handler
 * @author jacklin213
 *
 */
public class MessageHandler {
	

	public static ACP plugin;
	/**
	 * No Permissions Message
	 */
	public String noPermMessage;
	/**
	 * The Plugin name which shows up in chat
	 */
	public String chatPluginPrefix;
	/**
	 * Player only command Message
	 */
	public String playerOnlyMessage;
	/**
	 * Not a valid argument Message
	 */
	public String notValidArgs;
	/**
	 * Cannot Hurt Players in form of Melee Message
	 */
	public String cantHurtPlayerMelee;
	/**
	 * Cannot Hurt Players in form of Ranged using bow and arrow Message
	 */
	public String cantHurtPlayerRanged;
	
	private String pluginHeader = (
			ChatColor.GREEN + "#" 
			+ ChatColor.GOLD + "0o" 
			+ ChatColor.RED + "=========" 
			+ ChatColor.GOLD + "o0"
			+ ChatColor.RED + "[" 
			+ ChatColor.GREEN + "AntiCreativePvp"
			+ ChatColor.RED + "]" 
			+ ChatColor.GOLD + "0o" 
			+ ChatColor.RED + "=========" 
			+ ChatColor.GOLD + "o0" 
			+ ChatColor.GREEN + "#"
			);
	private FileConfiguration config;
	
	public MessageHandler(ACP instance) {
		plugin = instance;
	}
	
	/**
	 * Gets all messsages in the config and assign it to the MessageHandler
	 */
	
	public void getMessages(){
		this.getCantHurtPlayerMelee();
		this.getCantHurtPlayerRanged();
		this.getChatPluginPrefix();
		this.getNoPermMessage();
		this.getNotValidArgs();
		this.getPlayerOnlyMessage();
		// Test to see if all string are valid
		this.test();
	}
	
	private void test(){
		if (validate(this.cantHurtPlayerMelee) == false){
			this.logInfo("Error in configuration, please check CantHurtPlayerMelee");
		}
		if (validate(this.cantHurtPlayerRanged) == false){
			this.logInfo("Error in configuration, please check CantHurtPlayerRanged");
		}
		if (validate(this.chatPluginPrefix) == false){
			this.logInfo("Error in configuration, please check ChatPluginPrefix");
		}
		if (validate(this.noPermMessage) == false){
			this.logInfo("Error in configuration, please check NoPermMessage");
		}
		
		if (validate(this.notValidArgs) == false){
			this.logInfo("Error in configuration, please check NotValidArgs");
		}
		if (validate(this.playerOnlyMessage) == false){
			this.logInfo("Error in configuration, please check PlayerOnlyMessage");
		}
		
	}
	
	/**
	 * Displays AntiCreativePvp (ACP) [INFO] the the sender
	 * @param sender - CommandSender
	 */
	public void pluginInfo(CommandSender sender){
		sender.sendMessage(pluginHeader);
		sender.sendMessage(ChatColor.GOLD + "Name: " + ChatColor.AQUA + plugin.getDescription().getName());
		sender.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.AQUA + plugin.getDescription().getVersion());
		sender.sendMessage(ChatColor.GOLD + "Author: " + ChatColor.AQUA + "jacklin213");
	}
	/**
	 * Displays AntiCreativePvp (ACP) [HELP] the the sender
	 * @param sender - CommandSender
	 */
	public void pluginHelp(CommandSender sender){
		sender.sendMessage(pluginHeader);
		sender.sendMessage(ChatColor.GOLD + "/anticreativepvp " + ChatColor.WHITE + "OR" + ChatColor.GOLD + " /acp " + ChatColor.WHITE + ":" + ChatColor.AQUA + "Shows plugin name, version and author");
        sender.sendMessage(ChatColor.GOLD + "/acp help" + ChatColor.WHITE + ":" + ChatColor.AQUA + "Displays this page");
        sender.sendMessage(ChatColor.GOLD + "/acp fly" + ChatColor.WHITE + ":" + ChatColor.AQUA + "Sets fly mode on");
        sender.sendMessage(ChatColor.GOLD + "/acp god" + ChatColor.WHITE + ":" + ChatColor.AQUA + "Sets god mode on");
        sender.sendMessage(ChatColor.GOLD + "/acp reload" + ChatColor.WHITE + ":" + ChatColor.AQUA + "reloads the config");
	}
	
	private Boolean validate(String s){
		if (s != null){
			return true;
		}
		return false;
	}
	
	/**
	 * Logs info to console in correct format
	 * @param toLog - String to log
	 */
	public void logInfo(String toLog){
		plugin.log.info(String.format("[%s] " + toLog, plugin.getDescription().getName()));
	}
	
	/**
	 * Logs warning to console in correct format
	 * @param toLog - String to log
	 */
	public void logWarning(String toLog){
		plugin.log.warning(String.format("[%s] " + toLog, plugin.getDescription().getName()));
	}
	
	/**
	 * Logs severe to console in correct format
	 * @param toLog - String to log
	 */
	public void logSevere(String toLog){
		plugin.log.severe(String.format("[%s] " + toLog, plugin.getDescription().getName()));
	}
	
	// Gets stuff from config
	private FileConfiguration getConfig(){
		config = plugin.getConfig();
		return config;
	}
	
	private String getCantHurtPlayerMelee(){
		cantHurtPlayerMelee = ChatColor.translateAlternateColorCodes('&', getConfig().getString("CantHurtPlayerMelee") + " ");
		return notValidArgs;
	}
	
	private String getCantHurtPlayerRanged(){
		cantHurtPlayerRanged = ChatColor.translateAlternateColorCodes('&', getConfig().getString("CantHurtPlayerRanged") + " ");
		return notValidArgs;
	}
	
	private String getChatPluginPrefix(){
		chatPluginPrefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("PluginPrefix")+ " ");
		return chatPluginPrefix;
	}
	
	private String getNoPermMessage(){
		noPermMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("NoPermMessage") + " ");
		return noPermMessage;
	}
	
	private String getNotValidArgs(){
		notValidArgs = ChatColor.translateAlternateColorCodes('&', getConfig().getString("NotValidArgs") + " ");
		return notValidArgs;
	}
	
	private String getPlayerOnlyMessage(){
		playerOnlyMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("PlayerOnlyMessage")+ " ");
		return playerOnlyMessage;
	}
	
}
