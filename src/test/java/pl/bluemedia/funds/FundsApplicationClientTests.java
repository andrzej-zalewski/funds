package pl.bluemedia.funds;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.bluemedia.funds.domain.Fund;
import pl.bluemedia.funds.domain.Fund.FundType;
import pl.bluemedia.funds.domain.InvestmentStrategy;
import pl.bluemedia.funds.domain.InvestmentStrategy.StrategyType;
import pl.bluemedia.funds.domain.InvestmentStrategyItem;
import pl.bluemedia.funds.dto.Division;
import pl.bluemedia.funds.repository.FundRepository;
import pl.bluemedia.funds.repository.InvestmentStrategyRepository;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FundsApplicationClientTests {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private InvestmentStrategyRepository investmentStrategyRepository;

    private JacksonTester<Division> jacksonTester;

    @Before
    public void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        
        populateInvestmentStrategies();
    }

    @Test
    public void safeStrategyReturnsDivision() throws Exception {

        // given
        populateInMemoryDatabaseSafe1();

        // when
        ResponseEntity<Division> division = template.getForEntity("/funds/safe/10000", Division.class);

        // then
        assertThat(division.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jacksonTester.write(division.getBody())).isEqualToJson("safe1.json", JSONCompareMode.STRICT_ORDER);
    }

    @Test
    public void safeStrategyReturnsDivisionWithChange() throws Exception {

        // given
        populateInMemoryDatabaseSafe1();

        // when
        ResponseEntity<Division> division = template.getForEntity("/funds/safe/10001", Division.class);

        // then
        assertThat(division.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jacksonTester.write(division.getBody())).isEqualToJson("safe2.json", JSONCompareMode.STRICT_ORDER);
    }

    @Test
    public void safeStrategyReturnsDivisionWithModuloPartAddedToFirstItemOfAGroup() throws Exception {

        // given
        populateInMemoryDatabaseSafe3();

        // when
        ResponseEntity<Division> division = template.getForEntity("/funds/safe/10000", Division.class);

        // then
        assertThat(division.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jacksonTester.write(division.getBody())).isEqualToJson("safe3.json", JSONCompareMode.STRICT_ORDER);
    }

    private void populateInMemoryDatabaseSafe1() {
        fundRepository.save(Fund.of(FundType.POLISH, "Fundusz Polski 1"));
        fundRepository.save(Fund.of(FundType.POLISH, "Fundusz Polski 2"));
        fundRepository.save(Fund.of(FundType.FOREIGN, "Fundusz Zagraniczny 1"));
        fundRepository.save(Fund.of(FundType.FOREIGN, "Fundusz Zagraniczny 2"));
        fundRepository.save(Fund.of(FundType.FOREIGN, "Fundusz Zagraniczny 3"));
        fundRepository.save(Fund.of(FundType.MONEY, "Fundusz Pieniężny 1"));
    }

    private void populateInMemoryDatabaseSafe3() {
        fundRepository.save(Fund.of(FundType.POLISH, "Fundusz Polski 1"));
        fundRepository.save(Fund.of(FundType.POLISH, "Fundusz Polski 2"));
        fundRepository.save(Fund.of(FundType.POLISH, "Fundusz Polski 3"));
        fundRepository.save(Fund.of(FundType.FOREIGN, "Fundusz Zagraniczny 1"));
        fundRepository.save(Fund.of(FundType.FOREIGN, "Fundusz Zagraniczny 2"));
        fundRepository.save(Fund.of(FundType.MONEY, "Fundusz Pieniężny 1"));
    }

    private void populateInvestmentStrategies() {
        InvestmentStrategy safe = new InvestmentStrategy(StrategyType.SAFE);
        safe.addItems(InvestmentStrategyItem.of(FundType.POLISH, 20),
                      InvestmentStrategyItem.of(FundType.FOREIGN, 75),
                      InvestmentStrategyItem.of(FundType.MONEY, 5));
        
        investmentStrategyRepository.save(safe);
        
        InvestmentStrategy balanced = new InvestmentStrategy(StrategyType.BALANCED);
        balanced.addItems(InvestmentStrategyItem.of(FundType.POLISH, 30),
                          InvestmentStrategyItem.of(FundType.FOREIGN, 60),
                          InvestmentStrategyItem.of(FundType.MONEY, 10));
        
        investmentStrategyRepository.save(balanced);
        
        InvestmentStrategy aggressive = new InvestmentStrategy(StrategyType.AGRESSIVE);
        aggressive.addItems(InvestmentStrategyItem.of(FundType.POLISH, 40),
                            InvestmentStrategyItem.of(FundType.FOREIGN, 20),
                            InvestmentStrategyItem.of(FundType.MONEY, 40));
        
        investmentStrategyRepository.save(aggressive);
    }
}