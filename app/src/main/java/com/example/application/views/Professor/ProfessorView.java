package com.example.application.views.Professor;


import com.example.application.entidades.Professor;
import com.example.application.repositories.ProfessorRepository;
import com.example.application.repositories.postgres.ProfessorRepositoryImpl;
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
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("CADASTRO DO PROFESSOR")
@Route(value = "professor", layout = MainLayout.class)
public class ProfessorView extends VerticalLayout{
    private TextField nomefield;
    private IntegerField idprofessorfield;
    private TextField telefonefield;
    private TextField emailfield;
    private Button salvarButton;
    private Button limparButton;
    private Grid<Professor> grid 
                        = new Grid<>(Professor.class, false);
    private ProfessorRepository repository 
                        = new ProfessorRepositoryImpl(); 
    private Boolean teste = true;
    private int id;
    


    public ProfessorView() {
        nomefield = new TextField("Nome do Professor*");
        nomefield.setPrefixComponent(VaadinIcon.USER.create());
        nomefield.setClearButtonVisible(true);

        idprofessorfield = new IntegerField("ID do Professor*");
        idprofessorfield.setPrefixComponent(VaadinIcon.KEY.create());
        idprofessorfield.setClearButtonVisible(true);
        idprofessorfield.setHelperText("Apenas números");

        telefonefield = new TextField("Número de Telefone*");
        telefonefield.setPrefixComponent(VaadinIcon.PHONE.create());
        telefonefield.setMaxLength(13);
        telefonefield.setClearButtonVisible(true);
        telefonefield.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        telefonefield.setPattern("^?[(]?[0-9]{2}[)]?[-s.]?[0-9]{5}[-s.]?[0-9]{4,6}$");
        telefonefield.setHelperText("(55)555555555");

        emailfield = new TextField("Email*");
        emailfield.getElement().setAttribute("name", "email");
        emailfield.setPlaceholder("username@gmail.com");
        emailfield.setErrorMessage(
            "Digite um Email válido!");
        emailfield.setPattern("^.+@gmail\\.com$");
        emailfield.setClearButtonVisible(true);

        salvarButton = new Button("Salvar");
        limparButton = new Button("Cancelar");

        limparButton.addClickListener(ev ->{
            nomefield.setValue("");
            idprofessorfield.setValue(null);
            telefonefield.setValue("");
            emailfield.setValue("");
        });

        salvarButton.addClickListener(ev -> {
            Integer ch = idprofessorfield.getValue();

            String tl = telefonefield.getValue();

            String em = emailfield.getValue();

            String nm = nomefield.getValue();
            if(nm == "" ){
                Notification.show("Insira o nome do professor!");
                nomefield.focus();
                return;
            }

            if(ch == null || ch <= 0 ){
                Notification.show("ID professor inválido!");
                idprofessorfield.focus();
                return;
            }

            if(tl == "" ){
                Notification.show("Número de telefone inválida!!");
                telefonefield.focus();
                return;
            }

            if(em == "" ){
                Notification.show("Email inválido!!");
                emailfield.focus();
                return;
            }
            
            Professor novo = new Professor();
            novo.setNome(nomefield.getValue());
            novo.setIdprofessor(idprofessorfield.getValue());
            novo.setTelefone(telefonefield.getValue());
            novo.setEmail(emailfield.getValue());

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

        grid.addColumn(Professor::getNome)
            .setHeader("Nome Professor");
        grid.addColumn(Professor::getIdprofessor)
            .setHeader("ID Professor");
        grid.addColumn(Professor::getTelefone)
            .setHeader("Telefone Professor");
        grid.addColumn(Professor::getEmail)
            .setHeader("Email Professor");

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
                idprofessorfield.setValue(c.getIdprofessor());
                telefonefield.setValue(c.getTelefone());
                emailfield.setValue(c.getEmail());
                id = c.getId();
                teste = false;
            });
            return editaButton;
        });
        grid.setItems(repository.listar());
        add(nomefield, emailfield, idprofessorfield, telefonefield,  hl, grid);
    }
}
