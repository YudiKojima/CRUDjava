package com.example.application.views.helloworld;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("SISTEMA DE GERENCIAMENTO DE UM ENSINO SUPERIOR")
@Route(value = "Bem-Vindo", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HelloworldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public HelloworldView() {
        name = new TextField("Nome: ");
        sayHello = new Button("Enviar");
        sayHello.addClickListener(e -> {
            Notification.show(name.getValue() + " pode acessar outras abas!");
        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        add(name, sayHello);

    }

}
