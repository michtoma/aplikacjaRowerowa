package tomaszewski.michal.aplikacjarowerowa.view.uiTrip;

import com.sun.jna.platform.win32.Sspi;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import tomaszewski.michal.aplikacjarowerowa.data.entity.Bike;
import tomaszewski.michal.aplikacjarowerowa.data.entity.Trip;
import tomaszewski.michal.aplikacjarowerowa.data.entity.User;

import java.util.List;

public class TripForm extends FormLayout {
    private Trip trip;
    DatePicker date = new DatePicker("date");
    TextField name = new TextField("name");
    ComboBox<User> user= new ComboBox("user");
    ComboBox<Bike> bike= new ComboBox("bike");
    NumberField time = new NumberField("Time in minutes");
    NumberField distance = new NumberField("distance (m)");
    Button save = new Button("Zapisz");
    Button delete = new Button("Usuń");
    Button cancel = new Button("Zrezygnuj");
    Binder<Trip> binder = new BeanValidationBinder<>(Trip.class);



    public TripForm(List<User> users, List<Bike> bikes) {
        List<User> usersList = users;
        addClassName("contact-formTrip");
        binder.bindInstanceFields(this);
        user.setItems(usersList);
        user.setItemLabelGenerator(User::getUsername);
        bike.setItems(bikes);
        bike.setItemLabelGenerator(Bike::getModel);

        add(date,name,user,bike,distance,time, createButtonLayout());
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
        binder.readBean(trip);
    }

    private HorizontalLayout createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, trip)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(trip);
            fireEvent(new SaveEvent(this, trip));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class ContactFormTripEvent extends ComponentEvent<TripForm> {
        private Trip trip;

        protected ContactFormTripEvent(TripForm source, Trip trip) {
            super(source, false);
            this.trip = trip;
        }

        public Trip getContact() {
            return trip;
        }
    }

    public static class SaveEvent extends ContactFormTripEvent {
        SaveEvent(TripForm source, Trip trip) {
            super(source, trip);
        }
    }

    public static class DeleteEvent extends ContactFormTripEvent {
        DeleteEvent(TripForm source, Trip trip) {
            super(source, trip);
        }

    }

    public static class CloseEvent extends ContactFormTripEvent {
        CloseEvent(TripForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
