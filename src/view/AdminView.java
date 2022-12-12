package view;

import controller.AdminViewController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminView {
	private Stage stage = new Stage();
	private AdminMainPane adminMainPane;

	public AdminView(AdminViewController controller) {
		controller.setAdminView(this);
		stage.setTitle("ADMIN VIEW");
		stage.setX(660);
		stage.setY(5);
		stage.setResizable(false);
		Group root = new Group();
		Scene scene = new Scene(root, 690, 680);
		adminMainPane = new AdminMainPane(controller);
		adminMainPane.prefHeightProperty().bind(scene.heightProperty());
		adminMainPane.prefWidthProperty().bind(scene.widthProperty());
		root.getChildren().add(adminMainPane);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}
}
