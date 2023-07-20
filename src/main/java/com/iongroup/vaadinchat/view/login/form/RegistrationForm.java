package com.iongroup.vaadinchat.view.login.form;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.html.Span;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public class RegistrationForm extends FormLayout {
    private H1 title;

    private TextField username;

    private TextField firstName;

    private TextField lastName;

    private EmailField email;

    private PasswordField password;

    private PasswordField passwordConfirm;

    private Span errorMessageField;

    private Button submitButton;

    public RegistrationForm() {
        title = new H1("Signup form");
        username = new TextField("Username");
        firstName = new TextField("First name");
        lastName = new TextField("Last name");
        email = new EmailField("Email");

        password = new PasswordField("Password");
        passwordConfirm = new PasswordField("Confirm password");

        setRequiredIndicatorVisible(username, firstName, lastName, email, password,
                passwordConfirm);

        errorMessageField = new Span();

        submitButton = new Button("Join the community");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.getStyle().set("margin-top","20px");

        add(title, username, firstName, lastName, email, password, passwordConfirm, errorMessageField, submitButton);

        setMaxWidth("500px");

        getStyle().set("align-self", "center");

        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490PX", 2, ResponsiveStep.LabelsPosition.TOP)
        );

        setColspan(username, 2);
        setColspan(title, 2);
        setColspan(email, 2);
        setColspan(errorMessageField, 2);
        setColspan(submitButton, 2);
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }
}
