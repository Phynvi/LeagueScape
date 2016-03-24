package ardi.model.players.packets;

import ardi.Config;
import ardi.Server;
import ardi.model.items.GameItem;
import ardi.model.players.Client;
import ardi.model.players.DiceHandler;
import ardi.model.players.PacketType;
import ardi.model.players.PlayerHandler;
import ardi.model.players.lolspells.LeagueSpells;
//import ardi.model.players.skills.Cooking;
import ardi.util.Misc;

/**
 * Clicking most buttons
 **/
public class ClickingButtons implements PacketType {

	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		int actionButtonId = Misc.hexToInt(c.getInStream().buffer, 0,
				packetSize);
		if (c.isDead)
			return;
		if (c.playerRights == 3)
			Misc.println(c.playerName + " - actionbutton: " + actionButtonId);
		LeagueSpells.ClickButton(c, actionButtonId);
		switch (actionButtonId) {
		/* Lamp */	case 150:
			if (c.autoRet == 0)
				c.autoRet = 1;
			else
				c.autoRet = 0;
			break;
		case 10252:
			c.antiqueSelect = 0;
			c.sendMessage("You select Attack");
			break;
		case 10253:
			c.antiqueSelect = 2;
			c.sendMessage("You select Strength");
			break;
		case 10254:
			c.antiqueSelect = 4;
			c.sendMessage("You select Ranged");
			break;
		case 10255:
			c.antiqueSelect = 6;
			c.sendMessage("You select Magic");
			break;
		case 11000:
			c.antiqueSelect = 1;
			c.sendMessage("You select Defence");
			break;
		case 11001:
			c.antiqueSelect = 3;
			c.sendMessage("You select Hitpoints");
			break;
		case 11002:
			c.antiqueSelect = 5;
			c.sendMessage("You select Prayer");
			break;
		case 11003:
			c.antiqueSelect = 16;
			c.sendMessage("You select Agility");
			break;
		case 11004:
			c.antiqueSelect = 15;
			c.sendMessage("You select Herblore");
			break;
		case 11005:
			c.antiqueSelect = 17;
			c.sendMessage("You select Thieving");
			break;
		case 11006:
			c.antiqueSelect = 12;
			c.sendMessage("You select Crafting");
			break;
		case 11007:
			c.antiqueSelect = 20;
			c.sendMessage("You select Runecrafting");
			break;
		case 47002:
			c.antiqueSelect = 18;
			c.sendMessage("You select Slayer");
			break;
		case 54090:
			c.antiqueSelect = 19;
			c.sendMessage("You select Farming");
			break;
		case 11008:
			c.antiqueSelect = 14;
			c.sendMessage("You select Mining");
			break;
		case 11009:
			c.antiqueSelect = 13;
			c.sendMessage("You select Smithing");
			break;
		case 11010:
			c.antiqueSelect = 10;
			c.sendMessage("You select Fishing");
			break;
		case 11011:
			c.antiqueSelect = 7;
			c.sendMessage("You select Cooking");
			break;
		case 11012:
			c.antiqueSelect = 11;
			c.sendMessage("You select Firemaking");
			break;
		case 11013:
			c.antiqueSelect = 8;
			c.sendMessage("You select Woodcutting");
			break;
		case 11014:
			c.antiqueSelect = 9;
			c.sendMessage("You select Fletching");
			break;
		case 11015:
			if (c.usingLamp) {
				if (c.antiqueLamp && !c.normalLamp) {
					c.usingLamp = false;
					//c.getPA().addSkillXP(13100000, c.antiqueSelect);
					c.getItems().deleteItem2(4447, 1);
					c.sendMessage("The lamp mysteriously vanishes...");
					c.getPA().closeAllWindows();
				}
				if (c.normalLamp && !c.antiqueLamp) {
					c.usingLamp = false;
					//c.getPA().addSkillXP(1000000, c.antiqueSelect);
					c.getItems().deleteItem2(2528, 1);
					c.sendMessage("The lamp mysteriously vanishes...");
					c.getPA().closeAllWindows();
				}
			} else {
				c.sendMessage("You must rub a lamp to gain the experience.");
				return;
			}
			break;
		/* End lamp */
		case 89223: // Bank All
			for (int i = 0; i < c.playerItems.length; i++) {
				c.getItems().bankItem(c.playerItems[i], i, c.playerItemsN[i]);
			}
			break;

		/* Quest Tab by Ardi */
		case 28164: // 1st
			c.sendMessage("Your username is: @red@" + c.playerName + "@bla@.");
			break;
		case 28165: // 2nd
			c.sendMessage("You have @red@" + c.pkp + " @bla@Pk Points.");
			break;
		case 28166: // 3rd
			c.sendMessage("You have killed: @red@" + c.KC + " @bla@times.");
			break;
		case 28168: // 4th
			c.sendMessage("You have died: @red@" + c.DC + " @bla@times.");
			break;
		/*case 28172:
			if (c.expLock == false) {
				c.expLock = true;
				c.sendMessage("Your experience is now locked. You will not gain experience.");
				c.getPA().sendFrame126("@whi@EXP: @gre@LOCKED", 7340);
			} else {
				c.expLock = false;
				c.sendMessage("Your experience is now unlocked. You will gain experience.");
				c.getPA().sendFrame126("@whi@EXP: @gre@UNLOCKED", 7340);



		// case 58253:
		case 108005:
			c.getPA().showInterface(15106);
			// c.getItems().writeBonus();
			break;
		case 108006: // items kept on death
			c.getPA().sendFrame126("Ardi - Item's Kept on Death", 17103);
			c.StartBestItemScan(c);
			c.EquipStatus = 0;
			for (int k = 0; k < 4; k++)
				c.getPA().sendFrame34a(10494, -1, k, 1);
			for (int k = 0; k < 39; k++)
				c.getPA().sendFrame34a(10600, -1, k, 1);
			if (c.WillKeepItem1 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem1, 0,
						c.WillKeepAmt1);
			if (c.WillKeepItem2 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem2, 1,
						c.WillKeepAmt2);
			if (c.WillKeepItem3 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem3, 2,
						c.WillKeepAmt3);
			if (c.WillKeepItem4 > 0 && c.prayerActive[10])
				c.getPA().sendFrame34a(10494, c.WillKeepItem4, 3, 1);
			for (int ITEM = 0; ITEM < 28; ITEM++) {
				if (c.playerItems[ITEM] - 1 > 0
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem1 && ITEM == c.WillKeepItem1Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem2 && ITEM == c.WillKeepItem2Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem3 && ITEM == c.WillKeepItem3Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem4 && ITEM == c.WillKeepItem4Slot)) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus, c.playerItemsN[ITEM]);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem1 && ITEM == c.WillKeepItem1Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt1) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt1);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem2 && ITEM == c.WillKeepItem2Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt2) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt2);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem3 && ITEM == c.WillKeepItem3Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt3) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt3);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem4 && ITEM == c.WillKeepItem4Slot)
						&& c.playerItemsN[ITEM] > 1) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus, c.playerItemsN[ITEM] - 1);
					c.EquipStatus += 1;
				}
			}
			for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
				if (c.playerEquipment[EQUIP] > 0
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem1 && EQUIP + 28 == c.WillKeepItem1Slot)
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem2 && EQUIP + 28 == c.WillKeepItem2Slot)
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem3 && EQUIP + 28 == c.WillKeepItem3Slot)
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem4 && EQUIP + 28 == c.WillKeepItem4Slot)) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP],
							c.EquipStatus, c.playerEquipmentN[EQUIP]);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem1 && EQUIP + 28 == c.WillKeepItem1Slot)
						&& c.playerEquipmentN[EQUIP] > 1
						&& c.playerEquipmentN[EQUIP] - c.WillKeepAmt1 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP],
							c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt1);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem2 && EQUIP + 28 == c.WillKeepItem2Slot)
						&& c.playerEquipmentN[EQUIP] > 1
						&& c.playerEquipmentN[EQUIP] - c.WillKeepAmt2 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP],
							c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt2);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem3 && EQUIP + 28 == c.WillKeepItem3Slot)
						&& c.playerEquipmentN[EQUIP] > 1
						&& c.playerEquipmentN[EQUIP] - c.WillKeepAmt3 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP],
							c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt3);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem4 && EQUIP + 28 == c.WillKeepItem4Slot)
						&& c.playerEquipmentN[EQUIP] > 1
						&& c.playerEquipmentN[EQUIP] - 1 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP],
							c.EquipStatus, c.playerEquipmentN[EQUIP] - 1);
					c.EquipStatus += 1;
				}
			}
			c.ResetKeepItems();
			c.getPA().showInterface(17100);
			break;

		case 59004:
			c.getPA().removeAllWindows();
			break;

		case 9178:
			if (c.teleAction == 13) {
				c.getPA().spellTeleport(2839, 5293, 2);
				c.sendMessage("You must know it's not easy, get a team to own that boss!");
			}
			if (c.dialogueAction == 1658) {
				if (!c.getItems().playerHasItem(995, 2230000)) {
					c.sendMessage("You must have 2,230,000 coins to buy this package.");
					c.getPA().removeAllWindows();
					c.dialogueAction = 0;
					break;
				}
				c.dialogueAction = 0;
				c.getItems().addItemToBank(560, 4000);
				c.getItems().addItemToBank(565, 2000);
				c.getItems().addItemToBank(555, 6000);
				c.getItems().deleteItem(995, c.getItems().getItemSlot(995),
						2230000);
				c.sendMessage("@red@The runes has been added to your bank.");
				c.getPA().removeAllWindows();
				break;
			}
			if (c.usingGlory)
				// c.getPA().useCharge();
				c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y,
						0, "modern");
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(3428, 3538, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y,
						0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(3565, 3314, 0, "modern");
			if (c.dialogueAction == 20) {
				c.getPA().startTeleport(2897, 3618, 4, "modern");
				c.killCount = 0;
			} else if (c.teleAction == 2) {
				// barrows
				c.getPA().spellTeleport(3565, 3314, 0);
			}

			if (c.caOption4a) {
				c.getDH().sendDialogues(102, c.npcType);
				c.caOption4a = false;
			}
			if (c.caOption4c) {
				c.getDH().sendDialogues(118, c.npcType);
				c.caOption4c = false;
			}
			break;

		case 9179:
			if (c.teleAction == 13) {
				c.getPA().spellTeleport(2860, 5354, 2);
				c.sendMessage("You must know it's not easy, get a team to own that boss!");
			}
			if (c.dialogueAction == 1658) {
				if (!c.getItems().playerHasItem(995, 912000)) {
					c.sendMessage("You must have 912,000 coins to buy this package.");
					c.getPA().removeAllWindows();
					c.dialogueAction = 0;
					break;
				}
				c.dialogueAction = 0;
				c.getItems().addItemToBank(560, 2000);
				c.getItems().addItemToBank(9075, 4000);
				c.getItems().addItemToBank(557, 10000);
				c.getItems().deleteItem(995, c.getItems().getItemSlot(995),
						912000);
				c.sendMessage("@red@The runes has been added to your bank.");
				c.getPA().removeAllWindows();
				break;
			}
			if (c.usingGlory)
				// c.getPA().useCharge();
				c.getPA().startTeleport(Config.AL_KHARID_X, Config.AL_KHARID_Y,
						0, "modern");
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2884, 3395, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(3243, 3513, 0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(2444, 5170, 0, "modern");
			if (c.dialogueAction == 20) {
				c.getPA().startTeleport(2897, 3618, 12, "modern");
				c.killCount = 0;
			} else if (c.teleAction == 2) {
				// assault
				c.getPA().spellTeleport(2605, 3153, 0);
			}
			if (c.caOption4c) {
				c.getDH().sendDialogues(120, c.npcType);
				c.caOption4c = false;
			}
			if (c.caPlayerTalk1) {
				c.getDH().sendDialogues(125, c.npcType);
				c.caPlayerTalk1 = false;
			}
			break;

		case 9180:
			if (c.teleAction == 13) {
				c.getPA().spellTeleport(2925, 5334, 2);
				c.sendMessage("You must know it's not easy, get a team to own that boss!");
			}
			if (c.dialogueAction == 1658) {
				if (!c.getItems().playerHasItem(995, 1788000)) {
					c.sendMessage("You must have 1,788,000 coins to buy this package.");
					c.getPA().removeAllWindows();
					c.dialogueAction = 0;
					break;
				}
				c.dialogueAction = 0;
				c.getItems().addItemToBank(556, 1000);
				c.getItems().addItemToBank(554, 1000);
				c.getItems().addItemToBank(558, 1000);
				c.getItems().addItemToBank(557, 1000);
				c.getItems().addItemToBank(555, 1000);
				c.getItems().addItemToBank(560, 1000);
				c.getItems().addItemToBank(565, 1000);
				c.getItems().addItemToBank(566, 1000);
				c.getItems().addItemToBank(9075, 1000);
				c.getItems().addItemToBank(562, 1000);
				c.getItems().addItemToBank(561, 1000);
				c.getItems().addItemToBank(563, 1000);
				c.getItems().deleteItem(995, c.getItems().getItemSlot(995),
						1788000);
				c.sendMessage("@red@The runes has been added to your bank.");
				c.getPA().removeAllWindows();
				break;
			}
			if (c.usingGlory)
				// c.getPA().useCharge();
				c.getPA().startTeleport(Config.KARAMJA_X, Config.KARAMJA_Y, 0,
						"modern");
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2471, 10137, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(3363, 3676, 0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(2659, 2676, 0, "modern");
			if (c.dialogueAction == 20) {
				c.getPA().startTeleport(2897, 3618, 8, "modern");
				c.killCount = 0;
			} else if (c.teleAction == 2) {
				// duel arena
				c.getPA().spellTeleport(3366, 3266, 0);
			}
			if (c.caOption4c) {
				c.getDH().sendDialogues(122, c.npcType);
				c.caOption4c = false;
			}
			if (c.caPlayerTalk1) {
				c.getDH().sendDialogues(127, c.npcType);
				c.caPlayerTalk1 = false;
			}
			break;

		case 9181:
			if (c.teleAction == 13) {
				c.getPA().spellTeleport(2912, 5266, 0);
				c.sendMessage("You must know it's not easy, get a team to own that boss!");
			}
			if (c.dialogueAction == 1658) {
				c.getShops().openShop(5);
				c.dialogueAction = 0;
			}
			if (c.usingGlory)
				// c.getPA().useCharge();
				c.getPA().startTeleport(Config.MAGEBANK_X, Config.MAGEBANK_Y,
						0, "modern");
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2669, 3714, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(2540, 4716, 0, "modern");
			if (c.dialogueAction == 4) {
				c.getPA().startTeleport(3366, 3266, 0, "modern");
				c.sendMessage("Dueling is at your own risk. Refunds will not be given for items lost due to glitches.");
			} else if (c.teleAction == 2) {
				// tzhaar
				c.getPA().spellTeleport(2444, 5170, 0);
			}
			if (c.dialogueAction == 20) {
				// c.getPA().startTeleport(3366, 3266, 0, "modern");
				// c.killCount = 0;
				c.sendMessage("This will be added shortly");
			}
			if (c.caOption4c) {
				c.getDH().sendDialogues(124, c.npcType);
				c.caOption4c = false;
			}
			if (c.caPlayerTalk1) {
				c.getDH().sendDialogues(130, c.npcType);
				c.caPlayerTalk1 = false;
			}
			break;


		case 9157:
			if (c.dialogueAction == 2258) {
				c.getPA().startTeleport(3039, 4834, 0, "modern"); // first click
																	// teleports
																	// second
																	// click
																	// open
																	// shops
			}
			if (c.newPlayerAct == 1) {
				// c.isNewPlayer = false;
				c.newPlayerAct = 0;
				c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y,
						0, "modern");
				c.getPA().removeAllWindows();
			}
			
			if (c.dialogueAction == 6) {
				c.sendMessage("Slayer will be enabled in some minutes.");
				// c.getSlayer().generateTask();
				// c.getPA().sendFrame126("@whi@Task: @gre@"+Server.npcHandler.getNpcListName(c.slayerTask)+
				// " ", 7383);
				// c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 162) {
				c.sendMessage("You successfully emptied your inventory.");
				c.getPA().removeAllItems();
				c.dialogueAction = 0;
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 508) {
				c.getDH().sendDialogues(1030, 925);
				return;
			}
			if (c.doricOption2) {
				c.getDH().sendDialogues(310, 284);
				c.doricOption2 = false;
			}
			if (c.rfdOption) {
				c.getDH().sendDialogues(26, -1);
				c.rfdOption = false;
			}
			if (c.horrorOption) {
				c.getDH().sendDialogues(35, -1);
				c.horrorOption = false;
			}
			if (c.dtOption) {
				c.getDH().sendDialogues(44, -1);
				c.dtOption = false;
			}
			if (c.dtOption2) {
				if (c.lastDtKill == 0) {
					c.getDH().sendDialogues(65, -1);
				} else {
					c.getDH().sendDialogues(49, -1);
				}
				c.dtOption2 = false;
			}

			if (c.caOption2) {
				c.getDH().sendDialogues(106, c.npcType);
				c.caOption2 = false;
			}
			if (c.caOption2a) {
				c.getDH().sendDialogues(102, c.npcType);
				c.caOption2a = false;
			}

			if (c.dialogueAction == 1) {
				c.getDH().sendDialogues(38, -1);
			}
			break;

		case 9167:
			if (c.dialogueAction == 2245) {
				c.getPA().startTeleport(2110, 3915, 0, "modern");
				c.sendMessage("High Priest teleported you to @red@Lunar Island@bla@.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 508) {
				c.getDH().sendDialogues(1030, 925);
				return;
			}
			if (c.dialogueAction == 502) {
				c.getDH().sendDialogues(1030, 925);
				return;
			}
		
			if (c.doricOption) {
				c.getDH().sendDialogues(306, 284);
				c.doricOption = false;
			}
			break;
		case 9168:
			if (c.dialogueAction == 2245) {
				c.getPA().startTeleport(3230, 2915, 0, "modern");
				c.sendMessage("High Priest teleported you to @red@Desert Pyramid@bla@.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 508) {
				c.getDH().sendDialogues(1027, 925);
				return;
			}
			if (c.dialogueAction == 502) {
				c.getDH().sendDialogues(1027, 925);
				return;
			}
		
			if (c.doricOption) {
				c.getDH().sendDialogues(303, 284);
				c.doricOption = false;
			}

			break;
		case 9169:
			if (c.dialogueAction == 2245) {
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 508) {
				c.nextChat = 0;
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 502) {
				c.nextChat = 0;
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 251) {
				c.getDH().sendDialogues(1015, 494);
			}
			if (c.doricOption) {
				c.getDH().sendDialogues(299, 284);
			}
			break;

		case 9158:
			if (c.dialogueAction == 162) {
				c.dialogueAction = 0;
				c.getPA().closeAllWindows();
			}

			if (c.newPlayerAct == 1) {
				c.newPlayerAct = 0;
				c.sendMessage("@red@There is nothing to do in Crandor, i must teleport home and start playing Ardi.");
				c.getPA().removeAllWindows();
			}
			if (c.doricOption2) {
				c.getDH().sendDialogues(309, 284);
				c.doricOption2 = false;
			}
			/*
			 * if (c.dialogueAction == 8) { c.getPA().fixAllBarrows(); } else {
			 * c.dialogueAction = 0; c.getPA().removeAllWindows(); }
			 */
		
		/* VENG */
		case 118098:
		//	c.getPA().castVeng();
			break;
		/** Specials **/
		case 29188:
			c.specBarId = 7636; // the special attack text - sendframe126(S P E
								// C I A L A T T A C K, c.specBarId);
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29163:
			c.specBarId = 7611;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 33033:
			c.specBarId = 8505;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29038:
			if (c.playerEquipment[c.playerWeapon] == 4153) {
				c.specBarId = 7486;
			//	c.getCombat().handleGmaulPlayer();
				c.getItems().updateSpecialBar();
			} else {
				c.specBarId = 7486;
				c.usingSpecial = !c.usingSpecial;
				c.getItems().updateSpecialBar();
			}
			break;

		case 29063:
		/*	if (c.inDuelArena()) {
				c.sendMessage("You can't use dragon battleaxe special attack in Duel Arena, sorry sir.");
			} else {
				if (c.getCombat().checkSpecAmount(
						c.playerEquipment[c.playerWeapon])) {
					c.gfx0(246);
					c.forcedChat("Raarrrrrgggggghhhhhhh!");
					c.startAnimation(1056);
					c.playerLevel[2] = c.getLevelForXP(c.playerXP[2])
							+ (c.getLevelForXP(c.playerXP[2]) * 15 / 100);
					c.getPA().refreshSkill(2);
					c.getItems().updateSpecialBar();
				} else {
					c.sendMessage("You don't have the required special energy to use this attack.");
				}
			}*/
			break;

		case 48023:
			c.specBarId = 12335;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29138:
			c.specBarId = 7586;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29113:
			c.specBarId = 7561;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29238:
			c.specBarId = 7686;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		/** Dueling **/
		case 26065: // no forfeit
		/*case 26040:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(0);
			break;

		case 26066: // no movement
		case 26048:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(1);
			break;

		case 26069: // no range
		case 26042:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(2);
			break;

		case 26070: // no melee
		case 26043:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(3);
			break;

		case 26071: // no mage
		case 26041:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(4);
			break;

		case 26072: // no drinks
		case 26045:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(5);
			break;

		case 26073: // no food
		case 26046:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(6);
			break;

		case 26074: // no prayer
		case 26047:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(7);
			break;

		case 26076: // obsticals
		case 26075:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(8);
			break;

		case 2158: // fun weapons
		case 2157:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(9);
			break;

		case 30136: // sp attack
		case 30137:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(10);
			break;

		case 53245: // no helm
			c.duelSlot = 0;
			c.getTradeAndDuel().selectRule(11);
			break;

		case 53246: // no cape
			c.duelSlot = 1;
			c.getTradeAndDuel().selectRule(12);
			break;

		case 53247: // no ammy
			c.duelSlot = 2;
			c.getTradeAndDuel().selectRule(13);
			break;

		case 53249: // no weapon.
			c.duelSlot = 3;
			c.getTradeAndDuel().selectRule(14);
			break;

		case 53250: // no body
			c.duelSlot = 4;
			c.getTradeAndDuel().selectRule(15);
			break;

		case 53251: // no shield
			c.duelSlot = 5;
			c.getTradeAndDuel().selectRule(16);
			break;

		case 53252: // no legs
			c.duelSlot = 7;
			c.getTradeAndDuel().selectRule(17);
			break;

		case 53255: // no gloves
			c.duelSlot = 9;
			c.getTradeAndDuel().selectRule(18);
			break;

		case 53254: // no boots
			c.duelSlot = 10;
			c.getTradeAndDuel().selectRule(19);
			break;

		case 53253: // no rings
			c.duelSlot = 12;
			c.getTradeAndDuel().selectRule(20);
			break;

		case 53248: // no arrows
			c.duelSlot = 13;
			c.getTradeAndDuel().selectRule(21);
			break;

		case 26018:
			if (c.duelStatus == 5) {
				// c.sendMessage("You're funny sir.");
				return;
			}
			if (c.inDuelArena()) {
				Client o = (Client) PlayerHandler.players[c.duelingWith];
				if (o == null) {
					c.getTradeAndDuel().declineDuel();
					o.getTradeAndDuel().declineDuel();
					return;
				}

				if (c.duelRule[2] && c.duelRule[3] && c.duelRule[4]) {
					c.sendMessage("You won't be able to attack the player with the rules you have set.");
					break;
				}
				c.duelStatus = 2;
				if (c.duelStatus == 2) {
					c.getPA().sendFrame126("Waiting for other player...", 6684);
					o.getPA().sendFrame126("Other player has accepted.", 6684);
				}
				if (o.duelStatus == 2) {
					o.getPA().sendFrame126("Waiting for other player...", 6684);
					c.getPA().sendFrame126("Other player has accepted.", 6684);
				}

				if (c.duelStatus == 2 && o.duelStatus == 2) {
					c.canOffer = false;
					o.canOffer = false;
					c.duelStatus = 3;
					o.duelStatus = 3;
					c.getTradeAndDuel().confirmDuel();
					o.getTradeAndDuel().confirmDuel();
				}
			} else {
				Client o = (Client) PlayerHandler.players[c.duelingWith];
				c.getTradeAndDuel().declineDuel();
				o.getTradeAndDuel().declineDuel();
				c.sendMessage("You can't stake out of Duel Arena.");
			}
			break;

		case 25120:
			if (c.duelStatus == 5) {
				// c.sendMessage("You're funny sir.");
				return;
			}
			if (c.inDuelArena()) {
				if (c.duelStatus == 5) {
					break;
				}
				Client o1 = (Client) PlayerHandler.players[c.duelingWith];
				if (o1 == null) {
					c.getTradeAndDuel().declineDuel();
					return;
				}

				c.duelStatus = 4;
				if (o1.duelStatus == 4 && c.duelStatus == 4) {
					c.getTradeAndDuel().startDuel();
					o1.getTradeAndDuel().startDuel();
					o1.duelCount = 4;
					c.duelCount = 4;
					c.duelDelay = System.currentTimeMillis();
					o1.duelDelay = System.currentTimeMillis();
				} else {
					c.getPA().sendFrame126("Waiting for other player...", 6571);
					o1.getPA().sendFrame126("Other player has accepted", 6571);
				}
			} else {
				Client o = (Client) PlayerHandler.players[c.duelingWith];
				c.getTradeAndDuel().declineDuel();
				o.getTradeAndDuel().declineDuel();
				c.sendMessage("You can't stake out of Duel Arena.");
			}
			break;*/

		case 4169: // god spell charge
			c.usingMagic = true;
		//	if (!c.getCombat().checkMagicReqs(48)) {
		//		break;
		//	}

			if (System.currentTimeMillis() - c.godSpellDelay < Config.GOD_SPELL_CHARGE) {
				c.sendMessage("You still feel the charge in your body!");
				break;
			}
			c.godSpellDelay = System.currentTimeMillis();
			c.sendMessage("You feel charged with a magical power!");
			c.gfx100(c.MAGIC_SPELLS[48][3]);
			c.startAnimation(c.MAGIC_SPELLS[48][2]);
			c.usingMagic = false;
			break;

		/*
		 * case 152: c.isRunning2 = !c.isRunning2; int frame = c.isRunning2 ==
		 * true ? 1 : 0; c.getPA().sendFrame36(173,frame); break;
		 */
	
		case 9154:
			long buttonDelay = 0;
			if (System.currentTimeMillis() - buttonDelay > 2000) {
				c.logout();
				buttonDelay = System.currentTimeMillis();
			}
			break;

		case 21010:
			c.takeAsNote = true;
			break;

		case 21011:
			c.takeAsNote = false;
			break;

		// home teleports
		case 4171:
		case 117048:
		case 75010:
		case 50056:
			if (c.duelStatus == 5) {
				c.sendMessage("You can't teleport in during a duel.");
			} else {
				//c.getPA().startTeleport(3087, 3499, 0, "modern");
			}
			break;
		/*
		 * case 4171: case 50056: case 117048: if (c.homeTeleDelay <= 0) {
		 * c.homeTele = 10; } else if (c.homeTeleDelay <= 0) { c.homeTele = 10;
		 * }
		 */
		/*
		 * if (c.reset == false) { c.HomePort(); //String type =
		 * c.playerMagicBook == 0 ? "modern" : "ancient";
		 * //c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0,
		 * type); } else if (c.reset == true) { c.resetHomePort(); }
		 */

		/*
		 * case 50235: case 4140: c.getPA().startTeleport(Config.VARROCK_X,
		 * Config.VARROCK_Y, 0, "modern"); c.teleAction = 1; break;
		 * 
		 * case 4143: case 50245: c.getPA().startTeleport(Config.LUMBY_X,
		 * Config.LUMBY_Y, 0, "modern"); c.teleAction = 2; break;
		 * 
		 * case 50253: case 4146: c.getPA().startTeleport(Config.FALADOR_X,
		 * Config.FALADOR_Y, 0, "modern"); c.teleAction = 3; break;
		 * 
		 * 
		 * case 51005: case 4150: c.getPA().startTeleport(Config.CAMELOT_X,
		 * Config.CAMELOT_Y, 0, "modern"); c.teleAction = 4; break;
		 * 
		 * case 51013: case 6004: c.getPA().startTeleport(Config.ARDOUGNE_X,
		 * Config.ARDOUGNE_Y, 0, "modern"); c.teleAction = 5; break;
		 * 
		 * 
		 * case 51023: case 6005: c.getPA().startTeleport(Config.WATCHTOWER_X,
		 * Config.WATCHTOWER_Y, 0, "modern"); c.teleAction = 6; break;
		 * 
		 * 
		 * case 51031: case 29031: c.getPA().startTeleport(Config.TROLLHEIM_X,
		 * Config.TROLLHEIM_Y, 0, "modern"); c.teleAction = 7; break;
		 * 
		 * case 72038: case 51039: //c.getPA().startTeleport(Config.TROLLHEIM_X,
		 * Config.TROLLHEIM_Y, 0, "modern"); //c.teleAction = 8; break;
		 */
		/*
		 * case 50235: case 4140: case 117112: c.getDH().sendOption5("Cows",
		 * "Rock Crabs", "Experiments", "Earth warriors", "idk"); c.teleAction =
		 * 1; break;
		 * 
		 * case 4143: case 50245: case 117123:
		 * c.getDH().sendOption5("Varrock Dungeon", "Taverly Dungeon",
		 * "Brimhaven Dungeon", "idk", "idk"); c.teleAction = 2;
		 * //c.getPA().startTeleport(3094, 3478, 0, "modern");
		 * //c.sendMessage("NOTHING!"); break;
		 * 
		 * case 50253: case 4146: case 117131: c.getPA().startTeleport(3366,
		 * 3275, 0, "modern"); break;
		 * 
		 * case 51005: case 4150: case 117154:
		 * c.getDH().sendOption5("Edgeville", "Wests Dragons (10 Wild))",
		 * "Easts Dragons (18 Wild)", "Multi-Easts (44 Wild)",
		 * "Dark Castle Multi (17 Wild)"); c.teleAction = 4; break;
		 * 
		 * case 51013: case 6004: case 117162: if
		 * (c.getItems().playerHasItem(555, 2) &&
		 * c.getItems().playerHasItem(563, 2) && c.playerLevel[6] > 50) {
		 * c.getPA().startTeleport(2662, 3305, 0, "modern");
		 * c.getItems().deleteItem2(555, 2); c.getItems().deleteItem2(563, 2); }
		 * else {
		 * c.sendMessage("You don't have the required items and or level."); }
		 * break;
		 * 
		 * case 51023: case 6005: if (c.getItems().playerHasItem(557, 2) &&
		 * c.getItems().playerHasItem(563, 2) && c.playerLevel[6] > 57) {
		 * c.getPA().startTeleport(2549, 3112, 0, "modern");
		 * c.getItems().deleteItem2(557, 2); c.getItems().deleteItem2(563, 2); }
		 * else {
		 * c.sendMessage("You don't have the required items and or level."); }
		 * break;
		 * 
		 * case 29031: if (c.getItems().playerHasItem(554, 2) &&
		 * c.getItems().playerHasItem(563, 2) && c.playerLevel[6] > 60) {
		 * c.getPA().startTeleport(2888, 3674, 0, "modern");
		 * c.getItems().deleteItem2(563, 2); c.getItems().deleteItem2(554, 2); }
		 * else {
		 * c.sendMessage("You don't have the required items and or level."); }
		 * break;
		 * 
		 * case 72038: case 51039: case 117186: if
		 * (c.getItems().playerHasItem(554, 2) &&
		 * c.getItems().playerHasItem(555, 2) && c.getItems().playerHasItem(563,
		 * 2) && c.getItems().playerHasItem(1963, 1) && c.playerLevel[6] > 63) {
		 * c.getPA().startTeleport(2755, 2784, 0, "modern");
		 * c.getItems().deleteItem2(554, 2); c.getItems().deleteItem2(555, 2);
		 * c.getItems().deleteItem2(563, 2); c.getItems().deleteItem2(1963, 1);
		 * } else {
		 * c.sendMessage("You don't have the required items and or level."); }
		 * break;
		 */
		 
		/* TELEPORTS */ 
		/*case 50235:
		case 4140:
		case 117112:
			// c.getPA().startTeleport(Config.LUMBY_X, Config.LUMBY_Y, 0,
			// "modern");
			c.getDH().sendOption5("Rock Crabs", "Hill Giants",
					"Slayer Monsters", "Brimhaven Dungeon", "Traverly Dungeon");
			c.teleAction = 1;
			break;

		case 4143:
		case 50245:
		case 117123:
			// c.getDH().sendOption5("Barrows", "", "", "Duel Arena", "");
			// c.teleAction = 2;
			c.getPA().startTeleport(3365, 3265, 0, "modern");
			break;

		case 50253:
		case 117131:
		case 4146:
			c.getDH().sendOption5("King Black Dragon",
					"Chaos Elemental @red@(Wild)", "Barrelchest Boss",
					"Godwars", "Barrows");
			c.teleAction = 3;
			break;

		case 51005:
		case 117154:
		case 4150:
			c.getDH().sendOption5("Mage Bank @gre@(SAFE)",
					"Wests Dragons @red@(10 Wildy)",
					"East Dragons @red@(18 Wildy)",
					"Multi-Easts @red@(44 Wild)",
					"Dark Castle Multi @red@(17 Wild)");
			c.teleAction = 4;
			break;

		case 51013:
		case 6004:
		case 117162:
			c.getDH().sendOption5("Lumbridge", "Varrock", "Camelot", "Falador",
					"Canifis");
			c.teleAction = 20;
			break;*/

		case 9125: // Accurate
		case 6221: // range accurate
		case 48010: // flick (whip)
		case 21200: // spike (pickaxe)
		case 1080: // bash (staff)
		case 6168: // chop (axe)
		case 6236: // accurate (long bow)
		case 17102: // accurate (darts)
		case 8234: // stab (dagger)
		case 22230: // punch
			c.fightMode = 0;
		
			break;

		case 9126: // Defensive
		case 48008: // deflect (whip)
		case 21201: // block (pickaxe)
		case 1078: // focus - block (staff)
		case 6169: // block (axe)
		case 33019: // fend (hally)
		case 18078: // block (spear)
		case 8235: // block (dagger)
					// case 22231: //unarmed
		case 22228: // unarmed
			c.fightMode = 1;
	
			break;

		case 9127: // Controlled
		case 48009: // lash (whip)
		case 33018: // jab (hally)
		case 6234: // longrange (long bow)
		case 6219: // longrange
		case 18077: // lunge (spear)
		case 18080: // swipe (spear)
		case 18079: // pound (spear)
		case 17100: // longrange (darts)
			c.fightMode = 3;
	
			break;

		case 9128: // Aggressive
		case 6220: // range rapid
		case 21203: // impale (pickaxe)
		case 21202: // smash (pickaxe)
		case 1079: // pound (staff)
		case 6171: // hack (axe)
		case 6170: // smash (axe)
		case 33020: // swipe (hally)
		case 6235: // rapid (long bow)
		case 17101: // repid (darts)
		case 8237: // lunge (dagger)
		case 8236: // slash (dagger)
		case 22229: // kick
			c.fightMode = 2;
	
			break;

		/** Prayers **/
		/*case 21233: // thick skin
			c.getCombat().activatePrayer(0);
			break;
		case 21234: // burst of str
			c.getCombat().activatePrayer(1);
			break;
		case 21235: // charity of thought
			c.getCombat().activatePrayer(2);
			break;
		case 70080: // range
			c.getCombat().activatePrayer(3);
			break;
		case 70082: // mage
			c.getCombat().activatePrayer(4);
			break;
		case 21236: // rockskin
			c.getCombat().activatePrayer(5);
			break;
		case 21237: // super human
			c.getCombat().activatePrayer(6);
			break;
		case 21238: // improved reflexes
			c.getCombat().activatePrayer(7);
			break;
		case 21239: // hawk eye
			c.getCombat().activatePrayer(8);
			break;
		case 21240:
			c.getCombat().activatePrayer(9);
			break;
		case 21241: // protect Item
			c.getCombat().activatePrayer(10);
			break;
		case 70084: // 26 range
			c.getCombat().activatePrayer(11);
			break;
		case 70086: // 27 mage
			c.getCombat().activatePrayer(12);
			break;
		case 21242: // steel skin
			c.getCombat().activatePrayer(13);
			break;
		case 21243: // ultimate str
			c.getCombat().activatePrayer(14);
			break;
		case 21244: // incredible reflex
			c.getCombat().activatePrayer(15);
			break;
		case 21245: // protect from magic
			c.getCombat().activatePrayer(16);
			break;
		case 21246: // protect from range
			c.getCombat().activatePrayer(17);
			break;
		case 21247: // protect from melee
			c.getCombat().activatePrayer(18);
			break;
		case 70088: // 44 range
			c.getCombat().activatePrayer(19);
			break;
		case 70090: // 45 mystic
			c.getCombat().activatePrayer(20);
			break;
		case 2171: // retrui
			c.getCombat().activatePrayer(21);
			break;
		case 2172: // redem
			c.getCombat().activatePrayer(22);
			break;
		case 2173: // smite
			c.getCombat().activatePrayer(23);
			break;
		case 70092: // chiv
			c.getCombat().activatePrayer(24);
			break;
		case 70094: // piety
			c.getCombat().activatePrayer(25);
			break;

		case 13092:
			if (c.inWild()) {
				c.getTradeAndDuel().declineTrade();
				break;
			}
			if (System.currentTimeMillis() - c.lastButton < 400) {

				c.lastButton = System.currentTimeMillis();

				break;

			} else {

				c.lastButton = System.currentTimeMillis();

			}
			Client ot = (Client) PlayerHandler.players[c.tradeWith];
			if (ot == null) {
				c.getTradeAndDuel().declineTrade();
				c.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}
			c.getPA().sendFrame126("Waiting for other player...", 3431);
			ot.getPA().sendFrame126("Other player has accepted", 3431);
			c.goodTrade = true;
			ot.goodTrade = true;

			for (GameItem item : c.getTradeAndDuel().offeredItems) {
				if (item.id > 0) {
					if (ot.getItems().freeSlots() < c.getTradeAndDuel().offeredItems
							.size()) {
						c.sendMessage(ot.playerName
								+ " only has "
								+ ot.getItems().freeSlots()
								+ " free slots, please remove "
								+ (c.getTradeAndDuel().offeredItems.size() - ot
										.getItems().freeSlots()) + " items.");
						ot.sendMessage(c.playerName
								+ " has to remove "
								+ (c.getTradeAndDuel().offeredItems.size() - ot
										.getItems().freeSlots())
								+ " items or you could offer them "
								+ (c.getTradeAndDuel().offeredItems.size() - ot
										.getItems().freeSlots()) + " items.");
						c.goodTrade = false;
						ot.goodTrade = false;
						c.getPA().sendFrame126("Not enough inventory space...",
								3431);
						ot.getPA().sendFrame126(
								"Not enough inventory space...", 3431);
						break;
					} else {
						c.getPA().sendFrame126("Waiting for other player...",
								3431);
						ot.getPA().sendFrame126("Other player has accepted",
								3431);
						c.goodTrade = true;
						ot.goodTrade = true;
					}
				}
			}
			if (c.inTrade && !c.tradeConfirmed && ot.goodTrade && c.goodTrade) {
				c.tradeConfirmed = true;
				if (ot.tradeConfirmed) {
					c.getTradeAndDuel().confirmScreen();
					ot.getTradeAndDuel().confirmScreen();
					break;
				}

			}

			break;

		case 13218:
			if (System.currentTimeMillis() - c.lastButton < 400) {

				c.lastButton = System.currentTimeMillis();

				break;

			} else {

				c.lastButton = System.currentTimeMillis();

			}
			Client ot1 = (Client) PlayerHandler.players[c.tradeWith];
				c.tradeAccepted = true;
				if (ot1 == null) {
					c.getTradeAndDuel().declineTrade();
					c.sendMessage("Trade declined as the other player has disconnected.");
					break;
				}

				if (c.inTrade && c.tradeConfirmed && ot1.tradeConfirmed
						&& !c.tradeConfirmed2) {
					c.tradeConfirmed2 = true;
					if (ot1.tradeConfirmed2) {
						c.acceptedTrade = true;
						ot1.acceptedTrade = true;
						c.getTradeAndDuel().giveItems();
						ot1.getTradeAndDuel().giveItems();
						break;
					}
					ot1.getPA().sendFrame126("Other player has accepted.", 3535);
					c.getPA().sendFrame126("Waiting for other player...", 3535);
				}			

			break;
		/* Rules Interface Buttons */
		case 125011: // Click agree
			if (!c.ruleAgreeButton) {
				c.ruleAgreeButton = true;
				c.getPA().sendFrame36(701, 1);
			} else {
				c.ruleAgreeButton = false;
				c.getPA().sendFrame36(701, 0);
			}
			break;
		case 125003:// Accept
			if (c.ruleAgreeButton) {
				c.getPA().showInterface(3559);
				c.newPlayer = false;
			} else if (!c.ruleAgreeButton) {
				c.sendMessage("You need to click on you agree before you can continue on.");
			}
			break;
		case 125006:// Decline
			c.sendMessage("You have chosen to decline, Client will be disconnected from the server.");
			break;
		/* End Rules Interface Buttons */
		/* Player Options */
		case 74176:
			if (!c.mouseButton) {
				c.mouseButton = true;
				c.getPA().sendFrame36(500, 1);
				c.getPA().sendFrame36(170, 1);
			} else if (c.mouseButton) {
				c.mouseButton = false;
				c.getPA().sendFrame36(500, 0);
				c.getPA().sendFrame36(170, 0);
			}
			break;
		case 74184:
			if (!c.splitChat) {
				c.splitChat = true;
				c.getPA().sendFrame36(502, 1);
				c.getPA().sendFrame36(287, 1);
			} else {
				c.splitChat = false;
				c.getPA().sendFrame36(502, 0);
				c.getPA().sendFrame36(287, 0);
			}
			break;
		case 74180:
			if (!c.chatEffects) {
				c.chatEffects = true;
				c.getPA().sendFrame36(501, 1);
				c.getPA().sendFrame36(171, 0);
			} else {
				c.chatEffects = false;
				c.getPA().sendFrame36(501, 0);
				c.getPA().sendFrame36(171, 1);
			}
			break;
		case 74188:
			if (!c.acceptAid) {
				c.acceptAid = true;
				c.getPA().sendFrame36(503, 1);
				c.getPA().sendFrame36(427, 1);
			} else {
				c.acceptAid = false;
				c.getPA().sendFrame36(503, 0);
				c.getPA().sendFrame36(427, 0);
			}
			break;
		case 74192:
			if (!c.isRunning2) {
				c.isRunning2 = true;
				c.getPA().sendFrame36(504, 1);
				c.getPA().sendFrame36(173, 1);
			} else {
				c.isRunning2 = false;
				c.getPA().sendFrame36(504, 0);
				c.getPA().sendFrame36(173, 0);
			}
			break;
		case 74201:// brightness1
			c.getPA().sendFrame36(505, 1);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 1);
			break;
		case 74203:// brightness2
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 1);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 2);
			break;

		case 74204:// brightness3
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 1);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 3);
			break;

		case 74205:// brightness4
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 1);
			c.getPA().sendFrame36(166, 4);
			break;
		case 74206:// area1
			c.getPA().sendFrame36(509, 1);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74207:// area2
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 1);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74208:// area3
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 1);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74209:// area4
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 1);
			break;
		case 168:
			c.startAnimation(855);
			break;
		case 169:
			c.startAnimation(856);
			break;
		case 162:
			c.startAnimation(857);
			break;
		case 164:
			c.startAnimation(858);
			break;
		case 165:
			c.startAnimation(859);
			break;
		case 161:
			c.startAnimation(860);
			break;
		case 170:
			c.startAnimation(861);
			break;
		case 171:
			c.startAnimation(862);
			break;
		case 163:
			c.startAnimation(863);
			break;
		case 167:
			c.startAnimation(864);
			break;
		case 172:
			c.startAnimation(865);
			break;
		case 166:
			c.startAnimation(866);
			break;
		case 52050:
			c.startAnimation(2105);
			break;
		case 52051:
			c.startAnimation(2106);
			break;
		case 52052:
			c.startAnimation(2107);
			break;
		case 52053:
			c.startAnimation(2108);
			break;
		case 52054:
			c.startAnimation(2109);
			break;
		case 52055:
			c.startAnimation(2110);
			break;
		case 52056:
			c.startAnimation(2111);
			break;
		case 52057:
			c.startAnimation(2112);
			break;
		case 52058:
			c.startAnimation(2113);
			break;
		case 43092:
			c.startAnimation(0x558);
			break;
		case 2155:
			c.startAnimation(0x46B);
			break;
		case 25103:
			c.startAnimation(0x46A);
			break;
		case 25106:
			c.startAnimation(0x469);
			break;
		case 2154:
			c.startAnimation(0x468);
			break;
		case 52071:
			c.startAnimation(0x84F);
			break;
		case 52072:
			c.startAnimation(0x850);
			break;
		case 59062:
			c.startAnimation(2836);
			break;
		case 72032:
			c.startAnimation(3544);
			break;
		case 72033:
			c.startAnimation(3543);
			break;
		case 72254:
			c.startAnimation(3866);
			break;
		/* END OF EMOTES */


		}
		if (c.isAutoButton(actionButtonId))
			c.assignAutocast(actionButtonId);
	}

}
