package com.zoa.android.test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.zoa.writeToMQ.UnitTest;

import android.content.ContentProvider;
import android.test.ProviderTestCase;
import android.util.Log;

@SuppressWarnings({ "rawtypes", "deprecation" })
@Deprecated
public abstract class TTProviderTestCase<T extends ContentProvider>
       extends ProviderTestCase {

    @SuppressWarnings("unchecked")
	public TTProviderTestCase(Class<T> providerClass, String providerAuthority) {
        super(providerClass, providerAuthority);
    }
    
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
	    Log.v("TTProviderTestCase", "start");
    }
    
    @Override
    protected void tearDown() throws Exception {
        if(zoa_t != null)
        {
        	Log.v("TTProviderTestCase", "close");
        	zoa_t.close();		// close TT Unit Test
        }
        else
        {
        	Log.e("TTProviderTestCase", "close error");
        }
        super.tearDown();
    }
}
