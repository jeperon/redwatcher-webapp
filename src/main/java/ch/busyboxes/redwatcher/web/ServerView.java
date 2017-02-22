package ch.busyboxes.redwatcher.web;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView
public class ServerView extends VerticalLayout implements View {

    private static final long serialVersionUID = 1L;

    public ServerView() {
        super();
        setMargin(true);
        setSizeFull();
        initComponents();
    }

    private void initComponents() {
        Label dashboardLabel = new Label("This is the server view.");

        addComponent(dashboardLabel);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

}
