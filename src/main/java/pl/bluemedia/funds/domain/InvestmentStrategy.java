package pl.bluemedia.funds.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class InvestmentStrategy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private StrategyType strategyType;

    @OneToMany(cascade = CascadeType.ALL)
    private List<InvestmentStrategyItem> items = new ArrayList<>();

    public InvestmentStrategy(StrategyType strategyType) {
        this.strategyType = strategyType;
        this.createdAt = LocalDateTime.now();
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

    public void addItem(InvestmentStrategyItem item) {
        items.add(item);
    }

    public void addItems(InvestmentStrategyItem... items) {
        this.items.addAll(Arrays.asList(items));
    }
}
