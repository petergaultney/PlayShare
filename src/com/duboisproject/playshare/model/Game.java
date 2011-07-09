package com.duboisproject.playshare.model;

import java.util.Vector;

// Games are a sequence of Events

public class Game {

	protected Vector<Event> events = new Vector<Event>();

	public void begin() {
		// this method should start a timer, as a game is fundamentally a set of
		// Events that arrive at different times.
	}

	/**
	 * this method pauses the timer, and disallows? submission of Events
	 * 
	 */
	public void stop() {
	}

	/**
	 * this method restarts the timer, and reallows submission of Events if they
	 * have been disallowed
	 * 
	 */
	public void restart() {

	}

	/**
	 * This method ends the game.
	 */
	public void end() {

	}
}
