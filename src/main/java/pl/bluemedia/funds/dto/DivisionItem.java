package pl.bluemedia.funds.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class DivisionItem {

    private String id;
    private String type;
    private String name;
    private Integer value;
    private Double percent;
}
