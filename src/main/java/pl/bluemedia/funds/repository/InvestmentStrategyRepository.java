package pl.bluemedia.funds.repository;

import org.springframework.data.repository.CrudRepository;

import pl.bluemedia.funds.domain.InvestmentStrategy;
import pl.bluemedia.funds.domain.InvestmentStrategy.StrategyType;

public interface InvestmentStrategyRepository extends CrudRepository<InvestmentStrategy, Long> {
    
    public InvestmentStrategy findTopByStrategyTypeOrderByCreatedAtDesc(StrategyType strategyType);
}
