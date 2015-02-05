package com.zoa.android.test;

import android.test.LoaderTestCase;
import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import com.zoa.writeToMQ.UnitTest;

public class TTLoaderTestCase extends LoaderTestCase {
	private UnitTest zoa_t = null;
	private boolean needRun = false;
    
    @Override
    protected void setUp() throws Exception {
    	String cName = this.getClass().getName();	// get the class of the method(include package name)
	    assertNotNull(cName);				//	Determine whether to succeed
	    if(cName != "com.zoa.android.test.TTLoaderTestCase" && !cName.equals("com.zoa.android.test.TTLoaderTestCase"))
	    {
	    	needRun = true;
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
	    	cName = cName.replace('.', '/');	// change "." to "/"
	    	cName += "/";
	    	cName += fName;						// Join the method name
	    	zoa_t = new UnitTest(cName);	// call TT Unit Test
		    Log.v("TTLoaderTestCase", "start");
	    }
	    else 
	    {
	    	needRun = false;
	    }
    }
    
    @Override
    protected void tearDown() throws Exception {
    	if(needRun)
    	{
	        if(zoa_t != null)
	        {
	        	Log.v("TTLoaderTestCase", "close");
	        	zoa_t.close();		// close TT Unit Test
	        }
	        else
	        {
	        	Log.e("TTLoaderTestCase", "close error");
	        }
	        super.tearDown();
    	}
    }
}
