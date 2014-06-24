package net.jpnock.privateworlds;

import org.bukkit.ChatColor;

public class Language
{
	public static final String PLUGIN_NAMETAG = "[PrivateWorlds]";
	public static final String PLUGIN_NAMETAG_PLAYER = ChatColor.GOLD + "[PrivateWorlds]";
	public static String[] HELP_MENU = PrivateWorlds.cmds.generateHelpMenu();
	
	public static final String PRIVATEWORLDS_CMD_USAGE = "/privateworlds";
	public static final String PRIVATEWORLDS_CMD_DESC = "Displays the help menu";
	
	public static final String PRIVATEWORLDS_CREATE_CMD_USAGE = "/privateworlds create";
	public static final String PRIVATEWORLDS_CREATE_CMD_DESC = "Creates a new world.";
	
	public static final String PRIVATEWORLDS_TP_CMD_USAGE = "/privateworlds tp [worldname]";
	public static final String PRIVATEWORLDS_TP_CMD_DESC = "Teleports you to a world that you have created.";
	
	public static final String WORLD_CREATED_MESSAGE_PLAYER = PLUGIN_NAMETAG_PLAYER + ChatColor.AQUA + " World created!";
	
	public static final String WORLD_CREATING = PLUGIN_NAMETAG_PLAYER + ChatColor.AQUA + " Creating world!";
	
	public static final String WORLD_DOES_NOT_EXIST = PLUGIN_NAMETAG_PLAYER + ChatColor.AQUA + " That world does not exist!";
	
	public static final String NO_PERMISSION = PLUGIN_NAMETAG_PLAYER + ChatColor.AQUA + " You do not have permission to use that command!";
	
	public static final String TELEPORTED_TO_WORLD = PLUGIN_NAMETAG_PLAYER + ChatColor.AQUA + " You have been teleported to world: %1$s";
}
