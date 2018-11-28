package com.daybreakr.puppy.parse;

import com.daybreakr.puppy.engine.model.SceneInfo;

import java.io.InputStream;

public abstract class SceneParser {

    public static SceneParser newParser() {
        return new SceneXmlParser();
    }

    public abstract SceneInfo parse(InputStream input) throws SceneParserException;

    public static class SceneParserException extends Exception {

        public SceneParserException(String message) {
            super(message);
        }

        public SceneParserException(Throwable cause) {
            super(cause);
        }
    }
}
