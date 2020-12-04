package com.parkit.parkingsystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.service.InteractiveShell;

/**
 * Main class of the project to start the programm and show the interface
 * Park'It to the console.
 *
 * @author lilas
 */
class App {

    /**
     * The Constant logger.
     *
     */
    private static final Logger logger = LogManager.getLogger("App");

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String[] args) {
	logger.info("Initializing Parking System");
	InteractiveShell.loadInterface();
    }
}
