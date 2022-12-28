/**
 * @author Ernest Lauwers
 */
package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.MetroFacade;
import view.panels.AdminMainPane;

public class AdminView {
	private Stage stage = new Stage();
	private AdminMainPane adminMainPane;

	public AdminView(MetroFacade metroFacade) {
		stage.setTitle("ADMIN VIEW");
		stage.setX(660);
		stage.setY(5);
		stage.setResizable(false);
		Group root = new Group();
		Scene scene = new Scene(root, 690, 680);
		adminMainPane = new AdminMainPane(metroFacade);
		adminMainPane.prefHeightProperty().bind(scene.heightProperty());
		adminMainPane.prefWidthProperty().bind(scene.widthProperty());
		root.getChildren().add(adminMainPane);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}
}