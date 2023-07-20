package com.iongroup.vaadinchat.view;

import com.iongroup.vaadinchat.view.admin.AdminAppLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Main")
@Route(value = "admin/j", layout = AdminAppLayout.class)
//need to change USER to ADMIN
@RolesAllowed("USER")
public class MainView extends HorizontalLayout {

    private final TextField name;
    private final Button sayHello;

    public MainView() {
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        add(name, sayHello);
    }

}

