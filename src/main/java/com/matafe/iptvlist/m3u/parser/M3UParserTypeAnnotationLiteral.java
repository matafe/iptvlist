package com.matafe.iptvlist.m3u.parser;

import javax.enterprise.util.AnnotationLiteral;

public class M3UParserTypeAnnotationLiteral extends AnnotationLiteral<M3uParserType> implements M3uParserType {

    private static final long serialVersionUID = 1L;

    private final M3uParserType.Type value;

    private M3UParserTypeAnnotationLiteral(M3uParserType.Type value) {
	this.value = value;
    }

    public M3uParserType.Type value() {
	return value;
    }

    public static M3UParserTypeAnnotationLiteral type(M3uParserType.Type value) {
	return new M3UParserTypeAnnotationLiteral(value);
    }
}
