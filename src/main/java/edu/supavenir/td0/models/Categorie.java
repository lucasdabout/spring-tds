package edu.supavenir.td0.models;

import java.util.ArrayList;
import java.util.List;

public class Categorie {
	private String nom;
	private List<Element> items;

	public Categorie(String nom) {
		this.nom = nom;
		items = new ArrayList<Element>();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Element> getItems() {
		return items;
	}

	public void setItems(List<Element> items) {
		this.items = items;
	}

}
