package ardi.model.players.packets;

import ardi.model.players.Client;
import ardi.model.players.PacketType;

/**
 * Slient Packet
 **/
public class SilentPacket implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {

	}
}
