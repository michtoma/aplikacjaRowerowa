package tomaszewski.michal.aplikacjarowerowa.view.uiUser;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tomaszewski.michal.aplikacjarowerowa.data.entity.User;

import java.util.ArrayList;
import java.util.List;
//@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class contactFormUser extends FormLayout {

    Binder<User> binder = new BeanValidationBinder<>(User.class);
    TextField username = new TextField("username");
    PasswordField password = new PasswordField("password");
    TextField firstName = new TextField("firstName");
    TextField lastName = new TextField("lastName");
    DatePicker birthDate = new DatePicker("Date of Birth");
    NumberField weight = new NumberField("weight");
    ComboBox<String> role = new ComboBox<>("role");
    Button save = new Button("Zapisz");
    Button delete = new Button("Usuń");
    Button cancel = new Button("Zrezygnuj");
    private User user;


    public contactFormUser() {
        List<String> roles= new ArrayList<>();
        roles.add("ADMIN");
        roles.add("USER");
        addClassName("contact-form");
        binder.bindInstanceFields(this);
        role.setItems(roles);
        role.setItemLabelGenerator(String::valueOf);



        add(username, password, firstName, lastName, birthDate, weight,role, createButtonLayout());
    }

    public void setUser(User user) {
        this.user = user;
        binder.readBean(user);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, user)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(user);
            fireEvent(new SaveEvent(this, user));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<contactFormUser> {
        private User user;

        protected ContactFormEvent(contactFormUser source, User user) {
            super(source, false);
            this.user = user;
        }

        public User getContact() {
            return user;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(contactFormUser source, User user) {
            super(source, user);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(contactFormUser source, User user) {
            super(source, user);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(contactFormUser source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
