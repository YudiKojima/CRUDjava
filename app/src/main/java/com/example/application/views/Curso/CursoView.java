package com.example.application.views.Curso;


import com.example.application.entidades.Curso;
import com.example.application.repositories.CursoRepository;
import com.example.application.repositories.postgres.CursoRepositoryImpl;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("CADASTRO DO CURSO")
@Route(value = "curso", layout = MainLayout.class)
public class CursoView extends VerticalLayout{
    private TextField nomefield;
    private IntegerField idcursofield;
    private Button salvarButton;
    private Button limparButton;
    private Grid<Curso> grid 
                        = new Grid<>(Curso.class, false);
    private CursoRepository repository 
                        = new CursoRepositoryImpl(); 
    private Boolean teste = true;
    private int id;
    


    public CursoView() {
        nomefield = new TextField("Nome do Curso*");
        nomefield.setPrefixComponent(VaadinIcon.ACADEMY_CAP.create());
        nomefield.setClearButtonVisible(true);

        idcursofield = new IntegerField("ID do Curso*");
        idcursofield.setPrefixComponent(VaadinIcon.KEY.create());
        idcursofield.setClearButtonVisible(true);
        idcursofield.setHelperText("Apenas números");

        salvarButton = new Button("Salvar");
        limparButton = new Button("Cancelar");

        limparButton.addClickListener(ev ->{
            nomefield.setValue("");
            idcursofield.setValue(null);
        });

        salvarButton.addClickListener(ev -> {
            Integer ch = idcursofield.getValue();

            String nm = nomefield.getValue();
            if(nm == "" ){
                Notification.show("Insira o nome do curso!");
                nomefield.focus();
                return;
            }

            if(ch == null || ch <= 0 ){
                Notification.show("ID curso inválida!");
                idcursofield.focus();
                return;
            }
            
            Curso novo = new Curso();
            novo.setNome(nomefield.getValue());
            novo.setIdcurso(idcursofield.getValue());

            if(teste == true ){
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

        grid.addColumn(Curso::getNome)
            .setHeader("Nome Curso");
        grid.addColumn(Curso::getIdcurso)
            .setHeader("ID Curso");

        grid.addComponentColumn(c -> {
            Button del = new Button("DEL");
            del.addClickListener(ev ->{
                        repository.remover(c.getId());
                        grid.setItems(repository.listar());
            });
            return del;
            
        });
        grid.addComponentColumn(c ->{
            Button editaButton = new Button("EDITAR");
            editaButton.addClickListener(ev ->{
                nomefield.focus();
                nomefield.setValue(c.getNome());
                idcursofield.setValue(c.getIdcurso());
                id = c.getId();
                teste = false;
            });
            return editaButton;
        });
        grid.setItems(repository.listar());
        add(nomefield, idcursofield, hl, grid);
    }
}
