package org.dyreriket.gaupa.rdf;

import static org.junit.Assert.assertEquals;

import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.junit.Test;

public class ModelsTest {

    private String cloneQuery = "CONSTRUCT { ?s ?p ?o } WHERE { ?s ?p ?o }";

    public Model clone1(Model source) {
        Model copy = ModelFactory.createDefaultModel();
        copy.add(source);
        return copy;
    }

    public Model clone2(Model source) {
        return QueryExecutionFactory.create(this.cloneQuery, source).execConstruct();
    }

    public Model clone3(Model source) {
        Model copy = clone1(source);
        for (RDFNode node : ModelSelector.getRDFNodes(copy)) {
            if (node.isAnon()) {
                ModelEditor.substituteNode(copy, node, ResourceFactory.createResource());
            }
        }
        return copy;
    }

    @Test
    public void shouldCloneModel() throws ModelIOException {

        // create the model: [] a Thing .
        Model source = ModelFactory.createDefaultModel();
        source.add(ResourceFactory.createResource(), RDF.type, OWL.Thing);
        ModelIO.printModel(source, ModelIO.Format.NTRIPLES);

        // OK
        assertEquals(1, source.size());

        // clone
        Model copy = clone3(source);
        ModelIO.printModel(copy, ModelIO.Format.NTRIPLES);

        // OK
        assertEquals(1, copy.size());

        // adding cloned copy back into source
        source.add(copy);

        // Fails with current implementation
        assertEquals(2, source.size());
    }

}
