package com.daybreakr.puppy.parse;

import android.util.Xml;

import com.daybreakr.puppy.engine.model.EntityInfo;
import com.daybreakr.puppy.engine.model.SceneInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

class SceneXmlParser extends SceneParser {
    private static final String TAG_SCENE = "scene";
    private static final String TAG_ENTITIES = "entities";

    private static final String ATTR_NAME = "name";

    @Override
    public SceneInfo parse(InputStream input) throws SceneParserException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(input, "UTF-8");

            int type;

            do {
                type = parser.next();
            } while (type != XmlPullParser.START_TAG && type != XmlPullParser.END_DOCUMENT);

            if (type != XmlPullParser.START_TAG) {
                throw new SceneParserException("No start tag found");
            }

            if (!TAG_SCENE.equals(parser.getName())) {
                throw new SceneParserException("Root element is not '" + TAG_SCENE + "'");
            }

            SceneInfo sceneInfo = new SceneInfo();
            sceneInfo.name = parser.getAttributeValue(null, ATTR_NAME);
            sceneInfo.entities = new HashMap<>();

            final int searchDepth = parser.getDepth() + 1;

            while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
                    && (type != XmlPullParser.END_TAG || parser.getDepth() >= searchDepth)) {
                if (type == XmlPullParser.END_TAG || type == XmlPullParser.TEXT) {
                    continue;
                }

                if (parser.getDepth() != searchDepth) {
                    continue;
                }

                String name = parser.getName();
                if (TAG_ENTITIES.equals(name)) {
                    parseEntities(parser, sceneInfo.entities);
                }
            }
            return sceneInfo;
        } catch (XmlPullParserException e) {
            throw new SceneParserException(e);
        } catch (IOException e) {
            throw new SceneParserException(e);
        }
    }

    private void parseEntities(XmlPullParser parser, Map<String, EntityInfo> entities) {
        // TODO: parse entities
    }
}
