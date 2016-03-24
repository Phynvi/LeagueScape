package ardi.model.players.packets;

import ardi.model.players.Client;
import ardi.model.players.PacketType;


public class ItemClick2OnGroundItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		final int itemX = c.getInStream().readSignedWordBigEndian();
		final int itemY = c.getInStream().readSignedWordBigEndianA();
		final int itemId = c.getInStream().readUnsignedWordA();
		System.out.println("ItemClick2OnGroundItem - " + c.playerName + " - "
				+ itemId + " - " + itemX + " - " + itemY);

	}
}