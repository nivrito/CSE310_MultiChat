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
package multichatv1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class sets up a text transmission and reception service.
 * 
 *
 * @author Nivrito
 * @version 1.03
 */
public class textChat {

    String user1_ip;
    int user1_port;
    int user1_portb;
    String user2_ip;
    int user2_port;
    
    /**
     * Assigns user data and initializes transmission and reception.
     * @param u1 USER 1 IP
     * @param pU1 USER 1 PORT
     * @param u2 USER 2 IP
     * @param pU2 USER 2 PORT
     */
    textChat(String u1, int pU1, String u2, int pU2) {
        user1_ip = u1;
        user1_port = pU1;
        user1_portb = pU1 + 1;
        user2_ip = u2;
        user2_port = pU2;
        System.out.println("[<<<] denotes sent message, [>>>] denotes recieved message, [] denotes an error.");
        System.out.println("");
        
        new sender().start();
        new reciever().start();
        
    }

    class sender extends Thread {
        /**
         * This nested class starts transmission of texts.
         * Extends thread.
         * 
         * @author Nivrito
         * @version 1.00
         */

        private DatagramSocket ds;
        public int bufferSize = 1024;
        public byte buffer[] = new byte[bufferSize];
        
        /**
         * Initializes UDP socket connection to transmit.
         */
        sender() {
            try {
                this.ds = new DatagramSocket(user1_portb, InetAddress.getByName(user1_ip));
            } catch (SocketException ex) {
                System.out.println("[] Error: Socket Couldn't be created. System Exiting.");
                System.exit(1);
            } catch (UnknownHostException ex) {
                System.out.println("[] Error: Host Couldn't be created. System Exiting.");
                System.exit(1);
            }
        }

        @Override
        public void run() {
            try {
                int pos = 0;
                while (true) {
                    int c = System.in.read();
                    switch (c) {
                        case -1:
                            System.out.println("Server Quits");
                            ds.close();
                            return;
                        case '\r':
                            break;
                        case '\n':
                            ds.send(new DatagramPacket(buffer, pos, InetAddress.getByName(user2_ip), user2_port));
                            System.out.println("<<< " + new String(buffer));
                            pos = 0;
                            break;
                        default:
                            buffer[pos++] = (byte) c;
                    }
                }
            } catch (Exception e) {
                System.out.println("[]  System ran into an error. Exiting application.");
                System.exit(1);
            }
        }
    }

    class reciever extends Thread {
        /**
         * This nested class starts reception of test.
         * Extends thread.
         * 
         * @author Nivrito
         * @version 1.00
         */

        private DatagramSocket ds;
        public int bufferSize = 1024;
        public byte buffer[] = new byte[bufferSize];
        
        /**
         * Initializes UDP socket for receiving texts.
         */
        reciever() {
            try {
                this.ds = new DatagramSocket(user1_port, InetAddress.getByName(user1_ip));
            } catch (SocketException ex) {
                System.out.println("[] Error: Socket Couldn't be created. System Exiting.");
                System.exit(1);
            } catch (UnknownHostException ex) {
                System.out.println("[] Error: Host Couldn't be created. System Exiting.");
                System.exit(1);
            }
        }

        public void run() {
            while (true) {
                try {
                    DatagramPacket p = new DatagramPacket(buffer, buffer.length);
                    ds.receive(p);
                    System.out.println(">>> " + new String(p.getData(), 0, p.getLength()));
                } catch (IOException ex) {
                    System.out.println("[] Error: IOException. System Quits");
                    System.exit(1);
                }
            }
        }

    }

}
