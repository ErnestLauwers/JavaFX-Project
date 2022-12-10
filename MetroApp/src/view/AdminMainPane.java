package view;


import Controller.AdminViewController;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import model.database.MetroCardDatabase;
import view.panels.ControlCenterPane;
import view.panels.MetroCardOverviewPane;
import view.panels.SetupPane;

import java.util.Observable;

public class AdminMainPane extends BorderPane {
    private MetroCardOverviewPane metroCardOverviewPane;
    private ControlCenterPane controlCenterPane;
    private SetupPane setupPane;
    private MetroCardDatabase metroCardDatabase;

	public AdminMainPane(AdminViewController adminViewController){
	    TabPane tabPane = new TabPane();
        metroCardOverviewPane = new MetroCardOverviewPane(adminViewController);
        controlCenterPane = new ControlCenterPane();
	    setupPane = new SetupPane();
        Tab metroCardOverviewTab = new Tab("Metro cards overview",metroCardOverviewPane);
        Tab controlCenterTab = new Tab("Control Center", controlCenterPane);
        Tab setupTab = new Tab("Setup", setupPane);
        tabPane.getTabs().add(controlCenterTab);
        tabPane.getTabs().add(metroCardOverviewTab);
        tabPane.getTabs().add(setupTab);
        this.setCenter(tabPane);
	}
}
