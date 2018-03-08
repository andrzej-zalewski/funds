package pl.bluemedia.funds.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import pl.bluemedia.funds.domain.Fund.FundType;

@Data
@Entity
public class InvestmentStrategyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private FundType fundType;
    private Integer splitPercentage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public InvestmentStrategyItem(FundType fundType, Integer splitPercentage) {
        this.fundType = fundType;
        this.splitPercentage = splitPercentage;
        this.createdAt = LocalDateTime.now();
    }

    public static InvestmentStrategyItem of(FundType fundType, Integer splitPercentage) {
        return new InvestmentStrategyItem(fundType, splitPercentage);
    }
}
