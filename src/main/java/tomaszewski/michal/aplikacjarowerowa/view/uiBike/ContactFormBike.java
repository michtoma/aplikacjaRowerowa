package tomaszewski.michal.aplikacjarowerowa.view.uiBike;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import tomaszewski.michal.aplikacjarowerowa.data.entity.Bike;
import java.util.ArrayList;
import java.util.List;

public class ContactFormBike extends FormLayout {
    Binder<Bike> binder = new BeanValidationBinder<>(Bike.class);
    TextField producer = new TextField("producer");
    TextField model = new TextField("Model");
    NumberField weight = new NumberField("Weight");

    ComboBox<String> type = new ComboBox<>("Type");
    NumberField wheelSize = new NumberField("WheelSize");
    Button save = new Button("Zapisz");
    Button delete = new Button("Usuń");
    Button cancel = new Button("Zrezygnuj");
    private Bike bike;
    private static final String userRole = "ADMIN";


    public ContactFormBike() {
        List<String> bikeType= new ArrayList<>();
        bikeType.add("MTB");
        bikeType.add("ROAD");
        bikeType.add("GRAVEL");
        bikeType.add("CITY");

        addClassName("contact-form");
        binder.bindInstanceFields(this);
        type.setItems(bikeType);
        type.setItemLabelGenerator(String::valueOf);

        add(producer, model,wheelSize, weight,type, createButtonLayout());
    }

    public void setBike(Bike bike) {
        this.bike = bike;
        binder.readBean(bike);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, bike)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(bike);
            fireEvent(new SaveEvent(this, bike));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class ContactFormBikeEvent extends ComponentEvent<ContactFormBike> {
        private Bike bike;

        protected ContactFormBikeEvent(ContactFormBike source, Bike bike) {
            super(source, false);
            this.bike = bike;
        }

        public Bike getContact() {
            return bike;
        }
    }

    public static class SaveEvent extends ContactFormBikeEvent {
        SaveEvent(ContactFormBike source, Bike bike) {
            super(source, bike);
        }
    }

    public static class DeleteEvent extends ContactFormBikeEvent {
        DeleteEvent(ContactFormBike source, Bike bike) {
            super(source, bike);
        }

    }

    public static class CloseEvent extends ContactFormBikeEvent {
        CloseEvent(ContactFormBike source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
