package vttp2023.batch3.assessment.paf.bookings.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search {

    @NotNull (message = "Please select a country")
    private String country;
    
    @Min (value = 1)
    @Max (value = 10)
    private Integer person;

    @Min (value = 1)
    @Max (value = 10000)
    private Integer min;

    @Min (value = 1)
    @Max (value = 10000)
    private Integer max;
}
