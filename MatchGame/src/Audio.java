import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Audio {

	public void playSound(String soundFilePath) {
		// -------------------------------------------------------------------------------------------------------------------------------------------
		// Method                : playSound
		// Method Parameters     : String soundFilePath - it will allow us to load the audio we want.
		// Method return         : none
		// Synopsis              : In this method load the audio we are going to use in the code. 
		//
		// Modifications :             Author:                        Notes:
		//     Date:
		//   2023/05/16               C.Sebastian                  Initial Setup
		// --------------------------------------------------------------------------------------------------------------------------------------------
	    try {
	        File soundFile = new File(soundFilePath);
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, e);
	    }
	}
	//NOTE: All the audio files are from SoundQ library (Free Copyright)
	// Website: https://www.prosoundeffects.com/?switchLanguage=en
}
