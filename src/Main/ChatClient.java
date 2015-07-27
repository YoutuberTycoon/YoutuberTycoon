package Main;
/*
 * ******************************************************
 *  * Copyright (C) 2015 Malimoi <sandeaujules975@gmail.com>
 *  *
 *  * This file (ChatClient) is part of YoutuberTycoon.
 *  *
 *  * Created by Malimoi on 13/07/15 11:42.
 *  *
 *  * YoutuberTycoon can not be copied and/or distributed without the express
 *  * permission of Malimoi.
 *  ******************************************************
 */

import javax.swing.*;

import utilities.Evenement;
import utilities.Planning;
import utilities.StringComparator;
import utilities.Videos;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.List;

// Class to manage Client chat Box.
public class ChatClient {

	public static List<String> TEST = new ArrayList<String>();
	public static List<Videos> videos = new ArrayList<Videos>();
	public static List<Planning> planning = new ArrayList<Planning>();
	public static List<Evenement> evenements = new ArrayList<Evenement>();
	public static List<String> datapl = new ArrayList<String>();
	public static List<String> dataev = new ArrayList<String>();
    public static JFrame frame;

    public static void main(String[] args) {
        String server = "127.0.0.1";
        int port = 2009;
        ChatAccess access = null;
        try {
            access = new ChatAccess(server, port);
        } catch (IOException ex) {
            System.out.println("Cannot connect to " + server + ":" + port);
            ex.printStackTrace();
            System.exit(0);
        }
        frame = new ChatFrame(access);
        frame.setTitle("Launcher YT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Chat client access
     */
    static class ChatAccess extends Observable {
        private static final String CRLF = "\r\n"; // newline
        private Socket socket;
        private OutputStream outputStream;
        private PrintWriter out = null;

        /**
         * Create socket, and receiving thread
         */
        public ChatAccess(String server, int port) throws IOException {
            socket = new Socket(server, port);
            outputStream = socket.getOutputStream();

            Thread receivingThread = new Thread() {
                @Override
                public void run() {
                    try {
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                        String line;
                        while ((line = in.readLine()) != null)
                            if (!line.contains("connecte")) {
                                notifyObservers(line);
                            } else {
                            	 /*
                                 * Ceci sont des valeurs fictives. Il faudra seulement get toutes les horraires sur le server.
                        		 */
                        		
                        		/*
                        		 * On r�cup�re les vid�os
                        		 */
                        		videos.add(new Videos("JE MANGE DES CHIPS !", 0L, 0L, 0L, 0L, null, 1L));
                        		videos.add(new Videos("JE M'APPELLE BYSLIDE", 1254L, 215L, 3L, 3L, null, 2L));
                        		
                        		/*
                        		 * Evenements
                        		 */
                                evenements.add(new Evenement(27, 7, 15, 5, 0L));
                                evenements.add(new Evenement(28, 7, 15, 2, 1L));
                                evenements.add(new Evenement(27, 7, 15, 2, 2L));
                                for (int a = 0; a < evenements.size(); a++) {
                                    dataev.add(evenements.get(a).getDay() + " " + evenements.get(a).getMonth() + " " + evenements.get(a).getYear() + " " + evenements.get(a).getId()
                                    		+ " " + evenements.get(a).getData());
                                }
                                evenements.add(new Evenement(0, 0, 0, null, 0L));

                                Collections.sort(dataev, new StringComparator());
                                Collections.reverse(dataev);

                                evenements.clear();
                                for (int i = 0; i < dataev.size(); i++) {
                                    String line2 = dataev.get(i);

                                    String day = line2.split(" ")[0];
                                    String month = line2.split(" ")[1];
                                    String year = line2.split(" ")[2];
                                    String id = line2.split(" ")[3];
                                    String data = line2.split(" ")[4];

                                    evenements.add(new Evenement(Integer.valueOf(day), Integer.valueOf(month),
                                            Integer.valueOf(year), Integer.valueOf(id), Long.valueOf(data)));

                                }
                                evenements.add(new Evenement(0, 0, 0, null, 0L));
                        		/*
                        		 * Planning
                        		 */
                                planning.add(new Planning(16, 18, 27, 7, 15, 3, 1L));
                                planning.add(new Planning(18, 23, 27, 7, 15, 4, 1L));
                                planning.add(new Planning(8, 16, 27, 7, 15, 1, 0L));
                                planning.add(new Planning(8, 16, 28, 7, 15, 1, 0L));
                                for (int a = 0; a < planning.size(); a++) {
                                    datapl.add(planning.get(a).getHour_start() + " " + planning.get(a).getHour() + " " + planning.get(a).getDay() + " "
                                            + planning.get(a).getMonth() + " " + planning.get(a).getYear() + " " + planning.get(a).getId() + " " + planning.get(a).getData());
                                }
                                planning.add(new Planning(0, 0, 0, 0, 0, null, 0L));

                                Collections.sort(datapl, new StringComparator());
                                Collections.reverse(datapl);

                                planning.clear();
                                for (int i = 0; i < datapl.size(); i++) {
                                    String line2 = datapl.get(datapl.size()-(i+1));

                                    String hour_start = line2.split(" ")[0];
                                    String hour = line2.split(" ")[1];
                                    String day = line2.split(" ")[2];
                                    String month = line2.split(" ")[3];
                                    String year = line2.split(" ")[4];
                                    String id = line2.split(" ")[5];
                                    String data = line2.split(" ")[6];

                                    planning.add(new Planning(Integer.valueOf(hour_start), Integer.valueOf(hour), Integer.valueOf(day), Integer.valueOf(month),
                                            Integer.valueOf(year), Integer.valueOf(id), Long.valueOf(data)));
                                }
                                planning.add(new Planning(0, 0, 0, 0, 0, null, 0L));

                                new Fenetre();
                                /*
                                JFrame frame = new JFrame();
                                frame.setTitle("YoutuberTycoon V0.0.2");
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                frame.pack();
                                frame.setLocationRelativeTo(null);
                                frame.setResizable(false);
                                frame.setVisible(true);
                                */
                            }

                    } catch (IOException ex) {
                        notifyObservers(ex);
                    }
                }
            };
            receivingThread.start();
        }

        @Override
        public void notifyObservers(Object arg) {
            super.setChanged();
            super.notifyObservers(arg);
        }

        /**
         * Send a line of text
         */
        public void send(String text) {
            try {
                outputStream.write((text + CRLF).getBytes());
                outputStream.flush();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }

        /**
         * Close the socket
         */
        public void close() {
            try {
                socket.close();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
    }

    /**
     * Chat client UI
     */
    static class ChatFrame extends JFrame implements Observer {

        private JTextArea textArea;
        private JTextField inputTextField;
        private JButton sendButton;
        private ChatAccess chatAccess;

        public ChatFrame(ChatAccess chatAccess) {
            this.chatAccess = chatAccess;
            chatAccess.addObserver(this);
            buildGUI();
        }

        /* Builds the user interface */

        private void buildGUI() {
            textArea = new JTextArea(20, 50);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            add(new JScrollPane(textArea), BorderLayout.CENTER);

            Box box = Box.createHorizontalBox();
            add(box, BorderLayout.SOUTH);
            inputTextField = new JTextField();
            sendButton = new JButton("Envoyé");
            box.add(inputTextField);
            box.add(sendButton);

            // Action for the inputTextField and the goButton
            ActionListener sendListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String str = inputTextField.getText();
                    if (str != null && str.trim().length() > 0)
                        chatAccess.send(str);
                    inputTextField.selectAll();
                    inputTextField.requestFocus();
                    inputTextField.setText("");
                }
            };
            inputTextField.addActionListener(sendListener);
            sendButton.addActionListener(sendListener);

            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    chatAccess.close();
                }
            });
        }

        /**
         * Updates the UI depending on the Object argument
         */
        public void update(Observable o, Object arg) {
            final Object finalArg = arg;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    textArea.append(finalArg.toString());
                    textArea.append("\n");
                }
            });
        }
    }
}