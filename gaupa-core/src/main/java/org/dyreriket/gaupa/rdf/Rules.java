package org.dyreriket.gaupa.rdf;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.shared.PrefixMapping;

public class Rules {

    public static Model applyRules(Model model, List<Rule> rules) {
        Reasoner reasoner = new GenericRuleReasoner(rules);
        Model inf = ModelFactory.createInfModel(reasoner, model);
        inf.setNsPrefixes(model);
        return inf;
    }

    public static List<Rule> parseRules(PrefixMapping prefixMap, List<String> rules) {
        String prefixes = PrefixMappings.toStringTurtleFormat(prefixMap);

        // Prepend prefixes to each rule string, need to send via BufferedReader
        // to have prefixes accepted by the parser.
        return rules.stream()
                .map(s -> prefixes.concat(s))
                .map(StringReader::new)
                .map(BufferedReader::new)
                .map(Rule::rulesParserFromReader)
                .map(Rule.Parser::parseRule)
                .collect(Collectors.toList());
    }

    public static class EngineBob {

        /**
         * Bob is your little helpful rule engine.
         */

        public static final String namespace = "http://gaupa.dyreriket.xyz/bob#";
        public static final Property rule = Vocabulary.getProperty(namespace + "rule");

        /**
         * Get all bob:rule-s in Model, i.e, all literal values of the property
         * http://gaupa.dyreriket.xyz/bob#rule
         *
         * @param model
         * @return
         */
        public static List<Rule> getRules(Model model) {
            List<String> rules = model.listObjectsOfProperty(rule)
                    .toList().stream()
                    .map(RDFNode::asLiteral)
                    .map(Literal::getLexicalForm)
                    .collect(Collectors.toList());

            return Rules.parseRules(model, rules);
        }

        /**
         * Apply all bob:rule-s to model and return saturated model.
         *
         * @param model
         * @return
         */
        public static Model applyRules(Model model) {
            return Rules.applyRules(model, getRules(model));
        }

    }

}
