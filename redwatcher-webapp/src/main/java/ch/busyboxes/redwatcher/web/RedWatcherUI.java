package ch.busyboxes.redwatcher.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * The RedWatcher Vaadin UI
 * 
 * @author jeperon
 */
public class RedWatcherUI extends UI {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -7193721015839507497L;

	private static final Logger LOGGER = LoggerFactory.getLogger(RedWatcherUI.class);

	/**
	 * Default constructor
	 */
	public RedWatcherUI() {
		LOGGER.debug("Starting RedWatcherUI");
	}

	@Override
	protected void init(VaadinRequest request) {
		final HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);
		layout.setSizeFull();
		setContent(layout);

		final VerticalLayout mainView = new VerticalLayout();
		mainView.setMargin(false);
		mainView.setSpacing(true);
		mainView.setSizeFull();

		final DashboardView dashboard = new DashboardView();
		final ServerView serverView = new ServerView();

		final Navigator navigator = new Navigator(UI.getCurrent(), mainView);
		navigator.setErrorView(dashboard);

		navigator.addView(MenuLayout.DASHBOARD_FRAGMENT, dashboard);
		navigator.addView(MenuLayout.SERVERS_FRAGMENT, serverView);

		layout.addComponent(new MenuLayout(navigator));
		layout.addComponent(mainView);
	}

}
