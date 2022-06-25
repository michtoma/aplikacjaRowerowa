package tomaszewski.michal.aplikacjarowerowa.view.uiTrip;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import tomaszewski.michal.aplikacjarowerowa.data.entity.Trip;
import tomaszewski.michal.aplikacjarowerowa.data.service.ApiService;
import tomaszewski.michal.aplikacjarowerowa.view.MainLayout;
import javax.annotation.security.PermitAll;

@PageTitle("Trips | Bike Trip Application")
@Route(value = "tripelist", layout = MainLayout.class)
@PermitAll
public class TripView extends VerticalLayout {


    Grid<Trip> grideTrip = new Grid<>(Trip.class);
    TextField filterText = new TextField();
    TripForm formTrip;
    private final ApiService apiService;


    public TripView(ApiService apiService){
        this.apiService = apiService;
        this.getElement().setAttribute("theme", Lumo.DARK);

        addClassName("tripListView");
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
        formTrip.setTrip(null);
        formTrip.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grideTrip.setItems(apiService.findAllTrips(filterText.getValue()));
    }

    private Component getContent(){
        HorizontalLayout content= new HorizontalLayout(grideTrip, formTrip);
        content.setFlexGrow(2, grideTrip);
        content.setFlexGrow(1, formTrip);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }
    private void configureForm(){
        formTrip = new TripForm(apiService.findAllUser(),apiService.findAllBikes());
        formTrip.setWidth("25em");

        formTrip.addListener(TripForm.SaveEvent.class, this::saveTrip);
        formTrip.addListener(TripForm.DeleteEvent.class, this::deleteTrip);
        formTrip.addListener(TripForm.CloseEvent.class, e-> closeEditor());

    }
    private void saveTrip(TripForm.SaveEvent event){
        apiService.saveTrip(event.getContact());
        updateList();
        closeEditor();
    }
    private void deleteTrip(TripForm.DeleteEvent event){
        apiService.deleteTrip(event.getContact());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by trip...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        Button buttonAddTrip = new Button("Add trip");
        buttonAddTrip.addClickListener(e-> addTrip());
        HorizontalLayout toolbar = new HorizontalLayout(filterText,buttonAddTrip);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addTrip() {
        grideTrip.asSingleSelect().clear();
        editTrip(new Trip());
    }

    private void configureGrid(){

        grideTrip.addClassName("trip-grid");
        grideTrip.setSizeFull();
        grideTrip.setColumns("name","distance","time");
        grideTrip.addColumn(trip -> apiService.tripDateFormated(trip)).setHeader("Date").setComparator(Trip::getDate);
        grideTrip.addColumn(trip -> trip.getBike().getProducer()).setHeader("Bike");
        grideTrip.addColumn(trip -> trip.getUser().getUsername()).setHeader("User");
        grideTrip.addColumn(trip -> apiService.getUserAge(trip.getUser())).setHeader("user age");
        grideTrip.addColumn(trip -> String.format("%.2f", apiService.averageSpeed(trip))).setHeader("average speed");
        grideTrip.getColumns().forEach(col -> col.setAutoWidth(true));
        grideTrip.asSingleSelect().addValueChangeListener(e-> editTrip(e.getValue()));
    }

    private void editTrip(Trip trip) {
        if(trip == null){

            closeEditor();

        }else{
            formTrip.setTrip(trip);
            formTrip.setVisible(true);
            addClassName("editing");
        }
    }


}