package com.test.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResource;

public class WebappClassLoader extends org.apache.catalina.loader.WebappClassLoader {
	
	private static final org.apache.juli.logging.Log log = org.apache.juli.logging.LogFactory.getLog( WebappClassLoader.class );
	
	public WebappClassLoader() {
		
    }

    public WebappClassLoader(final ClassLoader parent) {
        super(parent);
    }

    @Override
    public void start() throws LifecycleException {
        String[] paths = {"/WEB-INF/commonLib"};
        // Iterate over all the non standard locations
        for (String path : paths) {
            // Get all the resources in the current location
            WebResource[] jars = resources.listResources(path);
            for (WebResource jar : jars) {
                // Check if the resource is a jar file
                if (jar.getName().endsWith(".jar") && jar.isFile() && jar.canRead()) {
                    // Add the jar file to the list of URL defined in the parent class
                    addURL(jar.getURL());
                    log.info("jar added" + jar.getName());
                }
            }
        }
        // Call start on the parent class
        super.start();
    }
}
