package ardi.model.players.packets;

import ardi.Config;
import ardi.model.players.Client;
import ardi.model.players.PacketType;
import ardi.model.players.PlayerHandler;

/**
 * Attack Player
 **/
public class AttackPlayer implements PacketType {

	public static final int ATTACK_PLAYER = 73, MAGE_PLAYER = 249;

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.playerIndex = 0;
		c.npcIndex = 0;
		switch (packetType) {

		/**
		 * Attack player
		 **/
		case ATTACK_PLAYER:
			c.playerIndex = c.getInStream().readSignedWordBigEndian();
			c.followDistance = 1;
			c.followId = c.playerIndex;
			c.getPA().followPlayer();
			break;

		/**
		 * Attack player with magic
		 **/
		case MAGE_PLAYER:
		

			c.playerIndex = c.getInStream().readSignedWordA();
			int castingSpellId = c.getInStream().readSignedWordBigEndian();
			c.usingMagic = false;
			c.followDistance = 1;
			c.followId = c.playerIndex;
			c.getPA().followPlayer();
			for (int i = 0; i < c.MAGIC_SPELLS.length; i++) {
				if (castingSpellId == c.MAGIC_SPELLS[i][0]) {
					c.spellId = i;
					c.usingMagic = true;
					break;
				}
			}

				
			break;

		}

	}

}
