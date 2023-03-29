package com.example.application.views.Aluno;


import com.example.application.entidades.Aluno;
import com.example.application.repositories.AlunoRepository;
import com.example.application.repositories.postgres.AlunoRepositoryImpl;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("CADASTRO DO ALUNO")
@Route(value = "aluno", layout = MainLayout.class)
public class AlunoView extends VerticalLayout{
    private TextField nomefield;
    private TextField datadenascimentofield;
    private TextField telefonefield;
    private TextField emailfield;
    private Button salvarButton;
    private Button limparButton;
    private Grid<Aluno> grid 
                        = new Grid<>(Aluno.class, false);
    private AlunoRepository repository 
                        = new AlunoRepositoryImpl(); 
    private Boolean teste = true;
    private int id;
    


    public AlunoView() {
        nomefield = new TextField("Nome do Aluno*");
        nomefield.setPrefixComponent(VaadinIcon.USER.create());
        nomefield.setClearButtonVisible(true);

        emailfield = new TextField("Email*");
        emailfield.getElement().setAttribute("name", "email");
        emailfield.setPlaceholder("username@gmail.com");
        emailfield.setErrorMessage(
            "Digite um Email válido!");
        emailfield.setPattern("^.+@gmail\\.com$");
        emailfield.setClearButtonVisible(true);
        

        datadenascimentofield = new TextField("Data de nascimento*");
        datadenascimentofield.setPrefixComponent(VaadinIcon.CALENDAR_USER.create());
        datadenascimentofield.setMaxLength(10);
        datadenascimentofield.setClearButtonVisible(true);
        datadenascimentofield.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        datadenascimentofield.setHelperText("01/01/2001");

        telefonefield = new TextField("Número de Telefone*");
        telefonefield.setPrefixComponent(VaadinIcon.PHONE.create());
        telefonefield.setMaxLength(13);
        telefonefield.setClearButtonVisible(true);
        telefonefield.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        telefonefield.setPattern("^?[(]?[0-9]{2}[)]?[-s.]?[0-9]{5}[-s.]?[0-9]{4,6}$");
        telefonefield.setHelperText("(55)555555555");

        salvarButton = new Button("Salvar");
        limparButton = new Button("Cancelar");

        limparButton.addClickListener(ev ->{
            nomefield.setValue("");
            datadenascimentofield.setValue("");
            telefonefield.setValue("");
            emailfield.setValue("");
        });

        salvarButton.addClickListener(ev -> {
            String ch = datadenascimentofield.getValue();

            String tl = telefonefield.getValue();

            String em = emailfield.getValue();

            String nm = nomefield.getValue();
            if(nm == "" ){
                Notification.show("Insira o nome do aluno!");
                nomefield.focus();
                return;
            }

            if(ch == "" ){
                Notification.show("Data de nascimento inválida!!");
                datadenascimentofield.focus();
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
            
            Aluno novo = new Aluno();
            novo.setNome(nomefield.getValue());
            novo.setDatadenascimento(datadenascimentofield.getValue());
            novo.setTelefone(telefonefield.getValue());
            novo.setEmail(emailfield.getValue());

            if(teste == true ){
                repository.inserir(novo);
                Notification.show("Salvo!!");
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

        grid.addColumn(Aluno::getNome)
            .setHeader("Nome Aluno");
        grid.addColumn(Aluno::getDatadenascimento)
            .setHeader("Data de nascimento Aluno");
        grid.addColumn(Aluno::getTelefone)
            .setHeader("Telefone Aluno");
        grid.addColumn(Aluno::getEmail)
            .setHeader("Email Aluno");
        grid.addColumn(Aluno::getId)
            .setHeader("ID Aluno");

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
                datadenascimentofield.setValue(c.getDatadenascimento());
                telefonefield.setValue(c.getTelefone());
                emailfield.setValue(c.getEmail());
                id = c.getId();
                teste = false;
            });
            return editaButton;
        });
        grid.setItems(repository.listar());
        add(nomefield, emailfield, datadenascimentofield, telefonefield, hl, grid);
    }
}
