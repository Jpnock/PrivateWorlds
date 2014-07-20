package net.jpnock.privateworlds.commands;

import org.bukkit.entity.Player;

public abstract class PWCommandBase
{
	public String cmdName;
	public String permNode;
	public String subCMDName;
	public String cmdUsage;
	public String cmdDesc;
	
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
		// If no permission node set, say we have permission.
		if(permNode == null || permNode.equals(""))
			return true;
		
		return player.hasPermission(permNode);
		//return true;
	}
	
	public String getCmdName()
	{
		return cmdName;
	}
	
	public void setCmdName(String cmdName)
	{
		this.cmdName = cmdName;
	}

}