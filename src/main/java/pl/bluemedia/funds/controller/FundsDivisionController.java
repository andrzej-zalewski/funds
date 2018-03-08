package pl.bluemedia.funds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.bluemedia.funds.dto.Division;
import pl.bluemedia.funds.service.FundsDivisionService;

@RestController
@RequestMapping("/funds")
public class FundsDivisionController {
    
    @Autowired
    private FundsDivisionService fundsDivisionService;

    @GetMapping(path = "/{strategy}/{value}")
    public Division getDivision(@PathVariable String strategy, @PathVariable Integer value) {
        return fundsDivisionService.calculateDivision(strategy, value);
    }
}
