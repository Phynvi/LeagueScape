package ardi.model.players.packets;

import ardi.model.players.Client;
import ardi.model.players.PacketType;

/**
 * Remove Item
 **/
public class RemoveItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int interfaceId = c.getInStream().readUnsignedWordA();
		int removeSlot = c.getInStream().readUnsignedWordA();
		int removeId = c.getInStream().readUnsignedWordA();
		int shop = 0;
		int value = 0;
		String name = "null";
		/*
		 * if(!c.getItems().playerHasItem(removeId, 1)) { return; }
		 */
		switch (interfaceId) {
		case 7423:
		/*	if (c.inTrade) {
				c.getTradeAndDuel().declineTrade(true);
				return;
			}*/
			c.getItems().bankItem(removeId, removeSlot, 1);
			c.getItems().resetItems(7423);
			break;

		case 1688:
			c.getItems().removeItem(removeId, removeSlot);
			break;

		case 5064:
			c.getItems().bankItem(removeId, removeSlot, 1);
			break;

		case 5382:
			c.getItems().fromBank(removeId, removeSlot, 1);
			break;

		case 3900:
			c.getShops().buyFromShopPrice(removeId, removeSlot);
			break;

		case 3823:
			c.getShops().sellToShopPrice(removeId, removeSlot);
			break;

		case 3322:
			if (!c.inTrade) {
				return;
			}
			if (!c.canOffer) {
				return;
			}
			if (c.duelStatus <= 0) {
				//c.getTradeAndDuel().tradeItem(removeId, removeSlot, 1);
			} else {
				//c.getTradeAndDuel().stakeItem(removeId, removeSlot, 1);
			}
			break;

		case 3415:
			if (!c.canOffer) {
				return;
			}
			if (c.duelStatus <= 0) {
				//.getTradeAndDuel().fromTrade(removeId, removeSlot, 1);
			}
			break;

		case 6669:
			//c.getTradeAndDuel().fromDuel(removeId, removeSlot, 1);
			break;


		}
	}

}
