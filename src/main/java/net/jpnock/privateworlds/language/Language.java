package net.jpnock.privateworlds.language;

import net.jpnock.privateworlds.PrivateWorlds;

import org.bukkit.ChatColor;

public class Language {

	public static class UserLang
	{
		public static final String PLUGIN_NAMETAG = "[PrivateWorlds]";
		//public static final String PLUGIN_NAMETAG_PLAYER = ChatColor.GOLD + "[PrivateWorlds]";
		public static final String PLUGIN_NAMETAG_AND_COLOUR_PLAYER = ChatColor.GOLD + "[PrivateWorlds]" + ChatColor.AQUA;
		public static String[] HELP_MENU = PrivateWorlds.cmds.generateHelpMenu();
		
		public static final String PRIVATEWORLDS_CMD_USAGE = "/privateworlds";
		public static final String PRIVATEWORLDS_CMD_DESC = "Displays the help menu";
		
		public static final String PRIVATEWORLDS_CREATE_CMD_USAGE = "/privateworlds create <worldname>";
		public static final String PRIVATEWORLDS_CREATE_CMD_DESC = "Creates a new world.";
		
		public static final String PRIVATEWORLDS_TP_CMD_USAGE = "/privateworlds tp <worldname>";
		public static final String PRIVATEWORLDS_TP_CMD_DESC = "Teleports you to a world that you have created.";
		
		public static final String PRIVATEWORLDS_TPI_CMD_USAGE = "/privateworlds tpi <worldname> <playername>";
		public static final String PRIVATEWORLDS_TPI_CMD_DESC = "Teleports you to a world that you have been invited to.";
		
		public static final String PRIVATEWORLDS_LIST_CMD_USAGE = "/privateworlds list";
		public static final String PRIVATEWORLDS_LIST_CMD_DESC = "Shows you all of the worlds you are currently the owner of.";
		
		public static final String PRIVATEWORLDS_INVITE_CMD_USAGE = "/privateworlds invite <playername> <worldname> [timelength]";
		public static final String PRIVATEWORLDS_INVITE_CMD_DESC = "Invites a player to the specified private world. The time length (which is optional) is in the format, for example: 10h = 10 hours | 20m =  20 minutes | 2d = 2 days.";
		
		public static final String PRIVATEWORLDS_LISTINV_CMD_USAGE = "/privateworlds listinv";
		public static final String PRIVATEWORLDS_LISTINV_CMD_DESC = "Shows you all of the worlds you are currently invited to.";
		
		public static final String PRIVATEWORLDS_REMOVEACCESS_CMD_USAGE = "/privateworlds removeaccess <username> <worldname>";
		public static final String PRIVATEWORLDS_REMOVEACCESS_CMD_DESC = "Removes access from a player to a specific world you have invited them to.";
		
		public static final String WORLD_CREATED_MESSAGE_PLAYER = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " World created!";
		
		public static final String WORLD_CREATING = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " Creating world! You may experience lag while this completes.";
		
		public static final String WORLD_DOES_NOT_EXIST = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " That world does not exist!";
		public static final String WORLD_FOLDER_DOES_NOT_EXIST = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " The folder for that world does not exist on the server! Contact your server adminstrator with this error message.";
		public static final String WORLD_DOES_NOT_EXIST_OR_NOT_INVITED = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " The world entered does not exist or the player specified is not invited to that world!";
		
		public static final String DOES_NOT_OWN_ANY_WORLDS_PLAYER = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " You currently do not own any worlds!";
		
		public static final String NO_PERMISSION = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " You do not have permission to use that command!";
		
		public static final String TELEPORTED_TO_WORLD = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " You have been teleported to world: %1$s";
		public static final String TELEPORTED_TO_INVITED_WORLD = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " You have been teleported to %1$s's world: %2$s";
		
		public static final String ERROR_OCCURRED = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " An error occurred! Try again.";
		
		public static final String LIST_OWNED_WORLDS_PLAYER = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " You currently own the following worlds: ";
		
		public static final String INVALID_CMD_ENTERED = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " You entered an invalid command!";
		
		public static final String PLAYER_NOT_ONLINE = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " That player is not online!";
		public static final String PLAYER_DOES_NOT_EXIST = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " That player has not played on this server before!";
		
		public static final String PLAYER_INVITED_ACCESS_REMOVED = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " Access removed to your world has been removed for that player!";
		
		
		public static final String CANNOT_INVITE_SELF = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " You cannot invite your self to a world that you own!";
		public static final String PLAYER_ALREADY_INVITED = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " That player is already invited to that world!";
		public static final String PLAYER_INVITED = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " You have invited %s to the world %s!";
		public static final String INVITE_DURATION_INVALID = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " Invalid invite duration time entered!";
		public static final String INVITED_TO_WORLDS_LIST = PLUGIN_NAMETAG_AND_COLOUR_PLAYER + " You have been invited to the following worlds:";
	}

	public static class ConsoleLang
	{
		public static final String INTERNAL_ERROR_OCCURRED = "An internal error occurred!";
		public static final String ERROR_OCCURRED = "An error occurred! Try again.";
	}

	public static class GeneralLang
	{
		
	}
}