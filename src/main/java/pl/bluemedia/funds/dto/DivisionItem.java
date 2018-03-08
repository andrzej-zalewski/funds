package pl.bluemedia.funds.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(Include.NON_NULL)
public class DivisionItem {

    private Integer id;
    private String type;
    private String name;
    private Integer value;
    private Double percent;
}
