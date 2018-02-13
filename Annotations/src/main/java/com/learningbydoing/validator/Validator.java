/**
 *
 */
package com.learningbydoing.validator;

/**
 * @author Maddipatla Chandra Babu
 * @date 08-Feb-2018
 */
public interface Validator {

    boolean isAllDocumentsValid();

    boolean isDocumentsContainAadhaar();

    boolean isAllDocumentsHaveConsistentDataAgainstAadhaar();

}