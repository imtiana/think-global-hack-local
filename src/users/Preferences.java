package users;

import java.awt.Color;
import java.io.File;

public class Preferences {
	
	String voice; 
	int speed;
	Themes theme;
	//Color backgroundColour;
	//Color fontColour;
	int fontSize;
	int volume;

	
	
	Preferences(){
		voice = "Allen";
		speed = 1;
		//backgroundColour = Color.green;
		//fontColour = Color.yellow;
		theme = Themes.DEFAULT;
		fontSize = 27;
	}
	
	/*Preferences(String v, int s, ){
		voice = v;
		speed = s;
		backgroundColour = Color.green;
		fontColour = Color.yellow;
		fontSize = 12;
	}*/
	
	
	public String getVoice() {
		return voice;
	}
	public void setVoice(String voice) {
		this.voice = voice;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		if(fontSize > 27){
			this.fontSize = fontSize;
		}		
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public int getVolume(){
		return this.volume;
	
	}
	/*public Color getBackgroundColour() {
	return backgroundColour;
	}
	public void setBackgroundColour(Color backgroundColour) {
		this.backgroundColour = backgroundColour;
	}
	public Color getFontColour() {
		return fontColour;
	}
	public void setFontColour(Color fontColour) {
		this.fontColour = fontColour;
	}*/
	
	
	
	

}
