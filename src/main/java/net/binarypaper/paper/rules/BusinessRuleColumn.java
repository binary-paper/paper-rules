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
 * Annotation to mark a field in a class annotated with the BusinessRuleTable
 * annotation.
 * <p>
 * When lines from a rules file are converted to instances of the
 * BusinessRuleTable class the BusinessRuleColumn annotation will indicate which
 * columns in the rules file should be mapped to which fields in the
 * BusinessRuleTable class.
 * </p>
 * <p>
 * By default the header in the rules file will be the same as the field name.
 * </p>
 *
 * @author <a href="mailto:willy.gadney@binarypaper.net">Willy Gadney</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface BusinessRuleColumn {

    /**
     * The header of the column in rules file.
     * <p>
     * This is an alias for {@link #header}. For example
     * {@code @BusinessRuleColumn("Name")} is equivalent to
     * {@code @BusinessRuleColumn(header="Name")}.
     * <p>
     *
     * @return The column header in the rules file
     */
    String value() default "";

    /**
     * The header of the column in rules file.
     *
     * @return The column header in the rules file
     */
    String header() default "";

}
