package pl.bluemedia.funds.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Division {

    private List<DivisionItem> items = new ArrayList<>();
    private Integer change;
}
