package pl.bluemedia.funds.repository;

import org.springframework.data.repository.CrudRepository;

import pl.bluemedia.funds.domain.InvestmentStrategyItem;

public interface InvestmentStrategyItemRepository extends CrudRepository<InvestmentStrategyItem, Long> {}
