package com.example.application.views.Disciplina;


import com.example.application.entidades.Disciplina;
import com.example.application.repositories.DisciplinaRepository;
import com.example.application.repositories.postgres.DisciplinaRepositoryImpl;
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

@PageTitle("CADASTRO DA DISCIPLINA")
@Route(value = "disciplina", layout = MainLayout.class)
public class DisciplinaView extends VerticalLayout{
    private TextField nomefield;
    private IntegerField cargahorariafield;
    private IntegerField idcursofield;
    private Button salvarButton;
    private Button limparButton;
    private Grid<Disciplina> grid 
                        = new Grid<>(Disciplina.class, false);
    private DisciplinaRepository repository 
                        = new DisciplinaRepositoryImpl(); 
    private Boolean teste = true;
    private int id;
    


    public DisciplinaView() {
        nomefield = new TextField("Disciplina*");
        nomefield.setPrefixComponent(VaadinIcon.LIST_SELECT.create());
        nomefield.setClearButtonVisible(true);

        cargahorariafield = new IntegerField("Carga Horaria*");
        cargahorariafield.setPrefixComponent(VaadinIcon.CALENDAR_CLOCK.create());
        cargahorariafield.setClearButtonVisible(true);
        cargahorariafield.setHelperText("Apenas números. (Em horas)");

        idcursofield = new IntegerField("ID do Curso*");
        idcursofield.setPrefixComponent(VaadinIcon.KEY.create());
        idcursofield.setClearButtonVisible(true);
        idcursofield.setHelperText("Apenas números");
        
        salvarButton = new Button("Salvar");
        limparButton = new Button("Cancelar");

        limparButton.addClickListener(ev ->{
            nomefield.setValue("");
            cargahorariafield.setValue(null);
            idcursofield.setValue(null);
        });

        salvarButton.addClickListener(ev -> {
            Integer ch = cargahorariafield.getValue();

            Integer cc = idcursofield.getValue();

            String nm = nomefield.getValue();
            if(nm == "" ){
                Notification.show("Insira o nome da disciplina!");
                nomefield.focus();
                return;
            }

            if(ch == null || ch <= 0 ){
                Notification.show("Carga horária inválida!");
                cargahorariafield.focus();
                return;
            }

            if(cc == null || cc <= 0 ){
                Notification.show("ID do curso inválida!");
                idcursofield.focus();
                return;
            }
            
            Disciplina novo = new Disciplina();
            novo.setNome(nomefield.getValue());
            novo.setCargaHoraria(cargahorariafield.getValue());
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

        grid.addColumn(Disciplina::getNome)
            .setHeader("Disciplina");
        grid.addColumn(Disciplina::getCargaHoraria)
            .setHeader("Carga Horária (Hrs)");
        grid.addColumn(Disciplina::getIdcurso)
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
                cargahorariafield.setValue(c.getCargaHoraria());
                idcursofield.setValue(c.getIdcurso());
                id = c.getId();
                teste = false;
            });
            return editaButton;
        });
        grid.setItems(repository.listar());
        add(nomefield, cargahorariafield, idcursofield, hl, grid);
    }
}
