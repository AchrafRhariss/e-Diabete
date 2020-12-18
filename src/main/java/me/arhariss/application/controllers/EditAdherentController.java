package me.arhariss.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import me.arhariss.application.entities.Adherent;
import me.arhariss.application.repositories.AdherentRepository;
import me.arhariss.application.util.ApplicationSession;

@Component
public class EditAdherentController {

	@FXML
	private TextField lastName;

	@FXML
	private TextField age;

	@FXML
	private TextField address;

	@FXML
	private DatePicker birthDate;

	@FXML
	private TextField firstName;

	@FXML
	private TextField orderedNumber;

	@FXML
	private TextField telephone;

	@FXML
	private DatePicker adhesionDate;

	@FXML
	private TextArea usedDrugs;

	@FXML
	private Label save;

	@FXML
	private Label title;

	@Autowired
	private AdherentRepository adherentRepository;

	@Autowired
	private ApplicationSession applicationSession;

	public AdherentRepository getAdherentRepository() {
		return adherentRepository;
	}

	public void setAdherentRepository(AdherentRepository adherentRepository) {
		this.adherentRepository = adherentRepository;
	}

	public ApplicationSession getApplicationSession() {
		return applicationSession;
	}

	public void setApplicationSession(ApplicationSession applicationSession) {
		this.applicationSession = applicationSession;
	}

	private Adherent nvAdherent;

	@FXML
	public void initialize() {

		Adherent selectedAdherent = applicationSession.getSelectedAdherent();
		nvAdherent = new Adherent();
		nvAdherent.setId(selectedAdherent.getId());
		
		title.setText(title.getText() + selectedAdherent.getOrderedNumber());
		
		firstName.setText(selectedAdherent.getFirstName());
		lastName.setText(selectedAdherent.getLastName());
		address.setText(selectedAdherent.getAddress());
		telephone.setText(selectedAdherent.getTelephone());
		age.setText(String.valueOf(selectedAdherent.getAge()));
		orderedNumber.setText(selectedAdherent.getOrderedNumber());
		birthDate.setValue(selectedAdherent.getBirthDate());
		adhesionDate.setValue(selectedAdherent.getAdhesionDate());
		usedDrugs.setText(selectedAdherent.getUsedDrugs());
	}

	@FXML
	void saveAdherent(MouseEvent event) {

		if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || address.getText().isEmpty()
				|| telephone.getText().isEmpty() || age.getText().isEmpty() || orderedNumber.getText().isEmpty()
				|| usedDrugs.getText().isEmpty() || birthDate.getValue() == null || adhesionDate.getValue() == null) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Attention");
			alert.setHeaderText("Impossible de modifier l'adhérent");
			alert.setContentText(String.format("Tous les champs doivent être remplis."));
			alert.showAndWait();

		} else {

			try {
				nvAdherent.setFirstName(firstName.getText());
				nvAdherent.setLastName(lastName.getText());
				nvAdherent.setAddress(address.getText());
				nvAdherent.setTelephone(telephone.getText());
				nvAdherent.setAge(Integer.valueOf(age.getText()));
				nvAdherent.setOrderedNumber(orderedNumber.getText());
				nvAdherent.setBirthDate(birthDate.getValue());
				nvAdherent.setAdhesionDate(adhesionDate.getValue());
				nvAdherent.setUsedDrugs(usedDrugs.getText());

				int index = AdherentController.getAdherentObservableList().indexOf(nvAdherent);
				AdherentController.getAdherentObservableList().set(index, adherentRepository.save(nvAdherent));

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Informarion");
				alert.setHeaderText("Transaction Validée");
				alert.setContentText(String.format("L'adhérent(e) %s %s à été modifié avec succés",
						nvAdherent.getFirstName(), nvAdherent.getLastName()));
				alert.showAndWait();

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERREUR");
				alert.setHeaderText("Une erreur est survenue lors du modification de l'adhérent");
				alert.setContentText(String.format("Vérifier les informations fournis\n%s", e.getMessage()));
				alert.showAndWait();
				e.printStackTrace();
			}
		}
	}

	@FXML
	void returnBack(MouseEvent event) {

	}

}
