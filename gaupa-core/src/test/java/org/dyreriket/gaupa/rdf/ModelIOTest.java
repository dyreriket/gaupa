package org.dyreriket.gaupa.rdf;

import static org.junit.Assert.assertEquals;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.OWL;
import org.junit.Test;

public class ModelIOTest {

    private String resources = "src/test/resources/rdf/";

    @Test
    public void shouldPrintModel() throws ModelIOException {
        Model m = ModelIO.readModel(this.resources + "model.ttl");
        ModelIO.printModel(m, ModelIO.Format.N3);
        ModelIO.printModel(m, ModelIO.Format.NTRIPLES);
        ModelIO.printModel(m, ModelIO.Format.RDFXML);
        ModelIO.printModel(m, ModelIO.Format.TURTLE);
    }

    @Test
    public void shouldReadModel() {
        Model m = ModelIO.readModel(this.resources + "model.ttl");
        assertEquals(m.size(), 2);
    }

    @Test
    public void shouldShortForm() {
        Model m = ModelIO.readModel(this.resources + "model.ttl");
        assertEquals(ModelIO.shortForm(m, OWL.Class), "owl:Class");
    }

}
