package org.dyreriket.gaupa.rdf;

import org.apache.commons.lang3.StringUtils;

public class ModelEditorException extends RuntimeException {

    private static final long serialVersionUID = -5954271867312442704L;

    public ModelEditorException(Object... message) {
        this(StringUtils.joinWith("", message));
    }

    public ModelEditorException(String s) {
        super(s);
    }

}
