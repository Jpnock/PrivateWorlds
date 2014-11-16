package net.jpnock.privateworlds.database;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public interface IDataHandler
{
	
	/**
	 * Creates the databases which the plugin requires 
	 * @return True: if the creation of the tables was successful.
	 */
	public DB_RETURN_CODE createDatabaseTables();
	
	
	/** 
	 * Gets all of the worlds that the player owns and has created themselves.
	 * @param playerUUID -  The UUID of the player for which you want to get the worlds they own.
	 * @return An ArrayList<String> of the players owned worlds or null.
	 */
	public ArrayList<String> getPlayerOwnedWorlds(UUID playerUUID);
	
	
	/**
	 * Gets all of the worlds that a player is currently invited to.
	 * @param playerUUID - The UUID of the player which you want to get the worlds they are invited to (excludes any worlds they own).
	 * @return A HashMap<String, Integer> of the worlds a player is invited to and the users UID who owns that world or null.
	 */
	public HashMap<String, Integer> getPlayerInvitedWorlds(UUID playerUUID);

	/**
	 * Inserts a player owned private world into the database.
	 * @param playerUUID - The UUID of the player creating the private world.
	 * @param worldName - Name of what the world is called.
	 * @return A code from the DB_RETURN_CODE enum.
	 */
	public DB_RETURN_CODE insertPlayerOwnedWorld(UUID playerUUID, String worldName);
	
	/**
	 * Deletes a player owned private world from the database.
	 * @param playerUUID - The UUID of the player owning the private world.
	 * @param worldName - Name of what the world is called.
	 * @return A code from the DB_RETURN_CODE enum.
	 */
	public DB_RETURN_CODE deletePlayerOwnedWorld(UUID playerUUID, String worldName);

	/**
	 * Inserts or updates a player in the PW_Users table.
	 * @param playerUUID - The UUID of the player.
	 * @param userName - The username of the player.
	 * @return Returns a code from the DB_RETURN_CODE enum.
	 */
	public DB_RETURN_CODE insertOrUpdatePlayer(UUID playerUUID, String userName);

	/**
	 * Gets the internalUID of a player from a UUID given.
	 * @param playerUUID - The UUID of the player.
	 * @return The internalUID of the player or -1.
	 */
	public int getInternalUIDFromUUID(UUID playerUUID);

	/**
	 * Inserts a player into the invited worlds table.
	 * @param playerOwnerUUID
	 * @param worldName
	 * @param playerInvitedUUID
	 * @param msLengthOfTime - Length of invite time in milliseconds or 0 for a permanent invite.
	 * @return A code from the DB_RETURN_CODE enum.
	 */
	public DB_RETURN_CODE insertPlayerInvitedWorld(UUID playerOwnerUUID, String worldName, UUID playerInvitedUUID, long msLengthOfTime);

	/**
	 * Gets the display name of the player from a UUID given.
	 * @param playerUUID - The UUID of the player.
	 * @return The display name of the player or an empty string.
	 */
	public String getUserNameFromUUID(UUID uniqueId);
	
	/**
	 * Gets the display name of the player from a internal userID given.
	 * @param internalUID - The internal userID of the player.
	 * @return The display name of the player or an empty string.
	 */
	public String getUserNameFromInternalUID(int integer);
	
	/**
	 * Gets the UUID of the player from a internal userID given.
	 * @param internalUID - The internal userID of the player.
	 * @return The UUID of the player or null.
	 */
	public UUID getUUIDFromInternalUID(int integer);
	
	/**
	 * Updates the InviteEndTime of an invited player to a PrivateWorld. If the newTime parameter is null then we will set the
	 * timestamp as to being the current time, thus expiring the invitation.
	 * @param ownerPlayerUID - The internalUID of the player who owns the world.
	 * @param invitedPlayerUID - The internalUID of the player whose InviteEndTime will be updated.
	 * @param worldName - The name of the world that you want this operation to happen on.
	 * @param newTime - The timestamp that you want to update the InviteEndTime to be (if null, we expire the time and set it to now)
	 * @return A code from the DB_RETURN_CODE enum is returned. 
	 */
	public DB_RETURN_CODE updateInvitedWorldEndTime(int ownerPlayerUID, int invitedPlayerUID, String worldName, Timestamp newTime);
	
	/**
	 * Allows the 
	 * @param dbTableVersionNum
	 * @return A code from the DB_RETURN_CODE enum is returned.
	 */
	public DB_RETURN_CODE performDataOperations(int dbTableVersionNum);
}
