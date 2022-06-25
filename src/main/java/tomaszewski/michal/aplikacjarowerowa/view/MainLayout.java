package tomaszewski.michal.aplikacjarowerowa.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;
import tomaszewski.michal.aplikacjarowerowa.data.security.SecurityService;
import tomaszewski.michal.aplikacjarowerowa.view.uiBike.BikeView;
import tomaszewski.michal.aplikacjarowerowa.view.uiTrip.TripView;
import tomaszewski.michal.aplikacjarowerowa.view.uiUser.UserView;

public class MainLayout extends AppLayout {
    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.getElement().setAttribute("theme", Lumo.DARK);

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
        addToDrawer(new VerticalLayout(
                userListLink,
                bikeListLink,
                tripListLink
        ));
    }
}
