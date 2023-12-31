package vttp2023.batch3.assessment.paf.bookings.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingDetail {
    private String id;
    private String description;
    private String address;
    private Integer price;
    private String amenities;
}
