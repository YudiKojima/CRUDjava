package com.example.application.views.FeedBack;

import com.example.application.entidades.FeedBack;
import com.example.application.repositories.FeedBackRepository;
import com.example.application.repositories.postgres.FeedBackRepositoryImpl;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("FEEDBACK")
@Route(value = "feedback", layout = MainLayout.class)
public class FeedBackView extends VerticalLayout {
    private Button salvarButton;
    private Button limparButton;
    private Grid<FeedBack> grid = new Grid<>(FeedBack.class, false);
    private FeedBackRepository repository = new FeedBackRepositoryImpl();
    private Boolean teste = true;
    private int id;

    public FeedBackView() {
        IntegerField notafield = new IntegerField();
        notafield.setLabel("Nota para o Site");
        notafield.setHelperText("MIN 0 / MAX 10");
        notafield.setMin(0);
        notafield.setMax(10);
        notafield.setValue(2);
        notafield.setHasControls(true);

        TextArea comentariofield = new TextArea("Fala para nós o que podemos melhorar no sistema");
        comentariofield.setWidthFull();
        comentariofield.setMaxLength(460);
        comentariofield.setValueChangeMode(ValueChangeMode.EAGER);
        comentariofield.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + 460);
        });
        comentariofield.setHelperText("Até 460 caracteres.");
        comentariofield.setClearButtonVisible(true);

        salvarButton = new Button("Salvar");
        limparButton = new Button("Cancelar");

        limparButton.addClickListener(ev -> {
            notafield.setValue(null);
            comentariofield.setValue("");
        });

        salvarButton.addClickListener(ev -> {
            Integer ch = notafield.getValue();

            String nm = comentariofield.getValue();
            if (nm == "") {
                Notification.show("Insira um comentário para o sistema!");
                comentariofield.focus();
                return;
            }

            if (ch == null || ch <= 0) {
                Notification.show("Insira uma nota para o sistema!");
                notafield.focus();
                return;
            }

            FeedBack novo = new FeedBack();
            novo.setNota(notafield.getValue());
            novo.setComentario(comentariofield.getValue());

            if (teste == true) {
                repository.inserir(novo);
                Notification.show("Salvo!");
            } else {
                novo.setId(id);
                repository.editar(novo);
                Notification.show("Alteração salva!");
                teste = true;
            }

            grid.setItems(repository.listar());
            limparButton.click();
        });

        salvarButton.addClickShortcut(Key.ENTER);
        HorizontalLayout hl = new HorizontalLayout();
        hl.add(salvarButton, limparButton);

        grid.addColumn(FeedBack::getNota)
                .setHeader("Nota para o sistema");
        grid.addColumn(FeedBack::getComentario)
                .setHeader("Comentario para o sistema");

        grid.addComponentColumn(c -> {
            Button del = new Button("DEL");
            del.addClickListener(ev -> {
                repository.remover(c.getId());
                grid.setItems(repository.listar());
            });
            return del;

        });
        grid.addComponentColumn(c -> {
            Button editaButton = new Button("EDITAR");
            editaButton.addClickListener(ev -> {
                notafield.focus();
                notafield.setValue(c.getNota());
                comentariofield.setValue(c.getComentario());
                id = c.getId();
                teste = false;
            });
            return editaButton;
        });
        grid.setItems(repository.listar());
        add(notafield, comentariofield, hl, grid);
    }
}
