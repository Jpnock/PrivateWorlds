package net.jpnock.privateworlds.commands;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import net.jpnock.privateworlds.PrivateWorlds;
import net.jpnock.privateworlds.database.DB_RETURN_CODE;
import net.jpnock.privateworlds.language.Language;

import org.bukkit.entity.Player;

public class InviteCommand extends PWCommandBase
{

	public InviteCommand() 
	{
		super("invite", "privateworlds", "privateworlds.world.invite", Language.UserLang.PRIVATEWORLDS_INVITE_CMD_USAGE, Language.UserLang.PRIVATEWORLDS_INVITE_CMD_DESC);
	}
	
	public void run(Player player, String userWorldName, Player invitedPlayer, String invitedPlayerAllowedTime)
	{
		if(!hasPermission(player))
		{
			//Tell the player they do not have the required permissions.
			player.sendMessage(Language.UserLang.NO_PERMISSION);
			return;
		}
		
		long msInviteTime = 0;
		
		if(!(invitedPlayerAllowedTime == ""))
		{
			float flInvitedPlayerAllowedTime = 0;
			try 
			{
				// This will parse the string into an float up to the point it sees a char. So we can do stuff like 10h etc...
				flInvitedPlayerAllowedTime = NumberFormat.getInstance().parse(invitedPlayerAllowedTime).floatValue();
			} 
			catch (ParseException e) 
			{
				player.sendMessage(Language.UserLang.INVITE_DURATION_INVALID);
				return;
			}
			
			
			int allowedTimeIndex = invitedPlayerAllowedTime.lastIndexOf(String.valueOf(flInvitedPlayerAllowedTime));
			String timeMeasurement = invitedPlayerAllowedTime.substring(allowedTimeIndex + String.valueOf(flInvitedPlayerAllowedTime).length() - 1);
			
			
			if(timeMeasurement.length() == 1)
			{
				switch(timeMeasurement.toLowerCase())
				{
					case "m": // Minutes
						msInviteTime = TimeUnit.HOURS.toMillis((long) flInvitedPlayerAllowedTime); //(long)(flInvitedPlayerAllowedTime * 3600);
						break;
					case "h": // Hours
						msInviteTime = TimeUnit.HOURS.toMillis((long) flInvitedPlayerAllowedTime); //(long)(flInvitedPlayerAllowedTime * 216000);
						break;
					case "d": // Days
						msInviteTime = (long) flInvitedPlayerAllowedTime * 24 * 60 * 60 * 1000;
						break;
					default:
						return;
						
					// Months not implemented as using this current setup we can only have 1 letter to represent the measurement and 'm' is taken by minutes
				}
			}
			else
			{
				player.sendMessage(Language.UserLang.INVITE_DURATION_INVALID);
				return;
			}
		}
		
		
		DB_RETURN_CODE invitedPlayerResult = PrivateWorlds.dataHandler.insertPlayerInvitedWorld(player.getUniqueId(), userWorldName, invitedPlayer.getUniqueId(), msInviteTime);
		if(invitedPlayerResult == DB_RETURN_CODE.SUCCESSFUL)
			player.sendMessage(String.format(Language.UserLang.PLAYER_INVITED, invitedPlayer.getName(), userWorldName));
		else if(invitedPlayerResult == DB_RETURN_CODE.WORLD_DOES_NOT_EXIST)
			player.sendMessage(Language.UserLang.WORLD_DOES_NOT_EXIST);
		else
			player.sendMessage(Language.UserLang.ERROR_OCCURRED);
		
	}

}
