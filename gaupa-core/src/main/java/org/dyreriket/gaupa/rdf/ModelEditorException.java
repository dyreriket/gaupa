package org.dyreriket.gaupa.rdf;

import org.dyreriket.gaupa.Strings;

public class ModelEditorException extends RuntimeException {

	private static final long serialVersionUID = -5954271867312442704L;

	public ModelEditorException (String s) {
		super(s);
	}
	
	public ModelEditorException (Object... message) {
		this(Strings.toString(message, ""));
	}

}
