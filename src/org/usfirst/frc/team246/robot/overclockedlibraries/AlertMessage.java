/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team246.robot.overclockedLibraries;

/**
 *
 * @author Dave
 */
public class AlertMessage {

	/**
	 * The Enum Severity provides several types of messages that can be filtered
	 * by.
	 */
	public enum Severity {

		/** For robot chitchat. */
		CHITCHAT("CHITCHAT"),

		/** For debug messages. */
		DEBUG("DEBUG"),

		/** For warnings. */
		WARNING("WARNING"),

		/** for general errors. */
		ERROR("ERROR"),

		/** For fatal errors. */
		FATAL("FATAL");

		/** The message. */
		private final String name;

		/**
		 * Instantiates a new severity.
		 *
		 * @param s
		 *            the severity
		 */
		private Severity(String s) {
			name = s;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		public String toString() {
			return name;
		}
	}

	/** The message. */
	private String message;

	/** The sound to play with the message. */
	private String soundToPlay;

	/** The severity of the message. */
	private Severity severity;

	/** The category of the message. */
	private String category;

	/** The subsystem the message belongs to. */
	private String subsystem;

	/** The details of the message. */
	private String details;

	/**
	 * Instantiates a blank warning message.
	 */
	public AlertMessage() {
		this.severity = Severity.WARNING;
	}

	/**
	 * Instantiates a new alert message.
	 *
	 * @param message
	 *            the message
	 */
	public AlertMessage(String message) {
		this();
		this.message = message;
	}

	/**
	 * returns an alert with the message given.
	 *
	 * @param message
	 *            the message
	 * @return the alert message
	 */
	public AlertMessage message(String message) {
		this.message = message;
		return this;
	}

	/**
	 * Returns an alert that plays a sound.
	 *
	 * @param soundToPlay
	 *            the sound to play
	 * @return the alert message
	 */
	public AlertMessage playSound(String soundToPlay) {
		this.soundToPlay = soundToPlay;
		return this;
	}

	/**
	 * Returns an alert with the given Severity.
	 *
	 * @param severity
	 *            the severity
	 * @return the alert message
	 */
	public AlertMessage severity(Severity severity) {
		this.severity = severity;
		return this;
	}

	/**
	 * Returns an alert of the given Category.
	 *
	 * @param category
	 *            the category
	 * @return the alert message
	 */
	public AlertMessage category(String category) {
		this.category = category;
		return this;
	}

	/**
	 * Returns an alert of the given Subsystem.
	 *
	 * @param subsystem
	 *            the subsystem
	 * @return the alert message
	 */
	public AlertMessage subsystem(String subsystem) {
		this.subsystem = subsystem;
		return this;
	}

	/**
	 * Adds a detail to an alert message or concats it if there are already
	 * details.
	 *
	 * @param details
	 *            the details
	 * @return the alert message
	 */
	public AlertMessage addDetail(String details) {
		if (this.details == null)
			this.details = details;
		else
			this.details = this.details.concat("\n" + details);
		return this;
	}

	/**
	 * Turns the alert into xml.
	 *
	 * @return the new XML string
	 */
	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<AlertMessage>\n");
		if (message != null) {
			sb.append("  <Message>");
			sb.append(message);
			sb.append("</Message>\n");
		}
		if (soundToPlay != null) {
			sb.append("  <SoundFileToPlay>");
			sb.append(soundToPlay);
			sb.append("</SoundFileToPlay>\n");
		}
		sb.append("  <SeverityLevel>");
		sb.append(severity.toString());
		sb.append("</SeverityLevel>\n");
		if (category != null) {
			sb.append("  <MessageCategory>");
			sb.append(category);
			sb.append("</MessageCategory>\n");
		}
		if (subsystem != null) {
			sb.append("  <SubsystemId>");
			sb.append(subsystem);
			sb.append("</SubsystemId>\n");
		}
		if (details != null) {
			sb.append("  <Details>");
			sb.append(details);
			sb.append("</Details>\n");
		}
		sb.append("</AlertMessage>");
		return sb.toString();
	}
}
