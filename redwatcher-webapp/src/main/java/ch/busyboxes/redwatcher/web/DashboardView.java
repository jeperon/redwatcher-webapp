package ch.busyboxes.redwatcher.web;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Dashboard
 * 
 * @author jeperon
 */
public class DashboardView extends VerticalLayout implements View {

	public DashboardView() {
		super();
		setMargin(true);
		setSizeFull();
		initComponents();
	}

	private void initComponents() {
		Label dashboardLabel = new Label("This is the dashboard.");

		addComponent(dashboardLabel);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
