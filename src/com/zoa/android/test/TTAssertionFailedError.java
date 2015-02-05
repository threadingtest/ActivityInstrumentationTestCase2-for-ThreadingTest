package com.zoa.android.test;

import android.test.AssertionFailedError;

@SuppressWarnings({ "serial", "deprecation" })
@Deprecated
public class TTAssertionFailedError extends AssertionFailedError {
   
    public TTAssertionFailedError() {
    }
    
    public TTAssertionFailedError(String errorMessage) {
        super(errorMessage);
    }
}
