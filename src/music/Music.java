package music;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Music {

    public void playClip() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        URL url = getClass().getResource("hit.wav");
        AudioInputStream is = AudioSystem.getAudioInputStream(url);
        Clip clip = AudioSystem.getClip();
        clip.open(is);
        clip.setFramePosition(0);
        clip.start();
    }

    public void playMidi() throws IOException, InvalidMidiDataException, MidiUnavailableException {
        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.open();
        sequencer.setSequence(getClass().getResourceAsStream("evil_woman.mid"));
        sequencer.start();
    }

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException, InvalidMidiDataException, MidiUnavailableException {
        Music music = new Music();
        music.playClip();
        Thread.sleep(1000);
        music.playMidi();
        System.out.println("Playing!");
        for (int i = 0; i < 10; i++) {
            Thread.sleep(5000);
            music.playClip();
        }
    }
}
