package tomaszewski.michal.aplikacjarowerowa.view.uiBike;
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
import tomaszewski.michal.aplikacjarowerowa.data.entity.Bike;
import tomaszewski.michal.aplikacjarowerowa.data.service.ApiService;
import tomaszewski.michal.aplikacjarowerowa.view.MainLayout;
import javax.annotation.security.RolesAllowed;

@PageTitle("Bikes | Bike Trip Application")
@Route(value = "bikelist", layout = MainLayout.class)
@RolesAllowed({"USER","ADMIN"})
public class BikeView extends VerticalLayout {


    Grid<Bike> bikeGrid = new Grid<>(Bike.class);
    TextField filterText = new TextField();
    BikeForm formBike;
    private final ApiService service;
    public BikeView(ApiService service){
        this.getElement().setAttribute("theme", Lumo.DARK);

        this.service = service;
        addClassName("bikeListView");
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
        formBike.setBike(null);
        formBike.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        bikeGrid.setItems(service.findAllBikes(filterText.getValue()));
    }

    private Component getContent(){
        HorizontalLayout content= new HorizontalLayout(bikeGrid, formBike);
        content.setFlexGrow(2, bikeGrid);
        content.setFlexGrow(1, formBike);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }
    private void configureForm(){


        formBike = new BikeForm();
        formBike.setWidth("25em");

        formBike.addListener(BikeForm.SaveEvent.class, this::saveBike);
        formBike.addListener(BikeForm.DeleteEvent.class, this::deleteBike);
        formBike.addListener(BikeForm.CloseEvent.class, e-> closeEditor());

    }
    private void saveBike(BikeForm.SaveEvent event){
        service.saveBike(event.getContact());
        updateList();
        closeEditor();
    }
    private void deleteBike(BikeForm.DeleteEvent event){
        service.deleteBike(event.getContact());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by bike...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        Button buttonAddBike = new Button("Add bike");
        buttonAddBike.addClickListener(e-> addBike());
        HorizontalLayout toolbar = new HorizontalLayout(filterText,buttonAddBike);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addBike() {
        bikeGrid.asSingleSelect().clear();
        editBike(new Bike());
    }

    private void configureGrid(){
        bikeGrid.addClassName("bike-grid");
        bikeGrid.setSizeFull();
        bikeGrid.setColumns("producer", "model", "type","wheelSize","weight");
        bikeGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        bikeGrid.asSingleSelect().addValueChangeListener(e-> editBike(e.getValue()));
    }

    private void editBike(Bike bike) {
        if(bike == null){
            closeEditor();
        }else{
            formBike.setBike(bike);
            formBike.setVisible(true);
            addClassName("editing");
        }
    }


}