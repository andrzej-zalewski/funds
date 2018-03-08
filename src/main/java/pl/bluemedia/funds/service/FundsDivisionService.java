package pl.bluemedia.funds.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import pl.bluemedia.funds.domain.Fund;
import pl.bluemedia.funds.domain.Fund.FundType;
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

        populateInMemoryDatabaseSafe1();
//        populateInMemoryDatabaseSafe3();

        StrategyType strategyType = StrategyType.valueOf(strategy.toUpperCase());
        InvestmentStrategy strategyFound = investmentStrategyRepository.findTopByStrategyTypeOrderByCreatedAtDesc(strategyType);

        Division division = new Division();

        List<Integer> firstDivision = Lists.newArrayList();
        for (InvestmentStrategyItem item : strategyFound.getItems()) {
            int percentage = (value * item.getSplitPercentage()) / 100;
            
            firstDivision.add(percentage);

            List<Fund> funds = fundRepository.findByType(item.getFundType());
            int percentageByFund = percentage / funds.size();
            int moduloOfPercentageByFund = percentage % funds.size();
            boolean moduloApplied = false;
            
            for (Fund fund : funds) {
                DivisionItem divisionItem = new DivisionItem();
                divisionItem.setId(UUID.randomUUID().toString());
                divisionItem.setName(fund.getName());
                divisionItem.setType(item.getFundType().getName());
                
                int percentageByFundValue = percentageByFund;
                if (moduloOfPercentageByFund > 0 && !moduloApplied) {
                    percentageByFundValue += moduloOfPercentageByFund;
                    moduloApplied = true;
                }
                divisionItem.setPercent(((double) percentageByFundValue) / 100);
                divisionItem.setValue(percentageByFundValue);

                division.getItems().add(divisionItem);
            }
        }

        int firstDivisionSum = firstDivision.stream().mapToInt(Integer::intValue).sum();
        division.setChange(value - firstDivisionSum);

        return division;
    }

    private void populateInMemoryDatabaseSafe1() {
        fundRepository.save(Fund.of(FundType.POLISH, "Fundusz Polski 1"));
        fundRepository.save(Fund.of(FundType.POLISH, "Fundusz Polski 2"));
        fundRepository.save(Fund.of(FundType.FOREIGN, "Fundusz Zagraniczny 1"));
        fundRepository.save(Fund.of(FundType.FOREIGN, "Fundusz Zagraniczny 2"));
        fundRepository.save(Fund.of(FundType.FOREIGN, "Fundusz Zagraniczny 3"));
        fundRepository.save(Fund.of(FundType.MONEY, "Fundusz Pieniężny 1"));

        InvestmentStrategy safe = new InvestmentStrategy(StrategyType.SAFE);
        safe.addItems(InvestmentStrategyItem.of(FundType.POLISH, 20),
                      InvestmentStrategyItem.of(FundType.FOREIGN, 75),
                      InvestmentStrategyItem.of(FundType.MONEY, 5));
        
        investmentStrategyRepository.save(safe);
        
        InvestmentStrategy balanced = new InvestmentStrategy(StrategyType.BALANCED);
        balanced.addItems(InvestmentStrategyItem.of(FundType.POLISH, 30),
                          InvestmentStrategyItem.of(FundType.FOREIGN, 60),
                          InvestmentStrategyItem.of(FundType.MONEY, 10));
        
        investmentStrategyRepository.save(balanced);
        
        InvestmentStrategy aggressive = new InvestmentStrategy(StrategyType.AGRESSIVE);
        aggressive.addItems(InvestmentStrategyItem.of(FundType.POLISH, 40),
                            InvestmentStrategyItem.of(FundType.FOREIGN, 20),
                            InvestmentStrategyItem.of(FundType.MONEY, 40));
        
        investmentStrategyRepository.save(aggressive);
    }

    private void populateInMemoryDatabaseSafe3() {
        fundRepository.save(Fund.of(FundType.POLISH, "Fundusz Polski 1"));
        fundRepository.save(Fund.of(FundType.POLISH, "Fundusz Polski 2"));
        fundRepository.save(Fund.of(FundType.POLISH, "Fundusz Polski 3"));
        fundRepository.save(Fund.of(FundType.FOREIGN, "Fundusz Zagraniczny 1"));
        fundRepository.save(Fund.of(FundType.FOREIGN, "Fundusz Zagraniczny 2"));
        fundRepository.save(Fund.of(FundType.MONEY, "Fundusz Pieniężny 1"));

        InvestmentStrategy safe = new InvestmentStrategy(StrategyType.SAFE);
        safe.addItems(InvestmentStrategyItem.of(FundType.POLISH, 20),
                      InvestmentStrategyItem.of(FundType.FOREIGN, 75),
                      InvestmentStrategyItem.of(FundType.MONEY, 5));
        
        investmentStrategyRepository.save(safe);
        
        InvestmentStrategy balanced = new InvestmentStrategy(StrategyType.BALANCED);
        balanced.addItems(InvestmentStrategyItem.of(FundType.POLISH, 30),
                          InvestmentStrategyItem.of(FundType.FOREIGN, 60),
                          InvestmentStrategyItem.of(FundType.MONEY, 10));
        
        investmentStrategyRepository.save(balanced);
        
        InvestmentStrategy aggressive = new InvestmentStrategy(StrategyType.AGRESSIVE);
        aggressive.addItems(InvestmentStrategyItem.of(FundType.POLISH, 40),
                            InvestmentStrategyItem.of(FundType.FOREIGN, 20),
                            InvestmentStrategyItem.of(FundType.MONEY, 40));
        
        investmentStrategyRepository.save(aggressive);
    }
}
