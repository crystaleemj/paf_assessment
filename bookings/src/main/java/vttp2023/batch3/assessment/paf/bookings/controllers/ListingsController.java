package vttp2023.batch3.assessment.paf.bookings.controllers;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import vttp2023.batch3.assessment.paf.bookings.models.Listing;
import vttp2023.batch3.assessment.paf.bookings.models.ListingDetail;
import vttp2023.batch3.assessment.paf.bookings.models.Search;
import vttp2023.batch3.assessment.paf.bookings.services.ListingsService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping
public class ListingsController {

	@Autowired
	ListingsService svc;

	// writing the landing page controller
	//TODO: Task 2
	@GetMapping()
	public String landingPage(Model model) {
		List<String> list = svc.listCountry();
		model.addAttribute("list", list);
		return "view1.html";
	}
	


	// TODO: Task 3
	// TODO: if search criteria violates search constraint, return view 1 with appropriate message
	@GetMapping(path = "/search")
	public String search(@Valid @ModelAttribute Search search, BindingResult result, Model model) {

		List<Listing> list = svc.listingsByCountry(search.getCountry(), search.getPerson(), search.getMin(), search.getMax());

		if (result.hasErrors()) {
			model.addAttribute("errorMsg", "Incorrect search fields!");
			return "view1.html";
		}

		// display appropriate message if the search returns no accommodation
		if (list.isEmpty()) {
			return "No Acommodations Found";
		}

		model.addAttribute("list", list);
		return "view2.html";
	}


	// @GetMapping(path = "/searched")
    // public String redirect(@RequestParam String country, @RequestParam Integer person, @RequestParam Integer min, @RequestParam Integer max, Model model, List<Document> list) {

    //     return "redirect:/search" + country + person + min + max;
    // }

	//TODO: Task 4
	@GetMapping(value="search/{id}")
	public String hotelDetails(@PathVariable String id, Model model) {
		ListingDetail listingDetail = svc.listingDetails(id);
		model.addAttribute("listingDetail", listingDetail);
		
		return "view3.html";
	}
	
	

	//TODO: Task 5


}
