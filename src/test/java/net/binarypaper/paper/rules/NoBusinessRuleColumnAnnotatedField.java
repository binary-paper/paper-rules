/*
 * Copyright 2018 William Gadney.
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

import lombok.Data;

/**
 * A business rule class with no fields annotated with the BusinessRuleColumn
 * annotation.
 *
 * @author <a href="mailto:willy.gadney@binarypaper.net">Willy Gadney</a>
 */
@BusinessRuleTable
@Data
public class NoBusinessRuleColumnAnnotatedField {

    @BusinessRuleLogic
    public void businessRuleLogic(Client client) {
        // Do nothing
    }

}
