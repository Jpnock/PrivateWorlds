package net.jpnock.privateworlds.commands;

import org.bukkit.entity.Player;

public abstract class PWCommandBase
{
	public final String cmdName;
	public final String permNode;
	public String subCMDName;
	public final String cmdUsage;
	public final String cmdDesc;
	
	public PWCommandBase(final String cmdName, final String permNode, final String cmdUsage, final String cmdDesc)
	{
		this.cmdName = cmdName;
		this.permNode = permNode;
		this.cmdUsage = cmdUsage;
		this.cmdDesc = cmdDesc;
	}
	
	public PWCommandBase(final String cmdName, final String subCMDName, final String permNode, final String cmdUsage, final String cmdDesc)
	{
		this.cmdName = cmdName;
		this.subCMDName = subCMDName;
		this.permNode = permNode;
		this.cmdUsage = cmdUsage;
		this.cmdDesc = cmdDesc;
	}
	
	public boolean hasPermission(Player player)
	{
		//return player.hasPermission(permNode);
		return true;
	}
}