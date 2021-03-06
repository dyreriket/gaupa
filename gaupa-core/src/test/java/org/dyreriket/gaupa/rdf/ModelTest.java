package org.dyreriket.gaupa.rdf;

import static org.junit.Assert.assertEquals;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.junit.Test;

public class ModelTest {

    @Test
    public void shouldCopyModelFreshingBlanks() throws ModelIOException {
        Model m = ModelFactory.createDefaultModel();
        m.add(ResourceFactory.createResource(), RDF.type, OWL.Thing);
        m.add(ResourceFactory.createResource(), RDF.type, OWL.Thing);

        assertEquals(2, m.size());
        ModelIO.printModel(m, ModelIO.Format.NTRIPLES);

        Model copy = Models.duplicate(m, Models.BlankCopy.FRESH);
        assertEquals(2, copy.size());
        ModelIO.printModel(copy, ModelIO.Format.NTRIPLES);

        m.add(copy);
        assertEquals(4, m.size());
        ModelIO.printModel(m, ModelIO.Format.NTRIPLES);
    }

    @Test
    public void shouldCopyModelKeepingBlanks() throws ModelIOException {
        Model m = ModelFactory.createDefaultModel();
        m.add(ResourceFactory.createResource(), RDF.type, OWL.Thing);
        m.add(ResourceFactory.createResource(), RDF.type, OWL.Thing);

        assertEquals(2, m.size());
        ModelIO.printModel(m, ModelIO.Format.NTRIPLES);

        Model copy = Models.duplicate(m, Models.BlankCopy.KEEP);
        assertEquals(2, copy.size());
        ModelIO.printModel(copy, ModelIO.Format.NTRIPLES);

        m.add(copy);
        assertEquals(2, copy.size());
        ModelIO.printModel(copy, ModelIO.Format.NTRIPLES);
    }

}
