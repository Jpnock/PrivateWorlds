package net.jpnock.privateworlds.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;

import net.jpnock.privateworlds.PrivateWorlds;

public class Commands 
{
	public static final PrivateWorldsCommand pwCommand = new PrivateWorldsCommand();
	public static final TPCommand tpCommand = new TPCommand();
	public static final CreateCommand createCommand = new CreateCommand();
	
	
	public String[] generateHelpMenu()
	{
		ArrayList<PWCommandBase> commandsList = new ArrayList<PWCommandBase>();
		ArrayList<String> helpMenuTemp = new ArrayList<String>();
		
		commandsList.add(pwCommand); commandsList.add(tpCommand); commandsList.add(createCommand);
		
		helpMenuTemp.add(ChatColor.GOLD + "PrivateWorlds - Version: " + PrivateWorlds.pdf.getVersion());
		
		for(PWCommandBase pwBaseCmd : commandsList)
		{
			helpMenuTemp.add(ChatColor.YELLOW + pwBaseCmd.cmdUsage + ChatColor.WHITE + " - " + pwBaseCmd.cmdDesc);
		}
		
		return helpMenuTemp.toArray(new String[helpMenuTemp.size()]);
	}
}
