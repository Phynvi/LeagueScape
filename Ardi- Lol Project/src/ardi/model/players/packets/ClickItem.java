package ardi.model.players.packets;

import ardi.model.players.Client;
import ardi.model.players.DiceHandler;
import ardi.model.players.PacketType;
import ardi.model.players.PlayerAssistant;
import ardi.util.Misc;
import ardi.Server;

/**
 * Clicking an item, bury bone, eat food etc
 **/
public class ClickItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int junk = c.getInStream().readSignedWordBigEndianA();
		int itemSlot = c.getInStream().readUnsignedWordA();
		int itemId = c.getInStream().readUnsignedWordBigEndian();
		int ROLL_TIMER = 3000;
		if (itemId != c.playerItems[itemSlot] - 1) {
			return;
		}
		if (itemId == DiceHandler.DICE_BAG) {
			DiceHandler.selectDice(c, itemId);
		}
		if (itemId > DiceHandler.DICE_BAG && itemId <= 15100) {
			if (c.playerRights <= 4) {
				c.sendMessage("You must be at least bronze donator to roll a dice.");
				return;
			}
			//DiceHandler.rollDice(c, itemId, false);
		}
		if (itemId == 15098) {
			if (System.currentTimeMillis() - c.diceDelay >= 5000) {
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("Ardi channel mate "+ Misc.ucFirst(c.playerName) + " rolled @red@"+ Misc.random(100) +"@bla@ on the percentile dice.");	
						c.diceDelay = System.currentTimeMillis();
					}
				}
			} else {
				c.sendMessage("You must wait 10 seconds to roll dice again.");
			}				
		}
		if (itemId == 15088) {
			if (System.currentTimeMillis() - c.diceDelay >= 5000) {
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("Ardi channel mate "+ Misc.ucFirst(c.playerName) + " rolled @red@"+ Misc.random(12) +"@bla@ on two six-sided dice.");
						c.diceDelay = System.currentTimeMillis();					
					}
				}
			} else {
				c.sendMessage("You must wait 10 seconds to roll dice again.");
			}				
		}	
			
		/* Mystery box */
		if (itemId == 6199) {
			int mysteryReward = Misc.random(15); // Coded by Sonic chao
			if (mysteryReward == 1) {
				c.getItems().addItemToBank(9921, 1);
				c.getItems().addItemToBank(9922, 1);
				c.getItems().addItemToBank(9923, 1);
				c.getItems().addItemToBank(9924, 1);
				c.getItems().addItemToBank(9925, 1);
				c.getItems().deleteItem(6199, 1);
				c.sendMessage("You've gained: @blu@A completed full skeleton!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 2) {
				c.getItems().addItemToBank(11019, 1);
				c.getItems().addItemToBank(11020, 1);
				c.getItems().addItemToBank(11021, 1);
				c.getItems().addItemToBank(11022, 1);
				c.getItems().addItemToBank(4566, 1);
				c.getItems().deleteItem(6199, 1);
				c.sendMessage("You've gained: @blu@A completed full chicken!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 3) {
				c.getItems().addItemToBank(6654, 1);
				c.getItems().addItemToBank(6655, 1);
				c.getItems().addItemToBank(6656, 1);
				c.getItems().deleteItem(6199, 1);
				c.sendMessage("You've gained: @blu@A completed full camo!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 4) {
				c.getItems().addItemToBank(6666, 1);
				c.getItems().addItemToBank(7003, 1);
				c.getItems().deleteItem(6199, 1);
				c.sendMessage("You've gained: @blu@A flippers and a camel mask!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 5) {
				c.getItems().addItemToBank(9920, 1);
				c.getItems().addItemToBank(10507, 1);
				c.getItems().deleteItem(6199, 1);
				c.sendMessage("You've gained: @blu@A jack lantern hat and a reindeer hat!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 6) {
				c.getItems().addItemToBank(1037, 1);
				c.getItems().addItemToBank(1961, 1);
				c.getItems().deleteItem(6199, 1);
				c.sendMessage("You've gained: @blu@A bunny ears and a easter egg!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 7) {
				c.getItems().addItemToBank(1419, 1);
				c.getItems().deleteItem(6199, 1);
				c.sendMessage("You've gained: @blu@A scythe!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 8) {
				c.getItems().addItemToBank(4565, 1);
				c.getItems().deleteItem(6199, 1);
				c.sendMessage("You've gained: @blu@A basket of eggs!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 9) {
				c.getItems().addItemToBank(5607, 1);
				c.getItems().deleteItem(6199, 1);
				c.sendMessage("You've gained: @blu@A grain!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 10) {
				c.getItems().addItemToBank(10836, 1);
				c.getItems().addItemToBank(10837, 1);
				c.getItems().addItemToBank(10838, 1);
				c.getItems().addItemToBank(10839, 1);
				c.getItems().deleteItem(6199, 1);
				c.sendMessage("You've gained: @blu@A completed full silly jester!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 11) {
				c.getItems().addItemToBank(6858, 1);
				c.getItems().addItemToBank(6859, 1);
				c.getItems().deleteItem(6199, 1);
				c.sendMessage("You've gained: @blu@A jester hat and scarf!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 12) {
				c.getItems().addItemToBank(6856, 1);
				c.getItems().addItemToBank(6857, 1);
				c.getItems().deleteItem(6199, 1);
				c.sendMessage("You've gained: @blu@A bobble hat and scarf!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 13) {
				c.getItems().addItemToBank(6860, 1);
				c.getItems().addItemToBank(6861, 1);
				c.getItems().deleteItem(6199, 1);
				c.sendMessage("You've gained: @blu@A tri-jester hat and scarf!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 14) {
				c.getItems().addItemToBank(6862, 1);
				c.getItems().addItemToBank(6863, 1);
				c.getItems().deleteItem(6199, 1);
				c.sendMessage("You've gained: @blu@A wolly hat and scarf!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 15) {
				c.getItems().addItemToBank(9470, 1);
				c.getItems().addItemToBank(10394, 1);
				c.getItems().deleteItem(6199, 1);
				c.sendMessage("You've gained: @blu@A gnome scarf and a flared trousers!");
				c.sendMessage("@red@The items has been added to your bank.");
			}
		}
		/*
		 * if (itemId == 4447) { c.usingLamp = true; c.antiqueLamp = true;
		 * c.normalLamp = false;
		 * c.sendMessage("You rub the antique lamp of 13 million experience..."
		 * ); c.getPA().showInterface(2808); } if (itemId == 2528) { c.usingLamp
		 * = true; c.normalLamp = true; c.antiqueLamp = false;
		 * c.sendMessage("You rub the lamp of 1 million experience...");
		 * c.getPA().showInterface(2808); }
		 */
		
		if (itemId == 2677) {
			int randomClue = Misc.random(11);

			if (randomClue == 0) {
				c.getItems().deleteItem(itemId, 1);
				c.getItems().addItem(2714, 1);
				c.sendMessage("You've recieved a easy clue scroll casket.");
			}
			if (randomClue == 0) {
				c.getItems().deleteItem(itemId, 1);
				c.getItems().addItem(2714, 1);
				c.sendMessage("You've recieved a easy clue scroll casket.");
			}
			if (randomClue == 1) {
				c.getItems().deleteItem(itemId, 1);
				c.getItems().addItem(2714, 1);
				c.sendMessage("You've recieved a easy clue scroll casket.");
			}
			if (randomClue == 2) {
				c.getItems().deleteItem(itemId, 1);
				c.getItems().addItem(2714, 1);
				c.sendMessage("You've recieved a easy clue scroll casket.");
			}
			if (randomClue == 3) {
				c.getItems().deleteItem(itemId, 1);
				c.getItems().addItem(2714, 1);
				c.sendMessage("You've recieved a easy clue scroll casket.");
			}
			if (randomClue == 4) {
				c.getItems().deleteItem(itemId, 1);
				c.getItems().addItem(2714, 1);
				c.sendMessage("You've recieved a easy clue scroll casket.");
			}
			if (randomClue == 5) {
				c.getItems().deleteItem(itemId, 1);
				c.getItems().addItem(2714, 1);
				c.sendMessage("You've recieved a easy clue scroll casket.");
			}
			if (randomClue == 6) {
				c.getItems().deleteItem(itemId, 1);
				c.getItems().addItem(2802, 1);
				c.sendMessage("You've recieved a medium clue scroll casket.");
			}
			if (randomClue == 7) {
				c.getItems().deleteItem(itemId, 1);
				c.getItems().addItem(2802, 1);
				c.sendMessage("You've recieved a medium clue scroll casket.");
			}
			if (randomClue == 8) {
				c.getItems().deleteItem(itemId, 1);
				c.getItems().addItem(2802, 1);
				c.sendMessage("You've recieved a medium clue scroll casket.");
			}
			if (randomClue == 9) {
				c.getItems().deleteItem(itemId, 1);
				c.getItems().addItem(2775, 1);
				c.sendMessage("You've recieved a hard clue scroll casket.");
			}
			if (randomClue == 10) {
				c.getItems().deleteItem(itemId, 1);
				c.getItems().addItem(2775, 1);
				c.sendMessage("You've recieved a hard clue scroll casket.");
			}

		}
		if (itemId == 405) {
			if (c.getItems().playerHasItem(405, 1)) {
				c.getItems().deleteItem(405, 1);
				c.getItems().addItem(995, 2500000);
			}
		}
		if (itemId == 6950) {
			if (c.playerMagicBook == 0) {
				if (c.playerLevel[6] >= 94) {
					if (System.currentTimeMillis() - c.lastVeng > 30000) {
						c.vengOn = true;
						c.lastVeng = System.currentTimeMillis();
						c.startAnimation(4410);
						c.gfx100(726);
					} else {
						c.sendMessage("You have to wait 30 seconds before you can use this spell again.");
					}
				} else {
					c.sendMessage("Your magic level has to be over 94 to use this spell.");
				}
			} else {
				c.sendMessage("You must be on the regular spellbook to use this spell.");
			}
		}

		if (itemId == 5073) {
			c.getItems().addItem(5075, 1);
			c.getItems().deleteItem(5073, 1);
			c.getItems().handleNests(itemId);
		}

		if (itemId == 299) {
			c.sendMessage("Flower game has been disabled.");
		}
	
		

		if (itemId == 952) {
			/*
			 * if(c.inArea(3553, 3301, 3561, 3294)) { c.teleTimer = 3;
			 * c.newLocation = 1; } else if(c.inArea(3550, 3287, 3557, 3278)) {
			 * c.teleTimer = 3; c.newLocation = 2; } else if(c.inArea(3561,
			 * 3292, 3568, 3285)) { c.teleTimer = 3; c.newLocation = 3; } else
			 * if(c.inArea(3570, 3302, 3579, 3293)) { c.teleTimer = 3;
			 * c.newLocation = 4; } else if(c.inArea(3571, 3285, 3582, 3278)) {
			 * c.teleTimer = 3; c.newLocation = 5; } else if(c.inArea(3562,
			 * 3279, 3569, 3273)) { c.teleTimer = 3; c.newLocation = 6; }
			 */
			c.sendMessage("Barrows is disabled, under development.");
		}
	}

}
