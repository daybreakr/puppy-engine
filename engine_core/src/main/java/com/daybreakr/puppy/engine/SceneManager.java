package com.daybreakr.puppy.engine;

import com.daybreakr.puppy.engine.model.SceneInfo;
import com.daybreakr.puppy.parse.SceneParser;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class SceneManager {
    private final Map<String, Scene> mScenes = new LinkedHashMap<>();

    private Scene mCurrentScene;

    public boolean isSceneLoaded(String name) {
        return mScenes.containsKey(name);
    }

    public void loadScene(InputStream input) throws SceneParser.SceneParserException {
        SceneParser parser = SceneParser.newParser();
        SceneInfo sceneInfo = parser.parse(input);

        Scene scene = new Scene();
        scene.name = sceneInfo.name;
        scene.sceneInfo = sceneInfo;

        mScenes.put(scene.name, scene);
    }

    public void startScene(String name) {
        Scene scene = mScenes.get(name);
        if (scene == null) {
            throw new IllegalStateException("Scene " + name + " not loaded yet");
        }

        if (mCurrentScene != null) {
            if (mCurrentScene == scene) {
                return;
            }
            mCurrentScene.onExit();
        }

        mCurrentScene = scene;
        mCurrentScene.onEnter();
    }
}
