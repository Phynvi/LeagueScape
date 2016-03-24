package ardi.model.players.lolspells;

import ardi.Server;
import ardi.event.Task;
import ardi.model.players.Client;

public class LeagueSpells {
	//2400 9500
	public static final int FLASH = 2357;

	public static void Flash(Client player) {
		Server.getTaskScheduler().schedule(new Task(4, false) {
			@Override
			protected void execute() {
				stop();
			}		
		});
	}

	/**
	 * 
	 * @param player
	 */
	public static void firstClickItem(Client player, int item) {
		switch (item) {
		case FLASH:
			Flash(player);
			break;
		}
	}

	public static void cancelVariablesOnWalk(Client player) {
		player.LV.choosingTeleport = false;
	}
	public static void Teleport(Client player) {
		player.LV.choosingTeleport = true;
		player.getDH().sendOption3("Mid", "Dragon", "Baron");
								//9167   9168		9169
	}
	
	public static void ClickButton(Client player, int buttonID) {
		if(!player.LV.choosingTeleport)
			return;
		player.LV.choosingTeleport = false;
		switch(buttonID) {
		case 9167:
			channelTeleport(player, 2378, 3103);
			break;
		}
	}
	
	public static void channelTeleport(Client player, int coordX, int coordY) {
		
		player.startAnimation(714);
		player.gfx100(111);
		Server.getTaskScheduler().schedule(new Task(3, false) {
			@Override
			protected void execute() {		
				player.teleportToX = coordX;
				player.teleportToY = coordY;
				player.startAnimation(715);
				player.sendMessage("You have been teleported");
				stop();
			}		
		});

	}
}
