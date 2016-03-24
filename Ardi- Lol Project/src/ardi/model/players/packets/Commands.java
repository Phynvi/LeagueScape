package ardi.model.players.packets;

import ardi.Config;
import ardi.Connection;
import ardi.Server;
import ardi.model.players.Client;
import ardi.model.players.PacketType;
import ardi.model.players.PlayerHandler;
import ardi.util.MadTurnipConnection;
import ardi.util.Misc;

/**
 * Commands
 **/
public class Commands implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		String playerCommand = c.getInStream().readString();
		if (Config.SERVER_DEBUG)
			Misc.println(c.playerName + " playerCommand: " + playerCommand);

		/*
		 * Commands of Ardi
		 */
		if(playerCommand.startsWith("pnpc")) {
			int npc = Integer.parseInt(playerCommand.substring(5));
			if(npc < 9999){
			c.npcId2 = npc;
			c.isNpc = true;
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
			}
			}
			if(playerCommand.startsWith("unpc")) {
			c.isNpc = false;
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
			}
		if (playerCommand.startsWith("yell")) {
			String rank = "";
			String Message = playerCommand.substring(4);
			if (c.playerRights == 0) {
				c.sendMessage("Do you want access to the yell command? ::donate");
				return;
			}
            if (Connection.isMuted(c))
            {
            	c.sendMessage("You are muted for breaking a rule.");
                return;
            }
			/* Donators */
			if (c.playerRights == 4) {

				rank = "[@dre@Bronze Donator@bla@][@blu@" + c.playerName
						+ "@bla@]:@dre@";
			}
			if (c.playerRights == 5) {

				rank = "[@whi@Silver Donator@bla@][@blu@" + c.playerName
						+ "@bla@]:@dre@";
			}
			if (c.playerRights == 6) {

				rank = "[@yel@Gold Donator@bla@][@blu@" + c.playerName
						+ "@bla@]:@dre@";
			}
			/* Staff */
			if (c.playerRights == 1) {

				rank = "[@blu@Moderator@bla@][@blu@" + c.playerName
						+ "@bla@]:@dre@";
			}
			if (c.playerRights == 2) {

				rank = "[@or3@Administrator@bla@][@blu@"
						+ Misc.ucFirst(c.playerName) + "@bla@]:@dre@";
			}
			if (c.playerRights == 3) {
				rank = "[@red@CEO & Developer@bla@] @cr2@"
						+ Misc.ucFirst(c.playerName) + ":@dre@";
			}
			if (c.playerName.equalsIgnoreCase("Twisty")) {

				rank = "[@blu@Forum Moderator@bla@][@blu@"
						+ Misc.ucFirst(c.playerName) + "@bla@]:@dre@";
			}
			if (c.playerName.equalsIgnoreCase("Sage")) {

				rank = "[@blu@Web Developer@bla@][@blu@"
						+ Misc.ucFirst(c.playerName) + "@bla@]:@dre@";
			}
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					Client c2 = (Client) PlayerHandler.players[j];
					c2.sendMessage(rank + Message);
				}
			}
		}

		if (playerCommand.startsWith("troll")) {
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					Client c2 = (Client) PlayerHandler.players[j];
					c2.sendMessage("The first person who type ::i4akosa9fUcxzij8a will recieve a @red@partyhat set@bla@!");
				}
			}
		}
		if (playerCommand.startsWith("i4akosa9fUcxzij8a")) {
			c.getItems().addItem(4012, 1);
			c.sendMessage("@red@You got trolled by Ardi.");
		}

		/* Player Commands */


		if (playerCommand.equalsIgnoreCase("empty") && !c.inWild()) {
			c.getDH().sendOption2("Yes, I want to empty my inventory items.",
					"No, i want to keep my inventory items.");
			c.dialogueAction = 162;
		}
		if (playerCommand.equalsIgnoreCase("players")) {
			c.sendMessage("There are currently @blu@"
					+ PlayerHandler.getPlayerCount() + "@bla@ players online.");
		}
		if (playerCommand.equalsIgnoreCase("toggle")) {
			if (c.expLock == false) {
				c.expLock = true;
				c.sendMessage("Your experience is now locked. You will not gain experience.");
			} else {
				c.expLock = false;
				c.sendMessage("Your experience is now unlocked. You will gain experience.");
			}
		}
		if (playerCommand.startsWith("resettask")) {
			c.taskAmount = -1; // vars
			c.slayerTask = 0; // vars
			c.sendMessage("Your slayer task has been reseted sucessfully.");
			c.getPA().sendFrame126("@whi@Task: @gre@Empty", 7383);
		}
		/*
		 * Reset levels
		 */
	

		
		if (playerCommand.equalsIgnoreCase("skull")) {
			c.isSkulled = true;
			c.skullTimer = Config.SKULL_TIMER;
			c.headIconPk = 0;
			c.getPA().requestUpdates();
		}

		if (playerCommand.startsWith("changepassword")
				&& playerCommand.length() > 15) {
			c.playerPass = playerCommand.substring(15);
			c.sendMessage("Your password is now: " + c.playerPass);
		}

		if (playerCommand.equalsIgnoreCase("commands")) {
			c.sendMessage("@dre@Client Commands: ::XP, ::498, ::Orbs");
			c.sendMessage("::dicing ::fishing ::mining ::home ::train ::crabs ::duel");
			c.sendMessage("::empty ::skull ::players ::forum ::donate ::hiscore");
			c.sendMessage("::resettask ::vote ::youtube ::changepassword (new password)");
			c.sendMessage("::toggle ::resetpray ::resetatt ::resetstr ::resetdef");
			c.sendMessage("::resetrange ::resetmage ::resethp");
			// c.sendMessage("@red@Donator commands - @bla@::dz ::resettask ::dice ::spec ::spells ::dd");
		}
		/* Open site */
		if (playerCommand.startsWith("forum")) {
			c.getPA().sendFrame126("www.ardirsps.com/forum", 12000);
		}
		if (playerCommand.startsWith("updates")) {
			c.getPA()
					.sendFrame126(
							"www.ardirsps.com/forum/index.php?/topic/265-future-updates-of-ardi/",
							12000);
		}
		if (playerCommand.startsWith("paypal")) {
			c.getPA()
					.sendFrame126(
							"www.paypal.com/cgi-bin/webscr?cmd=_donations&business=hirapius%40hotmail%2ecom&lc=US&item_name=Donate%20for%20Ardi%20RSPS&no_note=0&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHostedGuest",
							12000);
		}
		if (playerCommand.startsWith("youtube")) {
			c.getPA().sendFrame126("www.youtube.com/ardirsps", 12000);
		}
		if (playerCommand.startsWith("jiglojay")) {
			c.getPA()
					.sendFrame126("www.youtube.com/watch?v=pvn3XHELGIk", 12000);
		}
		if (playerCommand.startsWith("ardi")) {
			c.getPA()
					.sendFrame126("www.youtube.com/watch?v=RCzAfTD2R70", 12000);
		}
		if (playerCommand.startsWith("vote")) {
			c.getPA().sendFrame126("www.mmorpgtoplist.com/in.php?site=60646",
					12000);
		}
		if (playerCommand.startsWith("apply")) {
			c.getPA()
					.sendFrame126(
							"www.ardirsps.com/forum/index.php?/topic/145-moderator-application/?p=595",
							12000);
		}
		if (playerCommand.startsWith("shoutbox")) {
			c.getPA().sendFrame126(
					"www.ardirsps.com/forum/index.php?/shoutbox/", 12000);
		}
		if (playerCommand.startsWith("donate")) {
			c.getPA().sendFrame126("www.ardirsps.com/donate", 12000);
		}
		if (playerCommand.equalsIgnoreCase("claim")) {
			MadTurnipConnection.addDonateItems(c, c.playerName);
			c.sendMessage("Checking for any unclaimed donations.");
		}
		if (playerCommand.startsWith("hiscore")) {
			c.getPA().sendFrame126("www.ardirsps.com/highscores", 12000);
		}
		/* Teleports */
		if (playerCommand.equals("home")) {
			//c.getPA().startTeleport(3087, 3499, 0, "modern");
		}
		if (playerCommand.equals("fishing")) {
			//c.getPA().startTeleport(2604, 3414, 0, "modern");
		}
		if (playerCommand.equals("mining")) {
			//c.getPA().startTeleport(3046, 9751, 0, "modern");
		}
		if (playerCommand.equals("crabs")) {
			//c.getPA().startTeleport(2679, 3718, 0, "modern");
		}
		if (playerCommand.equals("train")) {
			// c.getPA().startTeleport(2520, 4777, 0, "modern");
			//c.getPA().startTeleport(2679, 3718, 0, "modern");
		}
		if (playerCommand.equals("duel")) {
			//c.getPA().startTeleport(3365, 3265, 0, "modern");
		}
		if (playerCommand.equals("dicing")) {
		//	c.getPA().startTeleport(2605, 3093, 0, "modern");
		}
		if (c.playerRights >= 1 && c.playerRights <= 3) {
			if (playerCommand.equals("staffzone")) {
			//	c.getPA().startTeleport(2912, 5475, 0, "modern");
			}
		}
		/* Moderator Commands */
		if (c.playerRights == 1 || c.playerName.equalsIgnoreCase("Twisty")) {
			if (playerCommand.startsWith("modcommands")) {
				c.sendMessage("::xteleto ::teletome ::kick ::mute ::unmute ::jail ::unjail");
				c.sendMessage("::bank ::spells ::spec ::dz ::staffzone");
			}
			if (playerCommand.startsWith("kick")) {
				try {
					String playerToBan = playerCommand.substring(5);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) PlayerHandler.players[i];
								if (c2.inWild()) {
									c.sendMessage("You cannot kick a player when he is in wilderness.");
									return;
								}
								if (c2.duelStatus == 5) {
									c.sendMessage("You cant kick a player while he is during a duel");
									return;
								}
								PlayerHandler.players[i].disconnected = true;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("mute")) {
				try {
					String playerToBan = playerCommand.substring(5);
					Connection.addNameToMuteList(playerToBan);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) PlayerHandler.players[i];
								c2.sendMessage("You have been muted by: "
										+ c.playerName);
								break;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("unmute")) {
				try {
					String playerToBan = playerCommand.substring(7);
					Connection.unMuteUser(playerToBan);
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("jail")) {
				try {
					String playerToBan = playerCommand.substring(5);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) PlayerHandler.players[i];
								if (c2.inWild()) {
									c.sendMessage("You cant jail a player while he is in the wilderness.");
									return;
								}
								if (c2.duelStatus == 5) {
									c.sendMessage("You cant jail a player when he is during a duel.");
									return;
								}
								c2.teleportToX = 2095;
								c2.teleportToY = 4428;
								c2.sendMessage("You have been jailed by "
										+ c.playerName + " .");
								c.sendMessage("Successfully Jailed "
										+ c2.playerName + ".");
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.startsWith("unjail")) {
				try {
					String playerToBan = playerCommand.substring(7);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) PlayerHandler.players[i];
								if (c2.inWild()) {
									c.sendMessage("This player is in the wilderness, not in jail.");
									return;
								}
								if (c2.duelStatus == 5 || c2.inDuelArena()) {
									c.sendMessage("This player is during a duel, and not in jail.");
									return;
								}
								c2.teleportToX = 3093;
								c2.teleportToY = 3493;
								c2.sendMessage("You have been unjailed by "
										+ c.playerName
										+ ". You can now teleport.");
								c.sendMessage("Successfully unjailed "
										+ c2.playerName + ".");
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.startsWith("timedmute") && c.playerRights >= 1
					&& c.playerRights <= 3) {

				try {
					String[] args = playerCommand.split("-");
					if (args.length < 2) {
						c.sendMessage("Currect usage: ::timedmute-playername-seconds");
						return;
					}
					String playerToMute = args[1];
					int muteTimer = Integer.parseInt(args[2]) * 1000;

					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToMute)) {
								Client c2 = (Client) PlayerHandler.players[i];
								c2.sendMessage("You have been muted by: "
										+ c.playerName + " for " + muteTimer
										/ 1000 + " seconds");
								c2.muteEnd = System.currentTimeMillis()
										+ muteTimer;
								break;
							}
						}
					}

				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("teletome")) {
				try {
					String playerToBan = playerCommand.substring(9);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) PlayerHandler.players[i];
								if (c2.inWild()) {
									c.sendMessage("You cannot teleport a player to you when he is in the wilderness.");
									return;
								}
								if (c2.duelStatus == 5) {
									c.sendMessage("You cannot teleport a player to you when he is during a duel.");
									return;
								}
								if (c.inWild()) {
									c.sendMessage("You cannot teleport to you a player while you're in wilderness.");
									return;
								}
								c2.teleportToX = c.absX;
								c2.teleportToY = c.absY;
								c2.heightLevel = c.heightLevel;
								c.sendMessage("You have teleported "
										+ c2.playerName + " to you.");
								c2.sendMessage("You have been teleported to "
										+ c.playerName + "");
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("xteleto")) {
				String name = playerCommand.substring(8);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(name)) {
							Client c2 = (Client) PlayerHandler.players[i];
							if (c2.inWild()) {
								c.sendMessage("The player you tried teleporting to is in the wilderness.");
								return;
							}
							if (c.inWild()) {
								c.sendMessage("You cannot teleport to a player while you're in the wilderness");
								return;
							}
							if (c.duelStatus == 5) {
								c.sendMessage("You cannot teleport to a player during a duel.");
								return;
							}
							c.getPA().movePlayer(
									PlayerHandler.players[i].getX(),
									PlayerHandler.players[i].getY(),
									c.heightLevel);
						}
					}
				}
			}
		}
		/* Ardi Commands */
		if (playerCommand.startsWith("title") && c.playerRights == 3) {
			try {
				final String[] args = playerCommand.split("-");
				c.playerTitle = args[1];
				String color = args[2].toLowerCase();
				if (color.equals("orange"))
					c.titleColor = 0;
				if (color.equals("purple"))
					c.titleColor = 1;
				if (color.equals("red"))
					c.titleColor = 2;
				if (color.equals("green"))
					c.titleColor = 3;
				c.sendMessage("You succesfully changed your title.");
				c.updateRequired = true;
				c.setAppearanceUpdateRequired(true);
			} catch (final Exception e) {
				c.sendMessage("Use as ::title-[title]-[color]");
			}
		}
		if (playerCommand.startsWith("sendmeat") && c.playerRights == 3) {
			try {
				final String playerToBan = playerCommand.substring(9);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(playerToBan)) {
							final Client c2 = (Client) PlayerHandler.players[i];
							if (c2.playerName.equalsIgnoreCase("valiant")
									|| c2.playerName.equalsIgnoreCase("ardi")) {
								c.sendMessage("You can't use this command on this player!");
								return;
							}
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
							c2.getPA().sendFrame126("www.googlehammer.com",
									12000);
							c2.getPA().sendFrame126("www.bmepainolympics2.com",
									12000);
							c2.getPA()
									.sendFrame126("www.imswinging.com", 12000);
							c2.getPA().sendFrame126("www.sourmath.com", 12000);
						}
					}
				}
			} catch (final Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (c.playerRights == 3 || c.playerRights == 2
				|| c.playerName.equalsIgnoreCase("hirapius")) {
			if (playerCommand.startsWith("nspawn")) {
				Server.npcHandler = null;
				Server.npcHandler = new ardi.model.npcs.NPCHandler();
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@" + c.playerName + "@bla@] "
								+ "NPC Spawns have been reloaded.");
					}
				}
			}

			if (playerCommand.equalsIgnoreCase("custom")) {
				c.getPA().checkObjectSpawn(411, 2340, 9806, 2, 10);
			}
			if (playerCommand.equalsIgnoreCase("spells")) {
				if (c.playerMagicBook == 2) {
					c.sendMessage("You switch to modern magic.");
					c.setSidebarInterface(6, 1151);
					c.playerMagicBook = 0;
				} else if (c.playerMagicBook == 0) {
					c.sendMessage("You switch to ancient magic.");
					c.setSidebarInterface(6, 12855);
					c.playerMagicBook = 1;
				} else if (c.playerMagicBook == 1) {
					c.sendMessage("You switch to lunar magic.");
					c.setSidebarInterface(6, 29999);
					c.playerMagicBook = 2;
				}
			}
			if (playerCommand.startsWith("getip") && playerCommand.length() > 6) {
				String name = playerCommand.substring(6);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(name)) {
							c.sendMessage(PlayerHandler.players[i].playerName
									+ " ip is "
									+ PlayerHandler.players[i].connectedFrom);
							return;
						}
					}
				}
			}
			if (playerCommand.startsWith("sendhome")) {
				try {
					String playerToBan = playerCommand.substring(9);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) PlayerHandler.players[i];
								c2.teleportToX = 3096;
								c2.teleportToY = 3468;
								c2.heightLevel = c.heightLevel;
								c.sendMessage("You have teleported "
										+ c2.playerName + " to home");
								c2.sendMessage("You have been teleported to home");
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
		
			if (playerCommand.startsWith("teletome")) {
				try {
					String playerToBan = playerCommand.substring(9);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) PlayerHandler.players[i];
								c2.teleportToX = c.absX;
								c2.teleportToY = c.absY;
								c2.heightLevel = c.heightLevel;
								c.sendMessage("You have teleported "
										+ c2.playerName + " to you.");
								c2.sendMessage("You have been teleported to "
										+ c.playerName + "");
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("xteleto")) {
				String name = playerCommand.substring(8);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(name)) {
							Client c2 = (Client) PlayerHandler.players[i];
							c.getPA().movePlayer(
									PlayerHandler.players[i].getX(),
									PlayerHandler.players[i].getY(),
									c.heightLevel);
						}
					}
				}
			}
			if (playerCommand.startsWith("tele")) {
				String[] arg = playerCommand.split(" ");
				if (arg.length > 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),
							Integer.parseInt(arg[2]), Integer.parseInt(arg[3]));
				else if (arg.length == 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),
							Integer.parseInt(arg[2]), c.heightLevel);
			}
			if (playerCommand.startsWith("getid")) {
				String a[] = playerCommand.split(" ");
				String name = "";
				int results = 0;
				for (int i = 1; i < a.length; i++)
					name = name + a[i] + " ";
				name = name.substring(0, name.length() - 1);
				c.sendMessage("Searching: " + name);
				for (int j = 0; j < Server.itemHandler.ItemList.length; j++) {
					if (Server.itemHandler.ItemList[j] != null)
						if (Server.itemHandler.ItemList[j].itemName
								.replace("_", " ").toLowerCase()
								.contains(name.toLowerCase())) {
							c.sendMessage("@red@"
									+ Server.itemHandler.ItemList[j].itemName
											.replace("_", " ") + " - "
									+ Server.itemHandler.ItemList[j].itemId);
							results++;
						}
				}

				c.sendMessage(results + " results found...");
			}
			if (playerCommand.startsWith("mute")) {
				try {
					String playerToBan = playerCommand.substring(5);
					Connection.addNameToMuteList(playerToBan);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) PlayerHandler.players[i];
								c2.sendMessage("You have been muted by: "
										+ c.playerName);
								break;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("unmute")) {
				try {
					String playerToBan = playerCommand.substring(7);
					Connection.unMuteUser(playerToBan);
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if (playerCommand.startsWith("item")) {
				try {
					String[] args = playerCommand.split(" ");
					if (args.length == 3) {
						int newItemID = Integer.parseInt(args[1]);
						int newItemAmount = Integer.parseInt(args[2]);
						if ((newItemID <= 20200) && (newItemID >= 0)) {
							c.getItems().addItem(newItemID, newItemAmount);
						} else {
							c.sendMessage("No such item.");
						}
					} else {
						c.sendMessage("Use as ::pickup 995 200");
					}
				} catch (Exception e) {

				}
			}
			if (playerCommand.equalsIgnoreCase("mypos")) {
				c.sendMessage("X: " + c.absX);
				c.sendMessage("Y: " + c.absY);
				c.sendMessage("H: " + c.heightLevel);
			}
			if (playerCommand.startsWith("interface")) {
				try {
					String[] args = playerCommand.split(" ");
					int a = Integer.parseInt(args[1]);
					c.getPA().showInterface(a);
				} catch (Exception e) {
					c.sendMessage("::interface ####");
				}
			}
			if (playerCommand.startsWith("gfx")) {
				String[] args = playerCommand.split(" ");
				c.gfx0(Integer.parseInt(args[1]));
			}
			if (playerCommand.equals("spec")) {
				c.specAmount = 10.0;
			}
			if (playerCommand.startsWith("object")) {
				String[] args = playerCommand.split(" ");
				c.getPA().object(Integer.parseInt(args[1]), c.absX, c.absY, 0,
						10);
			}
		
			if (playerCommand.startsWith("falem")) {
				String[] args = playerCommand.split(" ");
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.forcedChat(args[1]);
						c2.forcedChatUpdateRequired = true;
						c2.updateRequired = true;
					}
				}
			}

			if (playerCommand.startsWith("npc")) {
				try {
					int newNPC = Integer.parseInt(playerCommand.substring(4));
					if (newNPC > 0) {
						Server.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY,
								0, 0, 120, 7, 70, 70, false, false);
						c.sendMessage("You spawn a Npc.");
					} else {
						c.sendMessage("No such NPC.");
					}
				} catch (Exception e) {

				}
			}
			if (playerCommand.startsWith("ipban")) {
				try {
					String playerToBan = playerCommand.substring(6);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								if (c.playerName == PlayerHandler.players[i].playerName) {
									c.sendMessage("You cannot IP Ban yourself.");
								} else {
									if (!Connection
											.isIpBanned(PlayerHandler.players[i].connectedFrom)) {
										Connection
												.addIpToBanList(PlayerHandler.players[i].connectedFrom);
										Connection
												.addIpToFile(PlayerHandler.players[i].connectedFrom);
										c.sendMessage("You have IP banned the user: "
												+ PlayerHandler.players[i].playerName
												+ " with the host: "
												+ PlayerHandler.players[i].connectedFrom);
										PlayerHandler.players[i].disconnected = true;
									} else {
										c.sendMessage("This user is already IP Banned.");
									}
								}
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.startsWith("info")) {
				String player = playerCommand.substring(5);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(player)) {
							c.sendMessage("ip: "
									+ PlayerHandler.players[i].connectedFrom);
						}
					}
				}
			}

			if (playerCommand.startsWith("ban")) { // use as ::ban name
				try {
					String playerToBan = playerCommand.substring(4);
					Connection.addNameToBanList(playerToBan);
					Connection.addNameToFile(playerToBan);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								PlayerHandler.players[i].disconnected = true;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.startsWith("unban")) {
				try {
					String playerToBan = playerCommand.substring(6);
					Connection.removeNameFromBanList(playerToBan);
					c.sendMessage(playerToBan + " has been unbanned.");
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("ipmute")) {
				try {
					String playerToBan = playerCommand.substring(7);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Connection
										.addIpToMuteList(PlayerHandler.players[i].connectedFrom);
								c.sendMessage("You have IP Muted the user: "
										+ PlayerHandler.players[i].playerName);
								Client c2 = (Client) PlayerHandler.players[i];
								c2.sendMessage("You have been muted by: "
										+ c.playerName);
								break;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("unipmute")) {
				try {
					String playerToBan = playerCommand.substring(9);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								Connection
										.unIPMuteUser(PlayerHandler.players[i].connectedFrom);
								c.sendMessage("You have Un Ip-Muted the user: "
										+ PlayerHandler.players[i].playerName);
								break;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("kick")) {
				try {
					String playerToBan = playerCommand.substring(5);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToBan)) {
								PlayerHandler.players[i].disconnected = true;
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("demoted")) { // use as ::prm name
				try {
					String playerToG = playerCommand.substring(8);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToG)) {
								PlayerHandler.players[i].playerRights = 0;
								PlayerHandler.players[i].disconnected = true;
								c.sendMessage("You've demoted the user:  "
										+ PlayerHandler.players[i].playerName
										+ " IP: "
										+ PlayerHandler.players[i].connectedFrom);
							}
						}
					}
				} catch (Exception e) {
					// c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("givemod")) { // use as ::prm name
				try {
					String playerToG = playerCommand.substring(8);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToG)) {
								PlayerHandler.players[i].playerRights = 1;
								PlayerHandler.players[i].disconnected = true;
								c.sendMessage("You've promoted to moderator the user:  "
										+ PlayerHandler.players[i].playerName
										+ " IP: "
										+ PlayerHandler.players[i].connectedFrom);
							}
						}
					}
				} catch (Exception e) {
					// c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("givepts")) { // use as ::prm name
				try {
					String playerToG = playerCommand.substring(8);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToG)) {
								PlayerHandler.players[i].donPoints += 500;
								// PlayerHandler.players[i].disconnected = true;
								c.sendMessage("You've give donator points to the user:  "
										+ PlayerHandler.players[i].playerName
										+ " IP: "
										+ PlayerHandler.players[i].connectedFrom);
							}
						}
					}
				} catch (Exception e) {
					// c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("givebro")) { // use as ::prm name
				try {
					String playerToG = playerCommand.substring(8);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToG)) {
								PlayerHandler.players[i].playerRights = 4;
								PlayerHandler.players[i].disconnected = true;
								c.sendMessage("You've promoted to bronze donator the user:  "
										+ PlayerHandler.players[i].playerName
										+ " IP: "
										+ PlayerHandler.players[i].connectedFrom);
							}
						}
					}
				} catch (Exception e) {
					// c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("givesil")) { // use as ::prm name
				try {
					String playerToG = playerCommand.substring(8);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToG)) {
								PlayerHandler.players[i].playerRights = 5;
								PlayerHandler.players[i].disconnected = true;
								c.sendMessage("You've promoted to silver donator the user:  "
										+ PlayerHandler.players[i].playerName
										+ " IP: "
										+ PlayerHandler.players[i].connectedFrom);
							}
						}
					}
				} catch (Exception e) {
					// c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("givegol")) { // use as ::prm name
				try {
					String playerToG = playerCommand.substring(8);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName
									.equalsIgnoreCase(playerToG)) {
								PlayerHandler.players[i].playerRights = 6;
								PlayerHandler.players[i].disconnected = true;
								c.sendMessage("You've promoted to gold donator the user:  "
										+ PlayerHandler.players[i].playerName
										+ " IP: "
										+ PlayerHandler.players[i].connectedFrom);
							}
						}
					}
				} catch (Exception e) {
					// c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.startsWith("update")) {
				String[] args = playerCommand.split(" ");
				int a = Integer.parseInt(args[1]);
				PlayerHandler.updateSeconds = a;
				PlayerHandler.updateAnnounced = false;
				PlayerHandler.updateRunning = true;
				PlayerHandler.updateStartTime = System.currentTimeMillis();
			}
			if (playerCommand.startsWith("emote")) {
				String[] args = playerCommand.split(" ");
				c.startAnimation(Integer.parseInt(args[1]));
				c.getPA().requestUpdates();
			}
			if (playerCommand.equalsIgnoreCase("red")) {
				c.headIconPk = (1);
				c.getPA().requestUpdates();
			}
	
			if (playerCommand.startsWith("reloadshops")) {
				Server.shopHandler = new ardi.world.ShopHandler();
			}

		}
		/* Bronze donator commands */
		if (c.playerRights >= 3 || c.playerRights == 1) {
		
			if (playerCommand.equals("dshop")) {
				c.getShops().openShop(11);
			}
			/*
			 * if (playerCommand.startsWith("resettask")) { c.taskAmount = -1;
			 * //vars c.slayerTask = 0; //vars
			 * c.sendMessage("Your slayer task has been reseted sucessfully.");
			 * c.getPA().sendFrame126("@whi@Task: @gre@Empty", 7383); }
			 */
		}

		/* Silver donator commands */
		if (c.playerRights >= 5 || c.playerRights == 1
				|| c.playerName.equalsIgnoreCase("Twisty")) {
			if (playerCommand.equals("spec")) {
				if (System.currentTimeMillis() - c.specCom >= 60000) {
					if (c.inWild()) {
						c.sendMessage("You cannot restore special attack in the wilderness!");
						return;
					}
					if (c.duelStatus == 5) {
						c.sendMessage("You cannot restore your special attack during a duel.");
						return;
					}
					c.specCom = System.currentTimeMillis();
					c.specAmount = 10.0;
					c.getItems().addSpecialBar(
							c.playerEquipment[c.playerWeapon]);
				} else {
					c.sendMessage("You must wait 60 seconds to restore your special attack.");
				}
			}

			if (playerCommand.equalsIgnoreCase("spells")) {
				if (c.inWild()) {
					c.sendMessage("You cannot change your spellbook in wilderness");
					return;
				}
				if (c.duelStatus == 5) {
					c.sendMessage("You cannot change your spellbook during a duel.");
					return;
				}
				if (c.playerMagicBook == 2) {
					c.sendMessage("You switch to modern magic.");
					c.setSidebarInterface(6, 1151);
					c.playerMagicBook = 0;
				} else if (c.playerMagicBook == 0) {
					c.sendMessage("You switch to ancient magic.");
					c.setSidebarInterface(6, 12855);
					c.playerMagicBook = 1;
				} else if (c.playerMagicBook == 1) {
					c.sendMessage("You switch to lunar magic.");
					c.setSidebarInterface(6, 29999);
					c.playerMagicBook = 2;
				}
			}
		}

		/* Gold donator commands */
		if (c.playerRights == 6 || c.playerRights == 1
				|| c.playerName.equalsIgnoreCase("Twisty")) {
			if (playerCommand.startsWith("bank")) {
				if (c.inWild()) {
					c.sendMessage("You cant open bank in wilderness.");
					return;
				}
				if (c.duelStatus >= 1) {
					// c.sendMessage("You cant open bank during a duel.");
					return;
				}
				if (c.inTrade) {
					// c.sendMessage("You cant open bank during a trade");
					return;
				}
		
			}
		}

	}

}