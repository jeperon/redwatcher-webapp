package ch.busyboxes.redwatcher.web;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Dashboard
 */
@SpringView
public class DashboardView extends VerticalLayout implements View {

    private static final long serialVersionUID = 1L;

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
