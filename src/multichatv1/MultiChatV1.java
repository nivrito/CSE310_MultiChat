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

import java.util.Scanner;
import jmf.JMF;

/**
 *  Main class to initialize functions of chat program.
 * 
 * @author Nivrito
 * @version 1.00
 */
public class MultiChatV1 {

    static Scanner in = new Scanner(System.in);

    /**
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("*** Welcome to MultiChatV1 Console Version ***\n"
                + "Please enter numbers assigned to each functions to start\n"
                + "1. Text Chat \t 2. Audio Chat");

        while (true) {
            System.out.print("Enter number: ");
            int dir = in.nextInt();
            System.out.println("");
            System.out.println("********************************");

            if (dir == 1) {
                String user1_ip;
                int user1_port;
                String user2_ip;
                int user2_port;
                System.out.println("Enter User 1 details:\n"
                        + "Particularly it means the ip and port for the chat for your device.\n"
                        + "Make sure the other peer enters the same info in the \"User 2\" part.");
                
                System.out.print("\nEnter User 1 IP:");
                user1_ip = in.nextLine();
                
                System.out.print("\nEnter User 1 PORT:");
                user1_port = in.nextInt();
                
                System.out.println("\n\nEnter User 2 details:\n"
                        + "It means the ip and port for the chat for your peer's device.\n"
                        + "Make sure the other peer has the same info in the \"User 1\" part.");
                
                System.out.print("\nEnter User 2 IP:");
                user2_ip = in.nextLine();
                
                System.out.print("\nEnter User 2 PORT:");
                user2_port = in.nextInt();
                
                System.out.println("");
                
                new textChat(user1_ip, user1_port, user2_ip, user2_port);
                
                break;
                
            } else if (dir == 2) {
                
                String user1_ip;
                int user1_port;
                String user2_ip;
                int user2_port;
                System.out.println("Enter transmitting from details:\n"
                        + "Particularly it means the ip and port for the chat for your device.\n"
                        + "Make sure the other peer enters the same info in the \"receiveing from\" part.");
                
                System.out.print("\nEnter IP:");
                user1_ip = in.nextLine();
                
                System.out.print("\nEnter PORT:");
                user1_port = in.nextInt();
                
                System.out.println("\n\nEnter recieving from details:\n"
                        + "It means the ip and port for the chat for your peer's device.\n"
                        + "Make sure the other peer has the same info in the \"transmitting from\" part.");
                
                System.out.print("\nEnter IP:");
                user2_ip = in.nextLine();
                
                System.out.print("\nEnter PORT:");
                user2_port = in.nextInt();
                
                System.out.println("");
                
                new jmf.JMF(user1_ip, user1_port, user2_ip, user2_port);
                
                
            } else {
                System.out.println("Invalid input!");
            }
        }

    }

}
