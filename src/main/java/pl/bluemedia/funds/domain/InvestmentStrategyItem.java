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
    private StrategyType strategyType;
    private FundType fundType;
    private Integer splitPercentage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public InvestmentStrategyItem(StrategyType strategyType, FundType fundType, Integer splitPercentage) {
        this.strategyType = strategyType;
        this.fundType = fundType;
        this.splitPercentage = splitPercentage;
    }

    public enum StrategyType {
        SAFE, BALANCED, AGRESSIVE;
        
        public static boolean contains(String value) {
            for (StrategyType type : values()) {
                if (type.name().equalsIgnoreCase(value)) {
                    return true;
                }
            }
            return false;
        }
    }
}
