package org.dyreriket.gaupa.rdf;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.StringJoiner;

import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.FileUtils;
import org.dyreriket.gaupa.owl.Ontologies;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public abstract class ModelIO {

    public enum Format {
        RDFXML("RDF/XML"), TURTLE("TTL"), N3("N3"), NTRIPLES("N-TRIPLES"), OWL("OWL");
        private final String lang;

        private Format(final String lang) {
            this.lang = lang;
        }
    }

    public static void printModel(Model model, ModelIO.Format format) throws ModelIOException {
        System.out.println(writeModel(model, format));
    }

    public static Model readModel(String file) {
        return readModel(file, FileUtils.guessLang(file, ModelIO.Format.TURTLE.lang));
    }

    public static Model readModel(String file, ModelIO.Format serialisation) {
        return readModel(file, serialisation.lang);
    }

    private static Model readModel(String file, String serialisation) {
        return FileManager.get().loadModel(file, serialisation);
    }

    public static String shortForm(Model model, List<? extends RDFNode> nodes) {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (RDFNode node : nodes) {
            sj.add(shortForm(model, node));
        }
        return sj.toString();
    }

    public static String shortForm(Model model, Node node) {
        if (node.isVariable()) {
            return node.toString();
        }
        return model.shortForm(node.toString());
    }

    public static String shortForm(Model model, RDFNode node) {
        if (node.canAs(RDFList.class)) {
            return shortForm(model, node.as(RDFList.class).asJavaList());
        } else {
            return shortForm(model, node.asNode());
        }
    }

    public static String shortForm(RDFNode node) {
        Model model = node.getModel();
        return model == null ? node.toString() : shortForm(model, node.asNode());
    }

    public static String writeModel(Model model, ModelIO.Format format) throws ModelIOException {
        String serialisation = "";
        if (format.equals(ModelIO.Format.OWL)) {
            try {
                serialisation = Ontologies.writeAsOntology(model);
            } catch (OWLOntologyStorageException | OWLOntologyCreationException | IOException e) {
                throw new ModelIOException("Error writing model. " + e.getMessage());
            }
        } else {
            serialisation = writeRDFModel(model, format);
        }
        return serialisation;
    }

    private static String writeRDFModel(Model model, ModelIO.Format format) {
        StringWriter str = new StringWriter();
        model.write(str, format.lang);
        String modelString = str.toString();
        str.flush();
        try {
            str.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return modelString;
    }

}
