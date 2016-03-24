package ardi.model.players.packets;

import ardi.model.players.Client;
import ardi.model.players.PacketType;
import ardi.model.players.PlayerHandler;

/**
 * Challenge Player
 **/
public class ChallengePlayer implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		switch (packetType) {
		case 128:
			int answerPlayer = c.getInStream().readUnsignedWord();
			if (PlayerHandler.players[answerPlayer] == null) {
				return;
			}

			if (c.arenas() || c.duelStatus == 5) {
				c.sendMessage("You can't challenge inside the arena!");
				return;
			}

		//	c.getTradeAndDuel().requestDuel(answerPlayer);
			break;
		}
	}
}
