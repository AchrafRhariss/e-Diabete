package me.arhariss.application.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
import javafx.scene.layout.Pane;
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

	private static ObservableList<Adherent> adherentObservableList;

	public static ObservableList<Adherent> getAdherentObservableList() {
		return adherentObservableList;
	}

	public static void setAdherentObservableList(ObservableList<Adherent> newAdherentObservableList) {
		adherentObservableList = newAdherentObservableList;
	}

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

	@Autowired
	private WelcomeController welcomeController;

	public WelcomeController getWelcomeController() {
		return welcomeController;
	}

	public void setWelcomeController(WelcomeController welcomeController) {
		this.welcomeController = welcomeController;
	}

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
		
		//EventListener for row selection in TableView:
		adherentTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Adherent>() {
			@Override
			public void changed(ObservableValue<? extends Adherent> observable, Adherent oldValue, Adherent newValue) {
				if(newValue != null) {
					applicationSession.setSelectedAdherent(newValue);
					editBtn.setDisable(false);
					deleteBtn.setDisable(false);
					consultBtn.setDisable(false);
				}
			}
		});
		
		//UI stuff
		pane.requestFocus();
		consultBtn.setDisable(true);
		deleteBtn.setDisable(true);
		editBtn.setDisable(true);
	}

	@FXML
	void getAdherentDetails(MouseEvent event) {

	}

	@FXML
	void addNewAdherent(MouseEvent event) {
		welcomeController.loadViewToMainPaneByName("AddAdherent");
	}

	@FXML
	void editAdherent(MouseEvent event) {
		welcomeController.loadViewToMainPaneByName("EditAdherent");
	}

	@FXML
	void deleteAdherent(MouseEvent event) {
		Adherent adherentToRemove = applicationSession.getSelectedAdherent();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Attention");
		alert.setHeaderText(String.format("Vous allez supprimer l' Adhérent %s %s", adherentToRemove.getFirstName(),
				adherentToRemove.getLastName()));
		alert.setContentText("êtes vous sûr ?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			try {
				adherentRepository.delete(adherentToRemove);
				adherentObservableList.remove(adherentToRemove);
			} catch (Exception e) {
				Alert error = new Alert(AlertType.ERROR);
				error.setTitle("ERREUR");
				error.setHeaderText("Une erreur est survenue lors de la suppression de l'adhérent");
				error.setContentText("Impossible de supprimer l'adhérent");

				error.showAndWait();
			}
		} else {
			clicked(event);
		}
	}

	@FXML
	void searchAdherent(KeyEvent event) {
		clearSearchLabel.setVisible(true);
		searchAdherent.setVisible(false);
		List<Adherent> adherents = new ArrayList<Adherent>();
		String firstOrLastName = adherentName.getText().trim();
		if (!firstOrLastName.isEmpty()) {
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
	void clearSearch(MouseEvent event) {
		clearSearchLabel.setVisible(false);
		searchAdherent.setVisible(true);
		adherentName.clear();
		adherentObservableList.clear();
		adherentObservableList.addAll(FXCollections.observableArrayList(adherentRepository.findAll()));
	}

	@FXML
	void clicked(MouseEvent event) {
		adherentTable.getSelectionModel().clearSelection();
		applicationSession.setSelectedAdherent(null);
		editBtn.setDisable(true);
		deleteBtn.setDisable(true);
		consultBtn.setDisable(true);
		pane.requestFocus();
	}


}
