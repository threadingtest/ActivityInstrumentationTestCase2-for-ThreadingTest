package com.zoa.android.test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.zoa.writeToMQ.UnitTest;

import android.test.ActivityTestCase;
import android.util.Log;

public abstract class TTActivityTestCase extends ActivityTestCase {
	private UnitTest zoa_t = null;
    
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
    	String fName = getName();			//	get the method name
	    assertNotNull(fName);				//	Determine whether to succeed
	    Method method = null;
	    try {
	        method = getClass().getMethod(fName, (Class[]) null);	// get the method
	    } catch (NoSuchMethodException e) {
	        fail("Method \""+fName+"\" not found");	// get method fail
	    }

	    if (!Modifier.isPublic(method.getModifiers())) {
	        fail("Method \""+fName+"\" should be public"); // the method not public
	    }
    	String cName = this.getClass().getName();	// get the class of the method(include package name)
    	cName = cName.replace('.', '/');	// change "." to "/"
    	cName += "/";
    	cName += fName;						// Join the method name
    	zoa_t = new UnitTest(cName);	// call TT Unit Test
	    Log.v("TTActivityTestCase", "start");
    }
    
    @Override
    protected void tearDown() throws Exception {
        if(zoa_t != null)
        {
        	Log.v("TTActivityTestCase", "close");
        	zoa_t.close();		// close TT Unit Test
        }
        else
        {
        	Log.e("TTActivityTestCase", "close error");
        }
        super.tearDown();
    }
}
