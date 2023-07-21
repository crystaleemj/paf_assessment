package vttp2023.batch3.assessment.paf.bookings.services;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2023.batch3.assessment.paf.bookings.models.Listing;
import vttp2023.batch3.assessment.paf.bookings.models.ListingDetail;
import vttp2023.batch3.assessment.paf.bookings.repositories.ListingsRepository;

@Service
public class ListingsService {
	
	@Autowired
	ListingsRepository repo;
	
	//TODO: Task 2
	public List<String> listCountry(){
		return repo.listCountry();
	}
	
	//TODO: Task 3
	public List<Listing> listingsByCountry(String country, Integer person, Integer min, Integer max){
		List<Document> docList = repo.listingsByCountry(country, person, min, max);
		List<Listing> list = new ArrayList<>();
		for (Document document : docList) {
			Listing listing = new Listing();
			list.add(listing);
		}
		return list;
	}


	//TODO: Task 4
	public ListingDetail listingDetails(String id){
		Document doc = repo.listingDetails(id);
		String address = doc.getString("address.street");
		String address2 = doc.getString("address.suburb");
		String address3 = doc.getString("address.country");
		String addressCombine = address + address2 + address3;
		ListingDetail listingDetail = new ListingDetail(doc.getObjectId("_id").toString(), doc.getString("description"), addressCombine, doc.getInteger("price"), doc.getString("amenities"));

		return listingDetail;
		}

		
	
	

	//TODO: Task 5


}
