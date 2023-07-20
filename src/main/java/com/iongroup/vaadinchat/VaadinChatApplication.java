package com.iongroup.vaadinchat;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme(value = "myapp")
@Push
public class VaadinChatApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(VaadinChatApplication.class, args);
    }

}
