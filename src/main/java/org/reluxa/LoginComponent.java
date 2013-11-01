package org.reluxa;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class LoginComponent extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private HorizontalLayout mainLayout;
	@AutoGenerated
	private Button button_2;
	@AutoGenerated
	private PasswordField passwordField_1;
	@AutoGenerated
	private TextField username;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public LoginComponent() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}

	@AutoGenerated
	private HorizontalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new HorizontalLayout();
		mainLayout.setCaption("Login");
		mainLayout.setImmediate(false);
		mainLayout.setWidth("-1px");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		
		// top-level component properties
		setWidth("-1px");
		setHeight("-1px");
		
		// username
		username = new TextField();
		username.setCaption("username");
		username.setImmediate(false);
		username.setWidth("-1px");
		username.setHeight("-1px");
		mainLayout.addComponent(username);
		
		// passwordField_1
		passwordField_1 = new PasswordField();
		passwordField_1.setCaption("password");
		passwordField_1.setImmediate(false);
		passwordField_1.setWidth("-1px");
		passwordField_1.setHeight("-1px");
		mainLayout.addComponent(passwordField_1);
		
		// button_2
		button_2 = new Button();
		button_2.setCaption("Login");
		button_2.setImmediate(false);
		button_2.setWidth("-1px");
		button_2.setHeight("-1px");
		mainLayout.addComponent(button_2);
		mainLayout.setComponentAlignment(button_2, new Alignment(24));
		
		return mainLayout;
	}

}