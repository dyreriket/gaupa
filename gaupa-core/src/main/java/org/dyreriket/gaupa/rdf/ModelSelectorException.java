package org.dyreriket.gaupa.rdf;

import org.apache.commons.lang3.StringUtils;

public class ModelSelectorException extends RuntimeException {

    private static final long serialVersionUID = 1277837142317376514L;

    public ModelSelectorException(Object... message) {
        this(StringUtils.joinWith("", message));
    }

    public ModelSelectorException(String string) {
        super(string);
    }
}
