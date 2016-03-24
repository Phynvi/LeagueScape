package ardi.model.players.packets;

import ardi.model.items.UseItem;
import ardi.model.npcs.NPCHandler;
import ardi.model.players.Client;
import ardi.model.players.PacketType;

public class ItemOnNpc implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readSignedWordA();
		int i = c.getInStream().readSignedWordA();
		int slot = c.getInStream().readSignedWordBigEndian();
		int npcId = NPCHandler.npcs[i].npcType;
		if (!c.getItems().playerHasItem(itemId, 1)) {
			return;
		}
		UseItem.ItemonNpc(c, itemId, npcId, slot);
	}
}
