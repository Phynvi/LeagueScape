package ardi.model.players.achievement;

import ardi.model.players.Client;

public class Achievement {

	/**
	 * @author Valiant - http://www.rune-server.org/members/valiant/
	 * 
	 *         Basic Achievements
	 * 
	 */

	/**
	 * Tells the achievementComplete method what the name of the achievement you
	 * completed was.
	 */
	public String achievementName;
	
	private Client c;

	public Achievement(Client c) {
		this.c = c;
	}

	/**
	 * Updates the AchievementTab to display users status on an achievement
	 * 
	 * (Method also called on players login)
	 * 
	 * [tinderBoxPro]
	 * 
	 */
	public void updateAchievementTab() {
		c.getPA().loadQuests();
		if (c.fireslit == 100 || c.fireslit >= 100) {
			//TODO: Sendframes
		}
		if (c.crabsKilled == 50 || c.crabsKilled >= 50) {
			//TODO: Sendframes
		}
	}

	/**
	 * Handles when a user completes an achievement and updates their total
	 * points and calls updateAchievement method
	 */
	public void achievementComplete() {
		this.updateAchievementTab();
		c.achievementsCompleted++;
		c.achievementPoints++;
		c.sendMessage("@blu@@cr7@You have completed the achievement "
				+ this.achievementName + "..");
	}

	/**
	 * Reward Options - Easy, Medium, Hard If the player receiving the reward
	 * does not have room for the reward it is sent to their bank.
	 */
	public void rewardEASY() {
		if (c.getItems().freeSlots() > 1) {
			c.getItems().addItem(995, 100000);
			c.sendMessage("@blu@You have been rewarded 100k for this achievement!");
		} else {
			int[][] itemsToAdd = { { 995, 100000 } };
			for (int i = 0; i < itemsToAdd.length; i++) {
				c.getItems().addItemToBank(itemsToAdd[i][0], itemsToAdd[i][1]);
				c.sendMessage("@blu@You have been rewarded 100k for this achievement!");
			}
		}
	}

	public void rewardMEDIUM() {
		if (c.getItems().freeSlots() > 1) {
			c.getItems().addItem(995, 200000);
			c.sendMessage("@blu@You have been rewarded 200k for this achievement!");
		} else {
			int[][] itemsToAdd = { { 995, 200000 } };
			for (int i = 0; i < itemsToAdd.length; i++) {
				c.getItems().addItemToBank(itemsToAdd[i][0], itemsToAdd[i][1]);
				c.sendMessage("@blu@You have been rewarded 200k for this achievement!");
			}
		}
	}

	public void rewardHARD() {
		if (c.getItems().freeSlots() > 1) {
			c.getItems().addItem(995, 300000);
			c.sendMessage("@blu@You have been rewarded 1\300k for this achievement!");
		} else {
			int[][] itemsToAdd = { { 995, 300000 } };
			for (int i = 0; i < itemsToAdd.length; i++) {
				c.getItems().addItemToBank(itemsToAdd[i][0], itemsToAdd[i][1]);
				c.sendMessage("@blu@You have been rewarded 300k for this achievement!");
			}
		}
	}

	/**
	 * TinderboxPro - Light 100 Fires
	 */
	public void tinderboxPro() {
		this.achievementName = "'Light 100 Fires'";
		this.achievementComplete();
		this.rewardEASY();
	}

	/**
	 * Kill 50 - Rockcrabs
	 */
	public void crabKiller() {
		this.achievementName = "'Kill 50 Rockcrabs'";
		this.achievementComplete();
		this.rewardEASY();
	}

	/**
	 * killedMonsters - Kill 250 Monsters
	 */
	public void killedMonsters() {
		this.achievementName = "'Kill 250 NPC's'";
		this.achievementComplete();
		this.rewardMEDIUM();
	}

	/**
	 * eatSharks - Eat 50 Sharks
	 */
	public void eatSharks() {
		this.achievementName = "'Eat 50 Sharks'";
		this.achievementComplete();
		this.rewardEASY();
	}

	/**
	 * Cutter - Cut 100 Logs
	 */
	public void WoodCutter() {
		this.achievementName = "'Cut 100 Logs'";
		this.achievementComplete();
		this.rewardEASY();
	}
	
	/**
	 * KillPlayers - Kill 25 Players
	 */
	public void killPlayers() {
		this.achievementName = "'Kill 25 Players'";
		this.achievementComplete();
		this.rewardMEDIUM();
	}

}