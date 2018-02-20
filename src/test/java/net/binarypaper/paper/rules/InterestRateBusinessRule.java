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

import java.math.BigDecimal;
import lombok.Data;

/**
 * A simple example business rule to calculate the interest rate based on the
 * income range.
 *
 * @author
 * <a href="mailto:willy.gadney@binarypaper.net">Willy Gadney</a>
 */
@BusinessRuleTable
@Data
public class InterestRateBusinessRule {

    @BusinessRuleColumn
    private BigDecimal minimumIncome;

    @BusinessRuleColumn("maximumIncome")
    private BigDecimal maximumIncome;

    @BusinessRuleColumn(header = "Interest Rate")
    private BigDecimal interestRate;

    @BusinessRuleLogic
    public void businessRuleLogic(Client client) {
        // If minimumIncome <= client.getIncome()
        // and maximumIncome > client.getIncome()
        // then client interest rate = interestRate
        if ((minimumIncome.compareTo(client.getIncome()) == 0)
                || ((minimumIncome.compareTo(client.getIncome()) == -1)
                && (maximumIncome.compareTo(client.getIncome()) == 1))) {
            client.setInterestRate(interestRate);
        }
    }

}
