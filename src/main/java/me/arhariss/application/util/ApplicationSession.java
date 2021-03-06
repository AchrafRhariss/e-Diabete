package me.arhariss.application.util;

import org.springframework.stereotype.Component;

import me.arhariss.application.entities.Adherent;

@Component
public class ApplicationSession {

	private Adherent selectedAdherent;
	
	private String cuurentPage;

	public ApplicationSession() {
		
	}

	
	public String getCuurentPage() {
		return cuurentPage;
	}


	public void setCuurentPage(String cuurentPage) {
		this.cuurentPage = cuurentPage;
	}


	public Adherent getSelectedAdherent() {
		return selectedAdherent;
	}

	public void setSelectedAdherent(Adherent selectedAdherent) {
		this.selectedAdherent = selectedAdherent;
	}

}
