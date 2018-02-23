/*
 * Copyright 2018 <a href="mailto:willy.gadney@binarypaper.net">Willy Gadney</a>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.binarypaper.paper.rules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author <a href="mailto:willy.gadney@binarypaper.net">Willy Gadney</a>
 */
public class BusinessRuleTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void businessRuleClassWithoutAnnotation() throws FileNotFoundException {
        File rulesFile = new File("src/test/resources/InterestRateBusinessRule.csv");
        Reader rulesFileReader = new FileReader(rulesFile);
        thrown.expect(BusinessRuleClassException.class);
        thrown.expectMessage("The businessRuleClass must be annotated with @BusinessRuleTable");
        BusinessRule<Client> businessRule = new BusinessRule<>(String.class, rulesFileReader);
    }

    @Test
    public void businessRuleColumnFieldWithValueAndHeader() throws FileNotFoundException {
        File rulesFile = new File("src/test/resources/InterestRateBusinessRule.csv");
        Reader rulesFileReader = new FileReader(rulesFile);
        thrown.expect(BusinessRuleClassException.class);
        thrown.expectMessage("The business rule column someField @BusinessRuleColumn annotation may declare either a value or a header but not both");
        BusinessRule<Client> businessRule = new BusinessRule<>(BusinessRuleColumnFieldWithValueAndHeader.class, rulesFileReader);
    }

    @Test
    public void noBusinessRuleColumnAnnotatedField() throws FileNotFoundException {
        File rulesFile = new File("src/test/resources/InterestRateBusinessRule.csv");
        Reader rulesFileReader = new FileReader(rulesFile);
        thrown.expect(BusinessRuleClassException.class);
        thrown.expectMessage("The business rule class net.binarypaper.paper.rules.NoBusinessRuleColumnAnnotatedField "
                + "does not have any fields annotated with @BusinessRuleColumn");
        BusinessRule<Client> businessRule = new BusinessRule<>(NoBusinessRuleColumnAnnotatedField.class, rulesFileReader);
    }

    @Test
    public void businessRuleClassWithoutBusinessRuleLogicMethod() throws FileNotFoundException {
        File rulesFile = new File("src/test/resources/InterestRateBusinessRule.csv");
        Reader rulesFileReader = new FileReader(rulesFile);
        thrown.expect(BusinessRuleClassException.class);
        thrown.expectMessage("The businessRuleClass must have a method annotated with @BusinessRuleLogic");
        BusinessRule<Client> businessRule = new BusinessRule<>(BusinessRuleClassWithoutBusinessRuleLogicMethod.class, rulesFileReader);
    }

    @Test
    public void businessRuleLogicMethodWithInvalidNumberOfParameters() throws FileNotFoundException {
        File rulesFile = new File("src/test/resources/InterestRateBusinessRule.csv");
        Reader rulesFileReader = new FileReader(rulesFile);
        thrown.expect(BusinessRuleClassException.class);
        thrown.expectMessage("The businessRuleClass BusinessRuleLogic method must only take one parameter of type T");
        BusinessRule<Client> businessRule = new BusinessRule<>(BusinessRuleLogicMethodWithInvalidNumberOfParameters.class, rulesFileReader);
    }

    @Test
    public void interestRateBusinessRule() throws FileNotFoundException {
        File rulesFile = new File("src/test/resources/InterestRateBusinessRule.csv");
        Reader rulesFileReader = new FileReader(rulesFile);
        BusinessRule<Client> businessRule = new BusinessRule<>(InterestRateBusinessRule.class, rulesFileReader);
        Client client = new Client();
        client.setIncome(new BigDecimal(1500));
        Assert.assertNull(client.getInterestRate());
        businessRule.fire(client);
        Assert.assertEquals(new BigDecimal(9), client.getInterestRate());
    }

}
