/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team246.robot.overclockedLibraries;

import java.io.IOException;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;

/**
 * The Class UdpAlertService.
 *
 * @author Dave
 */
public class UdpAlertService {

	/**
	 * Initialize.
	 *
	 * @param hostname
	 *            the hostname
	 * @param port
	 *            the port
	 */
	public static void initialize(String hostname, int port) {
		UdpAlertService.alertServiceHost = hostname;
		UdpAlertService.alertServicePort = port;
		initialize();
	}

	/**
	 * Initializes the connection
	 */
	private static void initialize() {
		try {
			alertServerAddress = InetAddress.getByName(alertServiceHost);
			transmitSocket = new DatagramSocket();
			transmitSocket.setBroadcast(false);
			transmitSocket.setReuseAddress(true);
			isInitialized = true;
		} catch (Exception e) {
		}
	}

	/** The alert server address. */
	private static InetAddress alertServerAddress;

	/** The alert service port. */
	private static int alertServicePort;

	/** The transmit socket. */
	private static DatagramSocket transmitSocket;

	/** The alert service host. */
	private static String alertServiceHost;

	/** Has the connection been initialized */
	private static boolean isInitialized = false;

	/**
	 * Send alert.
	 *
	 * @param alertMessage
	 *            the alert message
	 */
	public static void sendAlert(AlertMessage alertMessage) {
		if (!isInitialized)
			return;
		String xmlString = alertMessage.toXml();
		DatagramPacket packet = new DatagramPacket(xmlString.getBytes(), xmlString.length(), alertServerAddress,
				alertServicePort);
		try {
			transmitSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes the port
	 */
	public static void close() {
		if (!isInitialized)
			transmitSocket.close();
	}
}
