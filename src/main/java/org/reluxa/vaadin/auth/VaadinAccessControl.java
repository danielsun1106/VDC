package org.reluxa.vaadin.auth;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.reluxa.login.service.LoginService;
import org.reluxa.player.Player;

import com.vaadin.cdi.access.AccessControl;
import com.vaadin.ui.UI;

@Alternative
public class VaadinAccessControl extends AccessControl {

	private static final String USER_EMAIL = "user";

	@Inject @Getter
	LoginService loginService;

	@Override
	public boolean isUserSignedIn() {
		return UI.getCurrent().getSession().getAttribute(USER_EMAIL) != null;
	}

	@Override
	public boolean isUserInRole(String role) {
		if (getPrincipalName() != null) {
			Player player = getCurrentPlayer();
			if (player != null && Player.ROLE_USER.equals(role)) {
				return true;
			} else if (player != null && Player.ROLE_ADMIN.equals(role) && player.isAdmin()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getPrincipalName() {
		return (String) UI.getCurrent().getSession().getAttribute(USER_EMAIL);
	}
	
	public Player getCurrentPlayer() {
		return loginService.getUser(getPrincipalName());
	}

	
	public boolean login(String username, String password) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			throw new IllegalArgumentException("Username and password must not be empty!");
		}
		Player player = loginService.getUser(username);
		if (BCrypt.checkpw(password, player.getPassword())) {
			UI.getCurrent().getSession().setAttribute(USER_EMAIL, player.getEmail());
			return true;
		}
		return false;
	}
	
	public boolean login(Player player) {
		if (loginService.isUserExists(player)) {
			UI.getCurrent().getSession().setAttribute(USER_EMAIL, player.getEmail());
			return true;
		}
		return false;
	}

	public void logout() {
		UI.getCurrent().detach();
		UI.getCurrent().getSession().setAttribute(USER_EMAIL, null);
	}

}
