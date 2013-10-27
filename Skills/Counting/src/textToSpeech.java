import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import sun.security.jca.GetInstance;

import com.sun.speech.freetts.FreeTTSSpeakableImpl;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/* TODO: Remove this when done & add jsapi.jar part to installation doc 
 * IF YOU ARE GETTING ERRORS: 
 * 	- 	Make sure you added FREETTS.JAR to your build path libraries 
 *	  	(Right-click project > Properties > Java Build Path > Libraries > Add External JARs 
 * If you're still getting errors, find Denise!  	
 */

public class textToSpeech {
	private static final String DEFAULT_VOICE = "kevin16";
	private static textToSpeech instance = new textToSpeech();
	private ExecutorService executor;
	private int wordsPerMinute;
	private List<FutureTask<String>> listOfTasks;

	private textToSpeech() {
		wordsPerMinute = 100; // default speed (in case people don't set the
								// speed)
		executor = Executors.newFixedThreadPool(1);
		listOfTasks = new ArrayList<FutureTask<String>>();
	}

	public static textToSpeech getInstance() {
		return instance;
	}

	private class SpeakerThread implements Callable<String> {
		private String textToSpeak;
		private int wordsPerMinute;

		public SpeakerThread(String text, int wpm) {
			textToSpeak = text;
			wordsPerMinute = wpm;
		}

		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			VoiceManager voiceManager = VoiceManager.getInstance();
			Voice speakerVoice = voiceManager.getVoice(DEFAULT_VOICE);
			speakerVoice.setRate(wordsPerMinute); // Set the speaker's voice
													// speed (default = 100
													// words/minute)
			speakerVoice.allocate(); // Allocates the resources for the voice
			speakerVoice.speak(textToSpeak);
			speakerVoice.deallocate(); // Synthesize speech & clean up after
			listOfTasks.remove(this);
			return textToSpeak;
		}

	}

	public void setWPM(int wpm) {
		wordsPerMinute = wpm;
	}

	public void speak(String text) {
		FutureTask<String> task = new FutureTask<String>(new SpeakerThread(
				text, 100));
		listOfTasks.add(task);

		executor.submit(task);
	}

	/**
	 * Speak the text now, removing all pending speech
	 * 
	 * @param text
	 */
	public void speakNow(String text) {
		for (FutureTask<String> task : listOfTasks){
			task.cancel(true);
			//listOfTasks.remove(task);
		}
		listOfTasks.clear();
		FutureTask<String> task = new FutureTask<String>(new SpeakerThread(
				text, 100));
		listOfTasks.add(task);

		executor.submit(task);
	}
}