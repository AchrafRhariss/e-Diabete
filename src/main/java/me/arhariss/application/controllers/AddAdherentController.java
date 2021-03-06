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

@Component
public class AddAdherentController {

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

	@Autowired
	private AdherentRepository adherentRepository;

	public AdherentRepository getAdherentRepository() {
		return adherentRepository;
	}

	public void setAdherentRepository(AdherentRepository adherentRepository) {
		this.adherentRepository = adherentRepository;
	}
	

	
	@FXML
	void saveAdherent(MouseEvent event) {

		if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || address.getText().isEmpty()
				|| telephone.getText().isEmpty() || age.getText().isEmpty() || orderedNumber.getText().isEmpty()
				|| usedDrugs.getText().isEmpty() || birthDate.getValue() == null || adhesionDate.getValue() == null) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Attention");
			alert.setHeaderText("Impossible de sauvegarder l'adhérent");
			alert.setContentText(String.format("Tous les champs doivent être remplis."));
			alert.showAndWait();

		} else {

			Adherent nvAdherent = new Adherent();
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
				AdherentController.getAdherentObservableList().addAll(adherentRepository.save(nvAdherent));// DONOT DO THAT
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Informarion");
				alert.setHeaderText("Transaction Validée");
				alert.setContentText(String.format("L'adhérent(e) %s %s à été ajouté avec succés",
						nvAdherent.getFirstName(), nvAdherent.getLastName()));
				alert.showAndWait();
				reset(event);
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERREUR");
				alert.setHeaderText("Une erreur est survenue lors du sauvegarde de l'adhérent");
				alert.setContentText(String.format("Vérifier les informations fournis"));
				alert.showAndWait();
			}
		}
	}

	@FXML
	void reset(MouseEvent event) {
		firstName.setText("");
		lastName.setText("");
		address.setText("");
		telephone.setText("");
		age.setText("");
		orderedNumber.setText("");
		birthDate.setValue(null);
		adhesionDate.setValue(null);
		usedDrugs.setText("");
	}

	@FXML
	void returnBack(MouseEvent event) {

	}

}
