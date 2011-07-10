package com.duboisproject.playshare.model;

import java.util.Date;
import java.util.Vector;

// Games are a sequence of Events that are assigned GameTimes.
// GameTimes are nothing more than a positive offset from the (original, absolute start time?)
// (or most recent GameTimeEvent?).
/*
 * let's say you want to know how long a player has been playing.
 * you query the player for all its substitution events
 * then, for each substitution event, you query it for its GameTime
 * then, you take each game time and perform appropriate additions to calculate an overall amount of
 * game time spent playing. This is then cached relative to the most recent GameTimeEvent to make
 * future calculations simpler.
 * to calculate game time of a substitution event, you query the event for its GameClockEvent,
 * and its relative (positive) offset to that GameClockEvent. Or, rather, the SubstitutionEvent adds
 * its
 * offset to the cumulative game time of the GameClockEvent to which it is relative and returns a
 * raw number.
 * GameClockEvents calculate their own actual values by referencing the previous GameClockEvent.
 * A GameClockEvent has a # of milliseconds relative to the Game epoch which is determined by making
 * a Date object at creation and then performing subtraction. The Date object is not retained. From
 * this time is subtracted the previous GameClockEvent time if it was a 'START' event, and to this
 * time is added the previous GameClockEvent time if it was a 'STOP' event. Each GameClockEvent is
 * calculated recursively in this manner. As these GameClockEvents have GameTimes computed for them,
 * they get marked as "calculated." If they are ever changed, they mark
 * for runtime efficiency, many of these values can be cached, but for the purposes of actual
 * storage, only a value freshly calculated from the specified reference values can be stored/relied
 * upon as official.
 * likewise, Players can cache things like totalGamePlayTime, but official statistics will only be
 * stored as reference values.
 */

public class Game {

	public enum GameState {
		PENDING, RUNNING, STOPPED, ENDED, ;
	}

	public class GameClockEvent {
		private final Date absoluteTime = new Date();
		private final GameState newState;
		private final GameClockEvent previous;
		private GameClockEvent next;

		public GameClockEvent(GameClockEvent previous, GameState newState) {
			if (null == newState)
				throw new NullPointerException("null new state!");
			this.newState = newState;
			this.previous = previous;
			previous.linkNext(this);
		}

		protected void linkNext(GameClockEvent next) {
			this.next = next;
		}

		protected void setGameTime(long millis) {

		}
	}

	public class GameClockMarker {
		public GameClockMarker() {
		}
	}

	private GameState state = GameState.PENDING;
	protected Vector<Event> events = new Vector<Event>();
	protected Vector<GameClockEvent> clockEvents = new Vector<GameClockEvent>();

	protected GameState getState() {
		return this.state;
	}

	public synchronized boolean isPending() {
		return this.state == GameState.PENDING;
	}

	public synchronized void begin() throws IllegalStateException {
		this.doStateTransition(new GameClockEvent(null, GameState.RUNNING));
		/*
		 * record a reference time for the very beginning all other times are relative to this one.
		 * stops and restarts will record a reference time as well. all standard events will record
		 * times relative not to the reference time, but to the game clock, as defined by all stops
		 * and restarts.
		 */
	}

	/**
	 * this method pauses the timer, and disallows? submission of Events
	 * 
	 */
	public synchronized void stop() throws IllegalStateException {
		this.doStateTransition(new GameClockEvent(null, GameState.STOPPED));
	}

	/**
	 * this method restarts the timer, and reallows submission of Events if they have been disallowed
	 * 
	 */
	public synchronized void restart() throws IllegalStateException {

	}

	/**
	 * This method ends the game.
	 */
	public synchronized void end() {
		this.state = GameState.ENDED;
	}

	protected final synchronized void doStateTransition(GameClockEvent gcEvent)
			throws IllegalStateException {

	}

	public synchronized void addEvent(Event event) {
		// get most recent GameClockEvent
		// assign to the event a time relative to the gameclockevent
		// store the event in the game
		this.events.add(event);
	}

	/**
	 * Uses the recorded absolute times of timer events within the game along with the stored 'game
	 * time' of the event itself to determine an absolute time for the event.
	 * 
	 * @param game
	 * @param event
	 * @return Date object representing the absolute time of an event
	 */
	public static Date getAbsoluteTime(Game game, Event event) {
		throw new RuntimeException("this method is not yet properly implemented");
	}
}
