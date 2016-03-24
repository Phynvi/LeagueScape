package ardi.model.players.lolspells;

import ardi.model.players.Client;

public class LeagueVariables {

	Client player;
	public LeagueVariables(Client player) {
		this.player = player;
	}
	
	public boolean choosingTeleport;
	public boolean channelingTeleport;
}
