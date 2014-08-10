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

/**
 * This class enables received media to be played via a Player.
 * Uses JMF.
 *
 * @author Nivrito
 * @version 1.00
 */

import javax.media.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;

public class MediaPlayerFrame extends JFrame {

    private static final String MEDIAFRAME_TITLE = "Recieveing . .";
    private static final String CONTROLFRAME_TITLE = "Controls";
    private static final int XPOSITION = 100;
    private static final int YPOSITION = 100;
    private static final int FRAMEHEIGHT = 500;
    private static final int FRAMEWIDTH = 500;
    private Player player = null;
    private JTabbedPane tabPane = null;
    
    
    /**
     * This constructor sets up basic structure for the media player.
     */
    public MediaPlayerFrame() {
        super(MEDIAFRAME_TITLE);
        setLocation(XPOSITION, YPOSITION);
        setSize(FRAMEWIDTH, FRAMEHEIGHT);
        tabPane = new JTabbedPane();
        getContentPane().add(tabPane);
        addWindowListener(new WindowAdapter() {
            
            /**
             * This method enables the player window to be closed when crossed out.
             * @param e WindowEvent monitoring the close button.  
             */
            @Override
            public void windowClosing(WindowEvent e) {
                currentPlayerClose();
                System.exit(0);
            }
        });
    }
    
    
    /**
     * Creates main panel UI.
     * @return returns a set upped JPanel. With all components and controls.
     */
    private JPanel mainPanelInitialize() {
        JPanel mainPanel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        mainPanel.setLayout(gbl);

        boolean visComp = false;

        if (player.getVisualComponent() != null) {
            visComp = true;

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.BOTH;

            mainPanel.add(player.getVisualComponent(), gbc);
        }

        if ((player.getGainControl() != null) && (player.getGainControl().getControlComponent() != null)) {
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.weighty = 1;
            gbc.gridheight = 2;
            gbc.fill = GridBagConstraints.VERTICAL;
            mainPanel.add(player.getGainControl().getControlComponent(), gbc);

        }

        if (player.getControlPanelComponent() != null) {
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 1;
            gbc.gridheight = 1;

            if (visComp) {
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weighty = 0;
            } else {
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weighty = 1;
            }
            mainPanel.add(player.getControlPanelComponent(), gbc);
        }

        return mainPanel;
    }
    
    /**
     * Sets MediaLocator to locate source of the playable media.
     * @param locator MediaLocator for the media.
     * @throws IOException
     * @throws NoPlayerException
     * @throws CannotRealizeException 
     */
    public void mediaLocatorSetup(MediaLocator locator) throws IOException, NoPlayerException, CannotRealizeException {
        PlayerSetup(Manager.createRealizedPlayer(locator));
    }
    
    /**
     * Sets up a given player to enable it for playing media. Closes all the existing players.
     * @param pl Player object to play the media.
     */
    private void PlayerSetup(Player pl) {
        currentPlayerClose();
        player = pl;

        tabPane.removeAll();

        if (player == null) {
            return;
        }

        tabPane.add(CONTROLFRAME_TITLE, mainPanelInitialize());

        Control[] controls = player.getControls();
        for (int i = 0; i < controls.length; i++) {
            if (controls[i].getControlComponent() != null) {
                tabPane.add(controls[i].getControlComponent());
            }
        }
    }

    
    /**
     * Closes current player.
     */
    private void currentPlayerClose() {
        if (player != null) {
            player.stop();
            player.close();
        }
    }
    
}
