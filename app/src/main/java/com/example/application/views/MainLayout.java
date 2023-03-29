package com.example.application.views;


import com.example.application.components.appnav.AppNav;
import com.example.application.components.appnav.AppNavItem;
import com.example.application.views.Aluno.AlunoView;
import com.example.application.views.Curso.CursoView;
import com.example.application.views.Disciplina.DisciplinaView;
import com.example.application.views.FeedBack.FeedBackView;
import com.example.application.views.Matricula.MatriculaView;
import com.example.application.views.Professor.ProfessorView;
import com.example.application.views.about.AboutView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("SISTEMA - ENSINO SUPERIOR");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        // AppNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        AppNav nav = new AppNav();

        nav.addItem(new AppNavItem("Aluno", AlunoView.class, "la la-user"));
        nav.addItem(new AppNavItem("Professor", ProfessorView.class, "la la-user"));
        nav.addItem(new AppNavItem("Matricula", MatriculaView.class, "la la-folder"));
        nav.addItem(new AppNavItem("Curso", CursoView.class, "la la-book"));
        nav.addItem(new AppNavItem("Disciplina", DisciplinaView.class, "la la-book"));
        nav.addItem(new AppNavItem("FeedBack", FeedBackView.class, "la la-comment"));
        nav.addItem(new AppNavItem("Sobre", AboutView.class, "la la-cog"));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
