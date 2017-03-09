package com.ekipa.constant;

public final class WebDefinitions {

    private WebDefinitions() {}

    public static final String FILE_INFO_BY_ID = "/{id:.*}";
    public static final String CONTENT_BY_ID = FILE_INFO_BY_ID + "/content";
}
