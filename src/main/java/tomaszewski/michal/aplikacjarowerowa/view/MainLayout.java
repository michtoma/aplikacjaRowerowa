package tomaszewski.michal.aplikacjarowerowa.view;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import tomaszewski.michal.aplikacjarowerowa.data.security.SecurityService;
import tomaszewski.michal.aplikacjarowerowa.view.uiBike.BikeView;
import tomaszewski.michal.aplikacjarowerowa.view.uiTrip.TripView;
import tomaszewski.michal.aplikacjarowerowa.view.uiUser.UserView;

public class MainLayout extends AppLayout {
    private SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Bike Trip Application");
        logo.addClassNames("text-m", "m-l");
        Button buttonLogout = new Button("Log out", e->securityService.logout());
        HorizontalLayout header = new HorizontalLayout(

                new DrawerToggle(),
                logo, buttonLogout
        );


        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink userListLink = new RouterLink("User List", UserView.class);
        RouterLink bikeListLink = new RouterLink("Bike List", BikeView.class);
        RouterLink tripListLink = new RouterLink("Trip List", TripView.class);
        userListLink.setHighlightCondition(HighlightConditions.sameLocation());
        bikeListLink.setHighlightCondition(HighlightConditions.sameLocation());
        Image logoImage = new Image("https://i1.wp.com/bearclawbicycleco.com/wp-content/uploads/2022/02/5596B97D-7A59-41EB-879E-16F0EE7B8FC4.jpeg?fit=3000%2C2000&ssl=1","Bike");
        logoImage.setWidth("100%");
        logoImage.setHeight("100%");
        addToDrawer(new VerticalLayout(
                userListLink,
                bikeListLink,
                tripListLink,
                logoImage
        ));
    }
}
