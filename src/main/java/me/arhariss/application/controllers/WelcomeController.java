package me.arhariss.application.controllers;



import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@Component
public class WelcomeController {
	
	private double x,y;
	
	@FXML
	private AnchorPane mainpane;

	@FXML
	private ImageView logoSopra;

	@FXML
	private Label username;

	@FXML
	private Label email;

	@FXML
	private Label statistiquesLink;

	@FXML
	private Label utilisateursLink;

	@FXML
	private Label idClient;

	@FXML
	private Label idReservation;

	@FXML
	private Label idContrat;

	@FXML
	private Label idFacture;

	@FXML
	private Label idVoiture;

	@FXML
	private Label idParking;

	@FXML
	private Label idSanction;

	@FXML
	private Label idSanction11;

	@FXML
	private ImageView logoutImg;

	@FXML
	private Label viewTitle;
	
	@Autowired
	private ConfigurableApplicationContext springContext;

	public ConfigurableApplicationContext getSpringContext() {
		return springContext;
	}

	public void setSpringContext(ConfigurableApplicationContext springContext) {
		this.springContext = springContext;
	}

	@FXML
	void closeWindow(MouseEvent event) {
		Stage stage = (Stage) username.getScene().getWindow();
		stage.close();
		System.exit(1);
	}

	@FXML
	void minimizeWindow(MouseEvent event) {
		Stage stage = (Stage) username.getScene().getWindow();
		stage.setIconified(true);
	}
	
	public void moving(MouseEvent mouseEvent) {

		mainpane.getScene().getWindow().setX(mouseEvent.getScreenX() + x);
		mainpane.getScene().getWindow().setY(mouseEvent.getScreenY() + y);
	}

	public void premoving(MouseEvent mouseEvent) {

		// record a delta distance for the drag and drop operation.
		x = (double) (mainpane.getScene().getWindow().getX() - mouseEvent.getScreenX());
		y = (double) (mainpane.getScene().getWindow().getY() - mouseEvent.getScreenY());
	}
	
	@FXML
	void loadViewToMainPane(MouseEvent event) {
		String vienName = ((Label)(event.getSource())).getText().replace("é", "e");
    	String viewPath = "../views/" + vienName + ".fxml";
//    	viewTitle.setText("Gestion " + vienName);
    	try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(viewPath));
			fxmlLoader.setControllerFactory(springContext::getBean);
			Node root = (Node) fxmlLoader.load();
			mainpane.getChildren().setAll(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void logout(MouseEvent event) {
		
	}
}
