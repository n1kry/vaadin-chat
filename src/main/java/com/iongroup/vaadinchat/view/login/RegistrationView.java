package com.iongroup.vaadinchat.view.login;

import com.iongroup.vaadinchat.view.login.binder.RegistrationFormBinder;
import com.iongroup.vaadinchat.view.login.form.RegistrationForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class RegistrationView extends VerticalLayout {
    public RegistrationView() {

        RegistrationForm registrationForm = new RegistrationForm();
        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);

        add(registrationForm);

        RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder(registrationForm);
        registrationFormBinder.addBindingAndValidation();
    }
}
