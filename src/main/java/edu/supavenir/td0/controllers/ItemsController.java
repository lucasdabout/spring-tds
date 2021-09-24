package edu.supavenir.td0.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import edu.supavenir.td0.models.Element;
import edu.supavenir.td0.technics.CssMessage;

@Controller
@SessionAttributes("items")
public class ItemsController {

	@ModelAttribute("items")
	public List<Element> getItems() {
		return new ArrayList<>();
	}

	@GetMapping("/")
	public String itemsAction() {
		return "items";
	}

	@GetMapping("/items/new")
	public String itemsNew() {
		return "form";
	}

	@PostMapping("/items/addNew")
	public RedirectView add(Element elm, @SessionAttribute List<Element> items, RedirectAttributes attrs) {
		if (!items.contains(elm)) {
			items.add(elm);
			attrs.addFlashAttribute("msg", CssMessage.SuccessMessage("Element <b>" + elm + "</b> ajouté"));
		} else {
			attrs.addFlashAttribute("msg", CssMessage.ErrorMessage("Cet élément existe déjà !"));
		}
		return new RedirectView("/");
	}

	@GetMapping("/items/inc/{nom}")
	public RedirectView incAction(@PathVariable String nom, @SessionAttribute List<Element> items) {
		int index = items.indexOf(new Element(nom));
		Element elm = items.get(index);
		elm.inc();
		return new RedirectView("/");
	}

	@GetMapping("/items/dec/{nom}")
	public RedirectView decAction(@PathVariable String nom, @SessionAttribute List<Element> items) {
		int index = items.indexOf(new Element(nom));
		Element elm = items.get(index);
		elm.dec();
		return new RedirectView("/");
	}
}
