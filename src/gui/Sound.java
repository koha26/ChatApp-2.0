package gui;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    public static void playSound(Sounds sounds) {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(sounds.toString()).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e.getCause() + " : " + e.getMessage());
        }
    }

    public enum Sounds {

        NEW_MESSAGE {
            @Override
            public String toString() {
                return "sound/message.wav";
            }
        }
    }
}
