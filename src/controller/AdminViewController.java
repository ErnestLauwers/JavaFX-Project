package controller;

import model.Facade;
import model.MetroEvent;
import model.Observer;
import model.database.MetroCardDatabase;
import view.AdminView;

public class AdminViewController implements Observer {

    private Facade metroFacade;
    private AdminView adminView;

    public AdminViewController(Facade facade) {
        this.metroFacade = facade;
        this.metroFacade.registerObserver(this);
    }

    public MetroCardDatabase getMetroCardsDatabase() {
        return metroFacade.getMetroCardDatabase();
    }

    public void setAdminView(AdminView adminView) {
        this.adminView = adminView;
    }

    public String getLoadSaveStrategy() {
        return metroFacade.getLoadSaveStrategy();
    }

    @Override
    public void update(MetroEvent event) {
    }
}