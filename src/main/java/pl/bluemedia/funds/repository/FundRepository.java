package pl.bluemedia.funds.repository;

import org.springframework.data.repository.CrudRepository;

import pl.bluemedia.funds.domain.Fund;

public interface FundRepository extends CrudRepository<Fund, Long> {}
