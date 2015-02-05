package com.zoa.android.test;

import android.test.ComparisonFailure;

/**
 * Thrown when an assert equals for Strings failed.
 * 
 * @deprecated use junit.framework.ComparisonFailure
 */
@SuppressWarnings("serial")
public class TTComparisonFailure extends ComparisonFailure {
    public TTComparisonFailure(String message, String expected, String actual) {
        super(message, expected, actual);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
