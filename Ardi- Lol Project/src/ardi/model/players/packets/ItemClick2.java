package ardi.model.players.packets;

import ardi.model.players.Client;
import ardi.model.players.PacketType;
import ardi.util.Misc;

/**
 * Item Click 2 Or Alternative Item Option 1
 * 
 * @author Ryan / Lmctruck30
 * 
 *         Proper Streams
 */

public class ItemClick2 implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readSignedWordA();

		if (!c.getItems().playerHasItem(itemId, 1))
			return;
		switch (itemId) {
		case 15098:
			c.getItems().deleteItem(itemId, 1);
			c.getItems().addItem(15088, 1);
			break;
		case 15088:
			c.getItems().deleteItem(itemId, 1);
			c.getItems().addItem(15098, 1);
			break;
		case 11694:
			c.sendMessage("Dismantle has been disabled.");
			break;
		case 11696:
			c.sendMessage("Dismantle has been disabled.");
			break;
		case 11698:
			c.sendMessage("Dismantle has been disabled.");
			break;
		case 11700:
			c.sendMessage("Dismantle has been disabled.");
			break;
		default:
			if (c.playerRights == 3)
				Misc.println(c.playerName + " - Item3rdOption: " + itemId);
			break;
		}

	}

}
