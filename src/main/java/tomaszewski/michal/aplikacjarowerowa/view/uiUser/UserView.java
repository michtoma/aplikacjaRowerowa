package tomaszewski.michal.aplikacjarowerowa.view.uiUser;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import tomaszewski.michal.aplikacjarowerowa.data.entity.User;
import tomaszewski.michal.aplikacjarowerowa.data.service.ApiService;
import tomaszewski.michal.aplikacjarowerowa.view.MainLayout;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

@Route(value = "userlist", layout = MainLayout.class)
@PageTitle("Users | Rowerowa Appka")
@RolesAllowed("ADMIN")
public class UserView extends VerticalLayout {
    Grid<User> grid = new Grid<>(User.class);
    TextField filterText = new TextField();
    ContactForm form;
    private ApiService service;
    public UserView(ApiService service){
        this.service = service;
        addClassName("userListView");
        setSizeFull();

        configureGrid();
        configureForm();

        add(
            getToolbar(),
            getContent()
        );
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
    grid.setItems(service.findAllUsers(filterText.getValue()));
    }

    private Component getContent(){
        HorizontalLayout content= new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }
    private void configureForm(){



        form = new ContactForm();
        form.setWidth("25em");

        form.addListener(ContactForm.SaveEvent.class, this::saveUser);
        form.addListener(ContactForm.DeleteEvent.class, this::deleteUser);
        form.addListener(ContactForm.CloseEvent.class, e-> closeEditor());

        }
        private void saveUser(ContactForm.SaveEvent event){
            service.saveUser(event.getContact());
            updateList();
            closeEditor();
        };
    private void deleteUser(ContactForm.DeleteEvent event){
        service.deleteUser(event.getContact());
        updateList();
        closeEditor();
    };

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        Button buttonAddUser = new Button("Add user");
        buttonAddUser.addClickListener(e-> addUser());
        HorizontalLayout toolbar = new HorizontalLayout(filterText,buttonAddUser);
        toolbar.addClassName("toolbar");
        return toolbar;

    }

    private void addUser() {
        grid.asSingleSelect().clear();
        editUser(new User());
    }

    private void configureGrid(){
        grid.addClassName("users-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "username","birthDate","role","password");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(e-> editUser(e.getValue()));
    }

    private void editUser(User user) {
        if(user == null){
            closeEditor();
        }else{
            form.setUser(user);
            form.setVisible(true);
            addClassName("editing");
        }
    }


}
