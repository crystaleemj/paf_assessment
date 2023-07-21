package vttp2023.batch3.assessment.paf.bookings.repositories;


import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class ListingsRepository {

	@Autowired
	MongoTemplate template;

	//TODO: Task 2
	// db.getCollection('listings').aggregate(
	// 	[
	// 	  {
	// 		$group: {
	// 		  _id: '$address.country_code',
	// 		  Country: { $first: '$address.country' }
	// 		}
	// 	  },
	// 	  { $sort: { Country: 1 } },
	// 	  { $project: { _id: 0, Country: 1 } }
	// 	],
	// 	{ maxTimeMS: 60000, allowDiskUse: true }
	//   );

		
	public List<String> listCountry(){
		GroupOperation group = Aggregation.group("address.country_code")
			.first("address.country").as("Country");
		SortOperation sort = Aggregation.sort(Sort.Direction.ASC, "Country");
		ProjectionOperation project = Aggregation.project("Country").andExclude("_id");

		Aggregation pipeline = Aggregation.newAggregation(group, sort, project);
		List<Document> list = template.aggregate(pipeline, "listings", Document.class).getMappedResults();
		
		List<String> countries = new ArrayList<>();
		for (Document document : list) {
			String countryName = document.getString("Country");
			countries.add(countryName);
		}

		return countries;
	}

	
	// 	db.getCollection('listings').aggregate(
	//   [
	//     { $match: { 'address.country': 'Canada' } },
	//     { $match: { accommodates: 2 } },
	//     { $match: { price: { $gt: 0, $lt: 100 } } },
	//     {
	//       $project: {
	//         _id: 1,
	//         address.street: 1,
	//         price: 1,
	//         'images.picture_url': 1
	//       }
	//     },
	//     { $sort: { price: -1 } }
	//   ],
	//   { maxTimeMS: 60000, allowDiskUse: true }
	// );

	//TODO: Task 3
	public List<Document> listingsByCountry(String country, Integer person, Integer min, Integer max){
		MatchOperation match = Aggregation.match(Criteria.where("address.country").is(country));
		MatchOperation match2 = Aggregation.match(Criteria.where("accommodates").is(person));
		MatchOperation match3 = Aggregation.match(Criteria.where("price").gt(min));
		MatchOperation match4 = Aggregation.match(Criteria.where("price").lt(max));
		ProjectionOperation project = Aggregation.project("_id", "address.street", "price", "images.picture_url");
		SortOperation sort = Aggregation.sort(Sort.Direction.DESC, "price");

		Aggregation pipeline = Aggregation.newAggregation(match, match2, match3, match4, project, sort);
		return template.aggregate(pipeline, "listings", Document.class).getMappedResults();

	}


	//TODO: Task 4
	// 	db.getCollection('listings').aggregate(
	//   [
	//     { $match: { _id: '27498126' } },
	//     {
	//       $project: {
	//         _id: 1,
	//         description: 1,
	//         'address.street': 1,
	//			'address.suburb': 1,
	//			'address.country': 1,
	//         'images.picture_url': 1,
	//         price: 1,
	//         amenities: 1
	//       }
	//     }
	//   ],
	//   { maxTimeMS: 60000, allowDiskUse: true }
	// );
	
	public Document listingDetails(String id){
		MatchOperation match = Aggregation.match(Criteria.where("_id").is(id));
		ProjectionOperation project = Aggregation.project("id", "description", "address.street", "address.suburb", "address.country", "images.picture_url", "price", "amenities");

		Aggregation pipeline = Aggregation.newAggregation(match, project);
		return template.aggregate(pipeline, "listings", Document.class).getUniqueMappedResult();
	}
	


	//TODO: Task 5


}
