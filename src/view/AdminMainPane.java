package view;

import controller.ControlCenterPaneController;
import controller.MetroCardOverviewPaneController;
import controller.SetupPaneController;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import model.MetroFacade;
import view.panels.ControlCenterPane;
import view.panels.MetroCardOverviewPane;
import view.panels.SetupPane;

public class AdminMainPane extends BorderPane {
    private MetroCardOverviewPane metroCardOverviewPane;
    private ControlCenterPane controlCenterPane;
    private SetupPane setupPane;
    private MetroCardOverviewPaneController metroCardOverviewPaneController;
    private MetroFacade metroFacade;
    private ControlCenterPaneController controlCenterPaneController;
    private SetupPaneController setupPaneController;

    public AdminMainPane() {
        TabPane tabPane = new TabPane();
        metroFacade = new MetroFacade();
        metroCardOverviewPaneController = new MetroCardOverviewPaneController(metroFacade);
        metroCardOverviewPane = new MetroCardOverviewPane(metroCardOverviewPaneController);
        controlCenterPaneController = new ControlCenterPaneController(metroFacade);
        controlCenterPane = new ControlCenterPane(controlCenterPaneController);
        setupPaneController = new SetupPaneController(metroFacade);
        setupPane = new SetupPane(setupPaneController);
        Tab metroCardOverviewTab = new Tab("Metro cards overview", metroCardOverviewPane);
        Tab controlCenterTab = new Tab("Control Center", controlCenterPane);
        Tab setupTab = new Tab("Setup", setupPane);
        tabPane.getTabs().add(metroCardOverviewTab);
        tabPane.getTabs().add(controlCenterTab);
        tabPane.getTabs().add(setupTab);
        this.setCenter(tabPane);
    }
}

