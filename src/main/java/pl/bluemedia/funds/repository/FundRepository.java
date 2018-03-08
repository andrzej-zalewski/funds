package pl.bluemedia.funds.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.bluemedia.funds.domain.Fund;
import pl.bluemedia.funds.domain.Fund.FundType;

public interface FundRepository extends CrudRepository<Fund, Long> {

    public List<Fund> findByType(FundType fundType);
}
