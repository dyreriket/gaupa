package org.dyreriket.gaupa.rdf;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public interface Vocabulary {
	
	static Property getProperty(String url) {
        return ResourceFactory.createProperty(url);
    }

    static Resource getResource(String url) {
        return ResourceFactory.createResource(url);
    }

}
