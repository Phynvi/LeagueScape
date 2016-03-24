package ardi.model.minigames;

import ardi.model.npcs.NPCHandler;
import ardi.model.players.Client;
import ardi.model.players.PlayerHandler;

public class PestControl {

	/**
	 * Slightly Revised Pest-Control
	 * 
	 * Ardi RSPS
	 */

	public PestControl() {

	}

	public final int GAME_TIMER = 150;
	public final int WAIT_TIMER = 15;

	public int gameTimer = -1;
	public int waitTimer = 25;
	public int properTimer = 0;
	public int Portal1kill = 0;
	public int Portal2kill = 0;
	public int Portal3kill = 0;
	public int Portal4kill = 0;

	public void process() {
		setInterface();
		if (properTimer > 0) {
			properTimer--;
			return;
		} else {
			properTimer = 4;
		}
		if (waitTimer > 0)
			waitTimer--;
		else if (waitTimer == 0)
			startGame();
		if (gameTimer > 0) {
			gameTimer--;
			if (allPortalsDead()) {
				//endGame(true);
			}
		} else if (gameTimer == 0){}
		//	endGame(false);
	}

	public void startGame() {
		if (playersInBoat() > 2) {
			gameTimer = GAME_TIMER;
			waitTimer = -1;
			spawnNpcs();
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					if (PlayerHandler.players[j].inPcBoat()) {
						movePlayer(j);
					}
				}
			}
		} else {
			waitTimer = WAIT_TIMER;
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					if (PlayerHandler.players[j].inPcBoat()) {
						Client c = (Client) PlayerHandler.players[j];
						c.sendMessage("There need to be at least 3 players to start a game of pest control.");
					}
				}
			}
		}
	}

	public int playersInBoat() {
		int count = 0;
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].inPcBoat()) {
					count++;
				}
			}
		}
		return count;
	}

	public void setInterface() {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].inPcBoat()) {
					Client c = (Client) PlayerHandler.players[j];
					c.getPA().sendFrame126("Next Departure: " + waitTimer + "",
							21120);
					c.getPA().sendFrame126(
							"Players Ready: " + playersInBoat() + "", 21121);
					c.getPA().sendFrame126("(Need 3 to 25 players)", 21122);
					c.getPA().sendFrame126("Points: " + c.pcPoints + "", 21123);
				}
				if (PlayerHandler.players[j].inPcGame()) {
					Client c = (Client) PlayerHandler.players[j];
					for (j = 0; j < NPCHandler.npcs.length; j++) {
						if (NPCHandler.npcs[j] != null) {
							if (NPCHandler.npcs[j].npcType == 6142)
								if (Portal1kill == 0) {
									c.getPA().sendFrame126(
											"" + NPCHandler.npcs[j].HP + "",
											21111);
									if (NPCHandler.npcs[j].HP == 0) {
										Portal1kill = 1;
									}
								} else {
									c.getPA().sendFrame126("Dead", 21111);
								}
							if (NPCHandler.npcs[j].npcType == 6143)
								if (Portal2kill == 0) {
									c.getPA().sendFrame126(
											"" + NPCHandler.npcs[j].HP + "",
											21112);
									if (NPCHandler.npcs[j].HP == 0) {
										Portal2kill = 1;
									}
								} else {
									c.getPA().sendFrame126("Dead", 21112);
								}
							if (NPCHandler.npcs[j].npcType == 6144)
								if (Portal3kill == 0) {
									c.getPA().sendFrame126(
											"" + NPCHandler.npcs[j].HP + "",
											21113);
									if (NPCHandler.npcs[j].HP == 0) {
										Portal3kill = 1;
									}
								} else {
									c.getPA().sendFrame126("Dead", 21113);
								}
							if (NPCHandler.npcs[j].npcType == 6145)
								if (Portal4kill == 0) {
									c.getPA().sendFrame126(
											"" + NPCHandler.npcs[j].HP + "",
											21114);
									if (NPCHandler.npcs[j].HP == 0) {
										Portal4kill = 1;
									}
								} else {
									c.getPA().sendFrame126("Dead", 21114);
								}
							if (NPCHandler.npcs[j].npcType == 3782)
								c.getPA().sendFrame126(
										"" + NPCHandler.npcs[j].HP + "", 21115);
						}
					}
					c.getPA().sendFrame126("0", 21116);
					c.getPA().sendFrame126("Time remaining: " + gameTimer + "",
							21117);
				}
			}
		}
	}

	

	public boolean allPortalsDead() {
		int count = 0;
		for (int j = 0; j < NPCHandler.npcs.length; j++) {
			if (NPCHandler.npcs[j] != null) {
				if (NPCHandler.npcs[j].npcType >= 6142
						&& NPCHandler.npcs[j].npcType <= 6145)
					if (NPCHandler.npcs[j].needRespawn)
						count++;
			}
		}
		return count >= 4;
	}

	public void movePlayer(int index) {
		Client c = (Client) PlayerHandler.players[index];
		if (c.combatLevel < 40) {
			c.sendMessage("You must be at least 40 to enter this boat.");
			return;
		}
		c.getPA().movePlayer(2658, 2611, 0);
	}

	public void spawnNpcs() {
		NPCHandler.spawnNpc2(3782, 2656, 2592, 0, 0, 200, 0, 0, 100);
		NPCHandler.spawnNpc2(6142, 2628, 2591, 0, 0, 200, 0, 0, 100);
		NPCHandler.spawnNpc2(6143, 2680, 2588, 0, 0, 200, 0, 0, 100);
		NPCHandler.spawnNpc2(6144, 2669, 2570, 0, 0, 200, 0, 0, 100);
		NPCHandler.spawnNpc2(6145, 2645, 2569, 0, 0, 200, 0, 0, 100);
	}

}