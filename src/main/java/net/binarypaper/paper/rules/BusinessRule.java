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

import java.io.IOException;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * The main driver for the paper-rules rules engine.
 *
 * @author <a href="mailto:willy.gadney@binarypaper.net">Willy Gadney</a>
 * @param <T> The class with data to which the business rule will be applied.
 */
public class BusinessRule<T> {

    private final List businessRuleRows = new ArrayList<>();
    private Method businessRuleLogic;
    private boolean singleMatch;

    /**
     * Construct a new business rule instance.
     *
     * @param businessRuleClass the business rule implementation class.
     * @param reader A java.io.Reader of the business rule file.
     */
    public BusinessRule(Class businessRuleClass, Reader reader) {
        // Validate the the businessRuleClass is annotated with @BusinessRuleTable
        Annotation annotation = businessRuleClass.getAnnotation(BusinessRuleTable.class);
        if (annotation == null) {
            throw new BusinessRuleClassException("The businessRuleClass must be annotated with @BusinessRuleTable");
        }
        // Get the businessRuleLogic Method
        List<Method> methods = Arrays.asList(businessRuleClass.getDeclaredMethods());
        for (Method method : methods) {
            BusinessRuleLogic businessRuleLogicAnnotation = method.getAnnotation(BusinessRuleLogic.class);
            if (businessRuleLogicAnnotation != null) {
                businessRuleLogic = method;
                singleMatch = businessRuleLogicAnnotation.singleMatch();
                break;
            }
        }
        if (businessRuleLogic == null) {
            throw new BusinessRuleClassException("The businessRuleClass must have a method annotated with @BusinessRuleLogic");
        } else {
            int parameterCount = businessRuleLogic.getParameterCount();
            if (parameterCount != 1) {
                throw new BusinessRuleClassException("The businessRuleClass BusinessRuleLogic method must only take one parameter of type T");
            }
            // ToDo: Validate the data type of the BusinessRuleLogic method parameter to be of class T
        }
        // Declare a fieldMap that will map rules file columns columns to businessRuleClass fields
        List<String> ruleFileColumns = new ArrayList<>();
        List<Field> ruleFields = new ArrayList<>();
        // Get the fields from the businessRuleClass
        List<Field> fields = Arrays.asList(businessRuleClass.getDeclaredFields());
        for (Field field : fields) {
            BusinessRuleColumn businessRuleColumn = field.getAnnotation(BusinessRuleColumn.class);
            if (businessRuleColumn != null) {
                String rulesFileColumn = field.getName();
                if (!businessRuleColumn.value().isEmpty()) {
                    rulesFileColumn = businessRuleColumn.value();
                }
                if (!businessRuleColumn.header().isEmpty()) {
                    rulesFileColumn = businessRuleColumn.header();
                }
                ruleFileColumns.add(rulesFileColumn);
                ruleFields.add(field);
                field.setAccessible(true);
            }
        }
        // Parse the rules file with Apache Commons CSV
        try {
            // Read the file bytes with Apache Commons CSV
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(reader);
            // Loop through the records of the CSV file
            for (CSVRecord record : records) {
                Object businessRule = businessRuleClass.newInstance();
                for (int i = 0; i < ruleFileColumns.size(); i++) {
                    if (!record.get(ruleFileColumns.get(i)).trim().isEmpty()) {
//                        String businessRuleFieldValue = record.get(ruleFileColumns.get(i)).trim();
                        BigDecimal businessRuleFieldValue = new BigDecimal(record.get(ruleFileColumns.get(i)).trim());
                        System.out.println(record.get(ruleFileColumns.get(i) + ":" + ruleFileColumns.get(i)).trim() + "-->" + businessRuleFieldValue);
//                        ruleFields.get(i).set(businessRule, businessRuleFieldValue);
                    }
                }
            }
            reader.close();
        } catch (IOException ex) {
            throw new BusinessRuleFileException("The business rules file could not be read");
        } catch (InstantiationException ex) {
            throw new BusinessRuleClassException("An instance of the businessRuleClass could not be instantiated");
        } catch (IllegalAccessException ex) {
            throw new BusinessRuleClassException("An businessRuleClass values could not be set");
//        } catch (IllegalArgumentException ex) {
//            throw new BusinessRuleFileException("The values of the business rules file could not be converted to objects of class " + businessRuleClass.getName());
        }
    }

    public void fire(T t) {
        for (Object businessRuleRow : businessRuleRows) {
            try {
                businessRuleLogic.invoke(businessRuleRow, t);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(BusinessRule.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(BusinessRule.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(BusinessRule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
