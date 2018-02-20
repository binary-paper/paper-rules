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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a method in a class annotated with the BusinessRuleTable
 * annotation in order to indicate that the method must be called to fire the
 * business rule on the business rule row.
 * <p>
 * The annotated method must take an instance of the class T as specified in the
 * BusinessRule<R, T> business rule instance.
 * </p>
 *
 * @author <a href="mailto:willy.gadney@binarypaper.net">Willy Gadney</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface BusinessRuleLogic {

    /**
     * Flag to indicate whether the business rule must be applied to all records
     * in the business rule file or if the business rule execution will stop
     * after the first match in the business rule file. If this flag is enabled
     * the performance of the business rule can be improved by not firing the
     * business rule logic for all rows in the business rules file.
     * <p>
     * If the singleMatch = <b>false</b> (default) then the business rule will
     * be applied to all records in the business rule file.
     * </p>
     * <p>
     * If the singleMatch = <b>true</b> then the business rule execution will
     * stop after the first rule matched successfully. If this flag is enabled
     * the BusinessRuleLogic method must return a boolean where true indicates a
     * match was found and false indicates that the business rule row did not
     * match.
     * </p>
     *
     * @return The singleMatch flag
     */
    boolean singleMatch() default false;

}
