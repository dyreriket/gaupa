package org.dyreriket.gaupa.rdf;

import org.dyreriket.gaupa.Strings;

public class ModelSelectorException extends RuntimeException {

	private static final long serialVersionUID = 1277837142317376514L;

	public ModelSelectorException(String string) {
		super(string);
	}
	
	public ModelSelectorException (Object... message) {
		this(Strings.toString(message, ""));
	}
}
