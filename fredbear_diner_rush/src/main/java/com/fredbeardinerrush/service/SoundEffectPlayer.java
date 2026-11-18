package com.fredbeardinerrush.service;

import javafx.scene.media.AudioClip;
import java.net.URL;

public class SoundEffectPlayer {
    private AudioClip springbonnieMunch;
    private AudioClip fredbearMunch;

    public SoundEffectPlayer() {
        try {
            // Try loading from package folder first, then root resources fallback
            URL resourceUrl = getClass().getResource("/com/fredbeardinerrush/munch_1.wav");
            if (resourceUrl == null) {
                resourceUrl = getClass().getResource("/munch_1.wav");
            }

            if (resourceUrl != null) {
                String audioUrl = resourceUrl.toExternalForm();

                // Springbonnie normal munch
                springbonnieMunch = new AudioClip(audioUrl);
                springbonnieMunch.setVolume(1);

                // Fredbear deepened munch (AudioClip supports rate shifting instantly!)
                fredbearMunch = new AudioClip(audioUrl);
                fredbearMunch.setVolume(1);
                fredbearMunch.setRate(0.52); // Lower rate deepens the pitch for Fredbear
            } else {
                System.err.println("Could not find munch_1.wav in resources!");
            }
        } catch (Exception e) {
            System.err.println("Audio assets could not be loaded: " + e.getMessage());
        }
    }

    public void playSpringbonnieMunch() {
        if (springbonnieMunch != null) {
            springbonnieMunch.play();
        }
    }

    public void playFredbearMunch() {
        if (fredbearMunch != null) {
            fredbearMunch.play();
        }
    }
}