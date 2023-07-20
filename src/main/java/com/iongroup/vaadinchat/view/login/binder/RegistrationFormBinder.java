package com.iongroup.vaadinchat.view.login.binder;

import com.iongroup.vaadinchat.component.ApplicationContextHolder;
import com.iongroup.vaadinchat.entity.UserEntity;
import com.iongroup.vaadinchat.service.UserEntityService;
import com.iongroup.vaadinchat.view.login.form.RegistrationForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;

public class RegistrationFormBinder {

    private final UserEntityService userEntityService = ApplicationContextHolder.getBean(UserEntityService.class);
    private RegistrationForm registrationForm;

    private boolean enablePasswordValidation;

    public RegistrationFormBinder(RegistrationForm registrationForm) {
        this.registrationForm = registrationForm;
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<UserEntity> binder = new BeanValidationBinder<>(UserEntity.class);
        binder.bindInstanceFields(registrationForm);

        binder.forField(registrationForm.getPassword())
                        .withValidator(this::passwordValidator).bind("password");

        binder.forField(registrationForm.getUsername())
                        .withValidator(this::usernameValidator).bind("username");

        binder.forField(registrationForm.getEmail())
                .withValidator(this::emailValidator).bind("email");

        registrationForm.getUsername().addValueChangeListener(e -> {
            binder.validate();
        });

        registrationForm.getPasswordConfirm().addValueChangeListener(e -> {
            enablePasswordValidation = true;

            binder.validate();
        });

        binder.setStatusLabel(registrationForm.getErrorMessageField());

        registrationForm.getSubmitButton().addClickListener(e -> {
            try {
                UserEntity user = new UserEntity();

                binder.writeBean(user);

                userEntityService.addUser(user);

                showSuccess(user);
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void showSuccess(UserEntity user) {
        Notification notification = Notification
                .show("Data saved, welcome " + user.getFirstName() + " " + user.getLastName());

        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private ValidationResult emailValidator(String email, ValueContext valueContext) {
        if (userEntityService.existsByEmail(email)) {
            return ValidationResult.error("This email already registered");
        }

        return ValidationResult.ok();
    }

    private ValidationResult usernameValidator(String username, ValueContext valueContext) {
        if (username == null || username.length() < 5) {
            return ValidationResult.error("Username should be at least 5 characters long");
        }

        if (userEntityService.existsByUserName(username)) {
            return ValidationResult.error("This username already exists");
        }

        return ValidationResult.ok();
    }

    private ValidationResult passwordValidator(String password, ValueContext valueContext) {
        if (password == null || password.length() < 8) {
            return ValidationResult.error("Password should be at least 8 characters long");
        }

        if (!enablePasswordValidation) {
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String confirmedPassword = registrationForm.getPasswordConfirm().getValue();

        if (password.equals(confirmedPassword)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Passwords dont match");
    }
}
