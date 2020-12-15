package me.arhariss.application.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import me.arhariss.application.entities.Adherent;
import me.arhariss.application.repositories.AdherentRepository;
import me.arhariss.application.util.ApplicationSession;

@Component
public class AdherentController {

	@FXML
	private Pane pane;

	@FXML
	private TextField adherentName;

	@FXML
	private TableView<Adherent> adherentTable;

	@FXML
	private TableColumn<Adherent, String> number;

	@FXML
	private TableColumn<Adherent, String> lastName;

	@FXML
	private TableColumn<Adherent, String> firstName;

	@FXML
	private TableColumn<Adherent, LocalDate> birthDate;

	@FXML
	private TableColumn<Adherent, String> address;

	@FXML
	private TableColumn<Adherent, String> telephone;

	@FXML
	private TableColumn<Adherent, String> usedDrugs;

	@FXML
	private TableColumn<Adherent, LocalDate> adhesionDate;

	private ObservableList<Adherent> adherentObservableList;

	@FXML
	private Label searchAdherent;

	@FXML
	private ImageView search;

	@FXML
	private Label clearSearchLabel;

	@FXML
	private Label consultBtn;

	@FXML
	private Label editBtn;

	@FXML
	private Label deleteBtn;

	@Autowired
	private ApplicationSession applicationSession;

	@Autowired
	private AdherentRepository adherentRepository;
	
	@Autowired
	private ConfigurableApplicationContext springContext;
	

	public ConfigurableApplicationContext getSpringContext() {
		return springContext;
	}

	public void setSpringContext(ConfigurableApplicationContext springContext) {
		this.springContext = springContext;
	}

	public ApplicationSession getApplicationSession() {
		return applicationSession;
	}

	public void setApplicationSession(ApplicationSession applicationSession) {
		this.applicationSession = applicationSession;
	}

	public AdherentRepository getAdherentRepository() {
		return adherentRepository;
	}

	public void setAdherentRepository(AdherentRepository adherentRepository) {
		this.adherentRepository = adherentRepository;
	}

	@PostConstruct
	private void init() {
		// get values of adherents from database and set them into an observable object
		

	}

	@FXML
	private void initialize() {

		adherentObservableList = FXCollections.observableArrayList(adherentRepository.findAll());
		// link each celldata in the tableview with the correspondant property on the
		// entity object
		number.setCellValueFactory(cellData -> cellData.getValue().orderedNumberProperty());
		firstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		birthDate.setCellValueFactory(cellData -> cellData.getValue().birthDateProperty());
		address.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
		telephone.setCellValueFactory(cellData -> cellData.getValue().telephoneProperty());
		usedDrugs.setCellValueFactory(cellData -> cellData.getValue().usedDrugsProperty());
		adhesionDate.setCellValueFactory(cellData -> cellData.getValue().adhesionDateProperty());

		// Add the observable object with all data on it to the table view so that it
		// tracks the chages on it.
		adherentTable.setItems(adherentObservableList);
		pane.requestFocus();
		consultBtn.setDisable(true);
		deleteBtn.setDisable(true);
		editBtn.setDisable(true);
	}

	@FXML
	void addNewAdherent(MouseEvent event) {
		try {
			Stage addAdherentStage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/AddAdherent.fxml"));
//			fxmlLoader.setControllerFactory(springContext::getBean);
			AnchorPane root = (AnchorPane) fxmlLoader.load();
			Scene addAdherentScene = new Scene(root, 1013, 600);
			addAdherentStage.setScene(addAdherentScene);
			addAdherentStage.setTitle("Ajouter Un Nouveau Adhérent");
			addAdherentStage.centerOnScreen();
			addAdherentStage.setAlwaysOnTop(true);
			addAdherentStage.setResizable(false);
			addAdherentStage.show();
			System.out.println("here iam");
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	@FXML
	void clearSearch(MouseEvent event) {
		clearSearchLabel.setVisible(false);
		searchAdherent.setVisible(true);
		adherentName.clear();
		adherentObservableList.clear();
		adherentObservableList.addAll(FXCollections.observableArrayList(adherentRepository.findAll()));
	}

	@FXML
	void clicked(MouseEvent event) {
		Adherent adherent = adherentTable.getSelectionModel().getSelectedItem();
		if (adherent != null) {
			applicationSession.setSelectedAdherent(adherent);
			editBtn.setDisable(false);
			deleteBtn.setDisable(false);
		}
		pane.requestFocus();
	}

	@FXML
	void deleteAdherent(MouseEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation De Suppression");
		alert.setHeaderText("Vous allez supprimer ce Adhérent");
		alert.setContentText("êtes vous sûr ?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			try {
				adherentRepository.delete(applicationSession.getSelectedAdherent());
				adherentObservableList.remove(applicationSession.getSelectedAdherent());
			} catch (Exception e) {
				Alert error = new Alert(AlertType.ERROR);
				error.setTitle("Erreur Suppression de l'Adhérent");
				error.setHeaderText("La suppression a échoué");
				error.setContentText("Ooops, there was an error!");

				error.showAndWait();
			}
		} else {
			clicked(event);
		}
	}

	@FXML
	void editAdherent(MouseEvent event) {
		try {
			Stage addAdherentStage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("../views/EditAdherent.fxml"));
			Scene addAdherentScene = new Scene(root, 550, 500);
			addAdherentStage.setScene(addAdherentScene);
			addAdherentStage.setTitle("Modifier l'Adhérent");
			addAdherentStage.centerOnScreen();
			addAdherentStage.setAlwaysOnTop(true);
			addAdherentStage.setResizable(false);
			addAdherentStage.show();

		} catch (IOException e) {

		}
	}

	@FXML
	void getAdherentDetails(MouseEvent event) {

	}

	@FXML
	void returnBack(MouseEvent event) {
		Node DashboardSceneGraph;
		try {
			DashboardSceneGraph = (Node) FXMLLoader.load(getClass().getResource("../views/Nothing.fxml"));
			AnchorPane mainpane = (AnchorPane) pane.getParent();
			mainpane.getChildren().setAll(DashboardSceneGraph);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void searchAdherent(KeyEvent event) {
		clearSearchLabel.setVisible(true);
		searchAdherent.setVisible(false);
		List<Adherent> adherents = new ArrayList<Adherent>();
		String firstOrLastName = adherentName.getText().trim();
		if(!firstOrLastName.isEmpty()) {
			adherents = adherentRepository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(firstOrLastName,
					firstOrLastName);
		}
			

		if (adherents.isEmpty()) {
			adherentObservableList.clear();
		} else {
			adherentObservableList.clear();
			adherentObservableList.addAll(adherents);
		}
	}

	@FXML
	void tableClick(MouseEvent event) {
		Adherent adherent = adherentTable.getSelectionModel().getSelectedItem();
		if (adherent != null) {
			applicationSession.setSelectedAdherent(adherent);
			editBtn.setDisable(false);
			deleteBtn.setDisable(false);
		}
	}

}
