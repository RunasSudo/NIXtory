package io.github.runassudo.libnixtory;

import java.util.ArrayList;

public class NIXtory {
	private ArrayList<VideoInput> videoInputs;
	private ArrayList<AudioInput> audioInputs;

	public NIXtory() {
		videoInputs = new ArrayList<VideoInput>();
		audioInputs = new ArrayList<AudioInput>();
	}

	public ArrayList<VideoInput> getVideoInputs() {
		return videoInputs;
	}

	public ArrayList<AudioInput> getAudioInputs() {
		return audioInputs;
	}
}
