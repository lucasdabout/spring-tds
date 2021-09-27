package edu.supavenir.td0.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import edu.supavenir.td0.models.Categorie;
import edu.supavenir.td0.models.Element;
import edu.supavenir.td0.technics.CssMessage;

@Controller
@SessionAttributes("categories")
public class ItemsController {

    private Categorie getCategorieByName(String nom, List<Categorie> categories) {
	int index = categories.indexOf(new Categorie(nom));
	return categories.get(index);
    }

    @ModelAttribute("categories")
    public List<Categorie> getCategories() {
	return new ArrayList<>(Arrays.asList(new Categorie("Amis"), new Categorie("Famille"), new Categorie("Pros")));
    }

    @GetMapping("/")
    public String itemsAction(Model model, @ModelAttribute String categorie) {
	if (categorie != null) {
	    model.addAttribute("categorie", categorie);
	} else {
	    model.addAttribute("categorie", "Amis");
	}
	return "items";
    }

    @GetMapping("/items/new/{categorie}")
    public String itemsNew(@PathVariable String categorie) {
	return "form";
    }

    @PostMapping("/items/addNew/{categorie}")
    public RedirectView add(@PathVariable String categorie, Element elm, @SessionAttribute List<Categorie> categories,
	    RedirectAttributes attrs) {
	Categorie cat = getCategorieByName(categorie, categories);
	if (cat.addItem(elm)) {
	    attrs.addFlashAttribute("msg", CssMessage.SuccessMessage("Element <b>" + elm + "</b> ajouté"));
	} else {
	    attrs.addFlashAttribute("msg", CssMessage.ErrorMessage("Cet élément existe déjà !"));
	}
	attrs.addFlashAttribute("categorie", categorie);
	return new RedirectView("/");
    }

    @GetMapping("/items/inc/{nom}/{categorie}")
    public RedirectView incAction(@PathVariable String nom, @PathVariable String categorie,
	    @SessionAttribute List<Element> categories) {
	getCategorieByName(categorie, categories).getElementByname(nom).inc();
	return new RedirectView("/");
    }

    @GetMapping("/items/dec/{nom}/{categorie}")
    public RedirectView decAction(@PathVariable String nom, @PathVariable String categorie,
	    @SessionAttribute List<Element> categories) {
	getCategorieByName(categorie, categories).getElementByname(nom).inc();
	return new RedirectView("/");
    }

    @GetMapping("/items/delete/{nom}/{categorie}")
    public RedirectView deleteAction(@PathVariable String nom, @SessionAttribute List<Element> items,
	    RedirectAttributes attrs) {
	items.remove(new Element(nom));
	attrs.addFlashAttribute("msg", CssMessage.SuccessMessage("L'élément <b>" + nom + "</b> a bien été effacé"));
	return new RedirectView("/");
    }
}
