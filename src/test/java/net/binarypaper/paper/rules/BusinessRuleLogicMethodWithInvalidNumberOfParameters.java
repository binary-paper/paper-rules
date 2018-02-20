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

/**
 * A test business rule class with a BusinessRuleLogic method with an invalid
 * number of parameters. A BusinessRuleLogic method must always have one
 * parameter.
 *
 * @author <a href="mailto:willy.gadney@binarypaper.net">Willy Gadney</a>
 */
@BusinessRuleTable
public class BusinessRuleLogicMethodWithInvalidNumberOfParameters {

    @BusinessRuleColumn
    private String min;

    @BusinessRuleColumn
    private String max;

    @BusinessRuleLogic
    public void businessRuleLogic() {

    }

}
