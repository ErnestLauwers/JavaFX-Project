package Controller;

import model.MetroFacade;
import model.Subject;
import model.database.MetroCardDatabase;
import model.database.MetroEvent;
import view.AdminView;

import java.util.Observable;
import java.util.Observer;

public class AdminViewController implements Observer {

    private AdminView adminView;
    private MetroFacade metroFacade;

    public AdminViewController(MetroFacade metroFacade) {
        this.metroFacade = metroFacade;
        this.metroFacade.registerObserver(this);
    }

    public MetroCardDatabase getMetroCardDatabase() {
        return metroFacade.getMetroCardDatabase();

    }

    public void setAdminView(AdminView adminView) {
        this.adminView = adminView;
    }

    public String getLoadStrategy() {
        return metroFacade.getLoadSaveStrategy();
    }

    public void update(MetroEvent metroEvent) {
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
