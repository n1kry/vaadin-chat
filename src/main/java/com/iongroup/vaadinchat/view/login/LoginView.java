package com.iongroup.vaadinchat.view.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.*;

@Route("login")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    private LoginForm loginForm = new LoginForm();
    private RegistrationView registrationView = new RegistrationView();

    public LoginView() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.add("Login",new Div(loginForm));
        tabSheet.add("Register",new Div(registrationView));
        tabSheet.addThemeVariants(TabSheetVariant.LUMO_TABS_CENTERED);

        addClassName("login-view");

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        loginForm.setAction("login");

        Button registrationBtn = new Button("Sing Up");
        registrationBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
        registrationBtn.addClickListener(e -> {
            UI.getCurrent().navigate("registration");
        });

        add(new H1("Test Application"), tabSheet);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }
}
