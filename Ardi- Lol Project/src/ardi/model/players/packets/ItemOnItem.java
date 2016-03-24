package ardi.model.players.packets;

/**
 * @author Ryan / Lmctruck30
 */

import ardi.model.items.UseItem;
import ardi.model.players.Client;
import ardi.model.players.PacketType;

public class ItemOnItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int usedWithSlot = c.getInStream().readUnsignedWord();
		int itemUsedSlot = c.getInStream().readUnsignedWordA();
		int useWith = c.playerItems[usedWithSlot] - 1;
		int itemUsed = c.playerItems[itemUsedSlot] - 1;
		if (!c.getItems().playerHasItem(useWith, 1)
				|| !c.getItems().playerHasItem(itemUsed, 1)) {
			return;
		}
		// UseItem.ItemonItem(c, itemUsed, useWith);
		UseItem.ItemonItem(c, itemUsed, useWith, itemUsedSlot, usedWithSlot);
	}

}
