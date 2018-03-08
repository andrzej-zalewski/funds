package pl.bluemedia.funds.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Fund {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private FundType type;
    private String name;

    public Fund(FundType type, String name) {
        this.type = type;
        this.name = name;
    }

    public enum FundType {
        POLISH("Polskie"),
        FOREIGN("Zagraniczne"),
        MONEY("Pieniężne");

        private String name;

        private FundType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
