package org.reluxa.login.service;

import org.reluxa.AbstractService;
import org.reluxa.player.Player;

import com.db4o.ObjectSet;

public class LoginService extends AbstractService {

	public boolean isUserExists(Player player) {
		ObjectSet<Player> users = db.queryByExample(player);
		return users.size() > 0;
	}
	
	public Player getUser(String email) {
		Player player = new Player();
		player.setEmail(email);
		ObjectSet<Player> users = db.queryByExample(player);
		return users.get(0);
	}


}