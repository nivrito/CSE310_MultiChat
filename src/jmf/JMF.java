/*
 * Copyright (C) 2014 Nivrito
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jmf;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoPlayerException;
import javax.media.NoProcessorException;
import javax.media.protocol.DataSource;

/**
 * This class redirects user data to the nested classes in order to establish a
 * audio data transmission between two devices.
 *
 * @author Nivrito
 * @version 1.02
 */
public class JMF extends Thread {

    String user1_ip;
    int user1_port;
    String user2_ip;
    int user2_port;

    /**
     * This method initializes the class and distributes user data.
     *
     * @param u1 IP address of the transmitting device.
     * @param pU1 Port of transmitting device.
     * @param u2 IP of the device from where this device is receiving
     * transmission.
     * @param pU2 Port of the device from where this device is receiving
     * transmission.
     */
    public JMF(String u1, int pU1, String u2, int pU2) {
        user1_ip = u1;
        user1_port = pU1;

        user2_ip = u2;
        user2_port = pU2;

        new transmit().start();
        new recieve().start();
    }

    class transmit extends Thread {

        /**
         * This nested class starts transmission of audio data.
         * Extends thread.
         * 
         * @author Nivrito
         * @version 1.00
         */

        @Override
        public void run() {
            MediaLocator locator = new MediaLocator("rtp://" + user1_ip + ":" + user1_port + "/audio/1");
            MediaTransmitter transmitter = new MediaTransmitter(locator);
            System.out.println("-> Created media locator: '"
                    + locator + "'");

            /* Creates and uses a file reference for the audio file,
             if a url or any other reference is desired, then this 
             line needs to change.
             */
            DataSource source = null;
            try {
                source = Manager.createDataSource(
                        new MediaLocator("javasound://44100"));
            } catch (Exception ex) {
                System.out.println("[] ERROR: " + ex);
                System.exit(1);
            }
            System.out.println("-> Created data source: '"
                    + "javasound://44100" + "'");
            try {
                // set the data source.
                transmitter.dataSourceSetup(source);
            } catch (Exception ex) {
                System.out.println("[] ERROR: " + ex);
                System.exit(1);
            }
            System.out.println("-> Set the data source on the transmitter");
            transmitter.startTransmition();
            System.out.println("-> Transmitting...");
            System.out.println("   Press the Enter key to exit");
            try {
                // wait for the user to press Enter to proceed and exit.
                System.in.read();
            } catch (IOException ex) {
                System.out.println("[] ERROR: " + ex);
                System.exit(1);
            }
            System.out.println("-> Exiting");
            transmitter.stopTransmition();
        }

    }

    class recieve extends Thread {
        
        /**
         * This nested class receives transmission of audio data.
         * Extends thread.
         * 
         * @author Nivrito
         * @version 1.00
         */
        
        @Override
        public void run() {
            MediaPlayerFrame mpf = new MediaPlayerFrame();
            System.out.println("Media Frame Ready");
            try {
                mpf.mediaLocatorSetup(new MediaLocator("rtp://" + user2_ip + ":" + user2_port + "/audio/1"));
            } catch (Exception ex) {
                System.out.println("[] ERROR: " + ex);
                System.exit(1);
            }
            mpf.show();
        }
    }
}
