package com.ekipa.constant;

public final class WebDefinitions {

    private WebDefinitions() {
    }

    public static final String ID = "id";
    public static final String FILE = "file";
    public static final String FILE_INFO_BY_ID = "/{" + ID + ":.*}";
    public static final String CONTENT_BY_ID = FILE_INFO_BY_ID + "/content";

}
