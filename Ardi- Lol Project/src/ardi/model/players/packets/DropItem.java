package ardi.model.players.packets;

import ardi.Config;
import ardi.model.players.Client;
import ardi.model.players.PacketType;

/**
 * Drop Item by Ardi
 **/
public class DropItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {

		int itemId = c.getInStream().readUnsignedWordA();
		c.getInStream().readUnsignedByte();
		c.getInStream().readUnsignedByte();
		int slot = c.getInStream().readUnsignedWordA();
		c.alchDelay = System.currentTimeMillis();
		if (c.arenas()) {
			c.sendMessage("You can't drop items inside the arena!");
			return;
		}
		if (c.underAttackBy > 0) {
			c.sendMessage("You can't drop items during a combat.");
			return;
		}
		if (c.inTrade) {
			c.sendMessage("You can't drop items while trading!");
			return;
		}
		if (c.playerRights == 2) {
			c.sendMessage("Administrator cannot drop items.");
			return;
		}

		boolean droppable = true;
		for (int i : Config.UNDROPPABLE_ITEMS) {
			if (i == itemId) {
				droppable = false;
				break;
			}
		}

		if (c.playerItemsN[slot] != 0 && itemId != -1
				&& c.playerItems[slot] == itemId + 1) {
			if (droppable) {
				if (c.underAttackBy > 0) {
					if (c.getShops().getItemShopValue(itemId) > 1000) {
						c.sendMessage("You may not drop items worth more than 1000 while in combat.");
						return;
					}
				}
				c.sendMessage("Your item dissapears when it touches the ground."); // drop
																					// dissapearing
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				ardi.model.players.PlayerSave.saveGame(c);
			} else {
				c.sendMessage("You can't drop this item.");
			}
		}
	}
}
