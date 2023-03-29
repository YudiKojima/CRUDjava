package com.example.application.views.Matricula;


import com.example.application.entidades.Matricula;
import com.example.application.repositories.MatriculaRepository;
import com.example.application.repositories.postgres.MatriculaRepositoryImpl;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("MATRICULA DO ALUNO")
@Route(value = "matricula", layout = MainLayout.class)
public class MatriculaView extends VerticalLayout{
    private IntegerField cpffield;
    private IntegerField idalunofield;
    private IntegerField cepfield;
    private Button salvarButton;
    private Button limparButton;
    private Grid<Matricula> grid 
                        = new Grid<>(Matricula.class, false);
    private MatriculaRepository repository 
                        = new MatriculaRepositoryImpl(); 
    private Boolean teste = true;
    private int id;
    


    public MatriculaView() {
        cpffield = new IntegerField("CPF do Aluno*");
        cpffield.setPrefixComponent(VaadinIcon.USER_CARD.create());
        cpffield.setClearButtonVisible(true);
        cpffield.setHelperText("Apenas número");

        idalunofield = new IntegerField("ID do Aluno*");
        idalunofield.setPrefixComponent(VaadinIcon.KEY.create());
        idalunofield.setClearButtonVisible(true);
        idalunofield.setHelperText("Insira o mesmo ID gerado após cadrasto do Aluno.");

        cepfield = new IntegerField("CEP*");
        cepfield.setPrefixComponent(VaadinIcon.GLOBE.create());
        cepfield.setClearButtonVisible(true);
        cepfield.setHelperText("Apenas número");

        salvarButton = new Button("Salvar");
        limparButton = new Button("Cancelar");

        limparButton.addClickListener(ev ->{
            cpffield.setValue(null);
            idalunofield.setValue(null);
            cepfield.setValue(null);
        });

        salvarButton.addClickListener(ev -> {
            Integer ch = idalunofield.getValue();

            Integer ce = cepfield.getValue();

            Integer nm = cpffield.getValue();
            if(nm == null){
                Notification.show("Insira o CPF do aluno!");
                cpffield.focus();
                return;
            }

            if(ch == null){
                Notification.show("ID aluno inválido!");
                idalunofield.focus();
                return;
            }

            if(ce == null){
                Notification.show("CEP inválido!");
                cepfield.focus();
                return;
            }
            
            Matricula novo = new Matricula();
            novo.setCpf(cpffield.getValue());
            novo.setIdaluno(idalunofield.getValue());
            novo.setCep(cepfield.getValue());

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

        grid.addColumn(Matricula::getCpf)
            .setHeader("CPF Aluno");
        grid.addColumn(Matricula::getIdaluno)
            .setHeader("ID Aluno");
        grid.addColumn(Matricula::getCep)
            .setHeader("CEP Aluno");

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
                cpffield.focus();
                cpffield.setValue(c.getCpf());
                idalunofield.setValue(c.getIdaluno());
                cepfield.setValue(c.getCep());
                id = c.getId();
                teste = false;
            });
            return editaButton;
        });
        grid.setItems(repository.listar());
        add(cpffield, idalunofield, cepfield, hl, grid);
    }
}
