package pl.bluemedia.funds.service;

import java.util.EnumSet;
import java.util.Optional;

import org.assertj.core.util.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.bluemedia.funds.domain.Fund;
import pl.bluemedia.funds.domain.InvestmentStrategy;
import pl.bluemedia.funds.domain.InvestmentStrategyItem.StrategyType;
import pl.bluemedia.funds.domain.InvestmentStrategyItem;
import pl.bluemedia.funds.domain.Fund.FundType;
import pl.bluemedia.funds.dto.Division;
import pl.bluemedia.funds.exception.UnknownStrategyTypeException;
import pl.bluemedia.funds.repository.FundRepository;
import pl.bluemedia.funds.repository.InvestmentStrategyItemRepository;
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

        fundRepository.save(new Fund(FundType.POLISH, "Fundusz Polski 1"));
        fundRepository.save(new Fund(FundType.POLISH, "Fundusz Polski 1"));
        fundRepository.save(new Fund(FundType.FOREIGN, "Fundusz Zagraniczny 1"));
        fundRepository.save(new Fund(FundType.FOREIGN, "Fundusz Zagraniczny 2"));
        fundRepository.save(new Fund(FundType.FOREIGN, "Fundusz Zagraniczny 3"));
        fundRepository.save(new Fund(FundType.MONEY, "Fundusz Pieniężny 1"));

        InvestmentStrategy safe = new InvestmentStrategy();
        safe.getItems().add(new InvestmentStrategyItem(StrategyType.SAFE, FundType.POLISH, 20));
        safe.getItems().add(new InvestmentStrategyItem(StrategyType.SAFE, FundType.FOREIGN, 75));
        safe.getItems().add(new InvestmentStrategyItem(StrategyType.SAFE, FundType.MONEY, 5));
        
        investmentStrategyRepository.save(safe);
        
        InvestmentStrategy balanced = new InvestmentStrategy();
        balanced.getItems().add(new InvestmentStrategyItem(StrategyType.BALANCED, FundType.POLISH, 30));
        balanced.getItems().add(new InvestmentStrategyItem(StrategyType.BALANCED, FundType.FOREIGN, 60));
        balanced.getItems().add(new InvestmentStrategyItem(StrategyType.BALANCED, FundType.MONEY, 10));
        
        investmentStrategyRepository.save(balanced);
        
        InvestmentStrategy aggressive = new InvestmentStrategy();
        aggressive.getItems().add(new InvestmentStrategyItem(StrategyType.AGRESSIVE, FundType.POLISH, 40));
        aggressive.getItems().add(new InvestmentStrategyItem(StrategyType.AGRESSIVE, FundType.FOREIGN, 20));
        aggressive.getItems().add(new InvestmentStrategyItem(StrategyType.AGRESSIVE, FundType.MONEY, 40));
        
        investmentStrategyRepository.save(aggressive);
        
//        Optional<InvestmentStrategy> foundInvestmentStrategy = investmentStrategyRepository.findById(savedInvestmentStrategy.getId());
//        
//        for (InvestmentStrategyItem item : foundInvestmentStrategy.get().getItems()) {
//            System.out.println(item.getSplitPercentage());
//        }
        
        

        Division division = new Division();

        return division;
    }
}
