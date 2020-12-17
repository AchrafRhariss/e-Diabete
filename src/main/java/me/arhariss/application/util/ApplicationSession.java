package me.arhariss.application.util;

import java.util.Stack;

import org.springframework.stereotype.Component;

import javafx.scene.Node;
import me.arhariss.application.entities.Adherent;

@Component
public class ApplicationSession {

	private Adherent selectedAdherent;

	private Stack<Node> pages;

	public ApplicationSession() {
		super();
		this.pages = new Stack<>();
	}

	public Adherent getSelectedAdherent() {
		return selectedAdherent;
	}

	public void setSelectedAdherent(Adherent selectedAdherent) {
		this.selectedAdherent = selectedAdherent;
	}

	public Stack<Node> getPages() {
		return pages;
	}

	public void setPages(Stack<Node> pages) {
		this.pages = pages;
	}
	
	public void pushPage(Node page) {
		this.pages.push(page);
	}

}
