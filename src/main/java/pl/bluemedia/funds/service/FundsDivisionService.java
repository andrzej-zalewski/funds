package pl.bluemedia.funds.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import pl.bluemedia.funds.domain.Fund;
import pl.bluemedia.funds.domain.InvestmentStrategy;
import pl.bluemedia.funds.domain.InvestmentStrategy.StrategyType;
import pl.bluemedia.funds.domain.InvestmentStrategyItem;
import pl.bluemedia.funds.dto.Division;
import pl.bluemedia.funds.dto.DivisionItem;
import pl.bluemedia.funds.exception.UnknownStrategyTypeException;
import pl.bluemedia.funds.repository.FundRepository;
import pl.bluemedia.funds.repository.InvestmentStrategyRepository;

@Service
public class FundsDivisionService {

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private InvestmentStrategyRepository investmentStrategyRepository;

    public Division calculateDivision(String strategy, Integer value) {

        if (!StrategyType.contains(strategy)) {
            throw new UnknownStrategyTypeException();
        }

        StrategyType strategyType = StrategyType.valueOf(strategy.toUpperCase());
        InvestmentStrategy strategyFound = investmentStrategyRepository.findTopByStrategyTypeOrderByCreatedAtDesc(strategyType);

        Division division = new Division();

        int sequence = 1;
        List<Integer> totalDivision = Lists.newArrayList();
        for (InvestmentStrategyItem item : strategyFound.getItems()) {
            int percentage = (value * item.getSplitPercentage()) / 100;

            totalDivision.add(percentage);

            List<Fund> funds = fundRepository.findByType(item.getFundType());
            int percentageByFund = percentage / funds.size();
            int moduloOfPercentageByFund = percentage % funds.size();
            boolean moduloApplied = false;

            for (Fund fund : funds) {
                int percentageByFundValue = percentageByFund;
                if (moduloOfPercentageByFund > 0 && !moduloApplied) {
                    percentageByFundValue += moduloOfPercentageByFund;
                    moduloApplied = true;
                }
                
                division.getItems().add(DivisionItem.builder()
                        .id(sequence++)
                        .name(fund.getName())
                        .type(item.getFundType().getName())
                        .percent(((double) percentageByFundValue) / 100)
                        .value(percentageByFundValue)
                        .build());
            }
        }

        int firstDivisionSum = totalDivision.stream().mapToInt(Integer::intValue).sum();
        division.setChange(value - firstDivisionSum);

        return division;
    }
}
