package pl.bluemedia.funds.repository;

import org.springframework.data.repository.CrudRepository;

import pl.bluemedia.funds.domain.InvestmentStrategy;

public interface InvestmentStrategyRepository extends CrudRepository<InvestmentStrategy, Long> {}
