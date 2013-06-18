package ch.busyboxes.redwatcher.web;

import com.vaadin.Application;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class RedWatcherApplication extends Application {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -7193721015839507497L;

	@Override
	public void init() {
		Window mainWindow = new Window("RedWatcher Application");
		Label label = new Label("Hello Vaadin user");
		mainWindow.addComponent(label);
		setMainWindow(mainWindow);
	}

}
