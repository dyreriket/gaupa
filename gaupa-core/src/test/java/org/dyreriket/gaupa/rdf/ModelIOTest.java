package org.dyreriket.gaupa.rdf;

import static org.junit.Assert.assertEquals;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.OWL;
import org.dyreriket.gaupa.rdf.ModelIO;
import org.dyreriket.gaupa.rdf.ModelIOException;
import org.junit.Test;

public class ModelIOTest {

	private String resources = "src/test/resources/rdf/";
		
	@Test
	public void shouldReadModel () {
		Model m = ModelIO.readModel(resources + "model.ttl");
		assertEquals(m.size(), 2);
	}
	
	@Test
	public void shouldPrintModel () throws ModelIOException {
		Model m = ModelIO.readModel(resources + "model.ttl");
		ModelIO.printModel(m, ModelIO.format.N3);
		ModelIO.printModel(m, ModelIO.format.NTRIPLES);
		ModelIO.printModel(m, ModelIO.format.RDFXML);
		ModelIO.printModel(m, ModelIO.format.TURTLE);
	}
	
	@Test
	public void shouldShortForm () {
		Model m = ModelIO.readModel(resources + "model.ttl");
		assertEquals(ModelIO.shortForm(m, OWL.Class), "owl:Class");
	}
		

}
