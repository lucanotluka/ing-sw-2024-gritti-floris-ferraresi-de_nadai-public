package it.polimi.ingsw.client;

import it.polimi.ingsw.listener.Event;
import it.polimi.ingsw.listener.EventType;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * In this class it is asked which communication and user interface the client wants to use
 *
 * @author Marco Ferraresi, Luca Gritti, Fabio Marco Floris, Angelo De Nadai
 */
public class Client {
    /**
     * This is the main where all these questions are asked
     *
     * @param args
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        //  -----    CHOICE OF THE COMMUNICATION TECHNOLOGY   ----

        System.out.println("Do you prefer to connect using Socket or RMI?\n[1]. Socket\n[2]. RMI\n ");
        String response = null;
        int choice = 0;
        Socket socket = null;

        do {
            try {
                response = sc.nextLine();
                choice = Integer.parseInt(response);
                if (choice != 1 && choice != 2)
                    System.out.println("Reply \033[3m1\033[0m or \033[3m2\033[0m: ");

            } catch(NumberFormatException e){
                System.out.println("Reply \033[3m1\033[0m or \033[3m2\033[0m: ");
            }
        } while(choice != 1 && choice != 2);


        //  -----    CREATION OF THE CHOSEN COMMUNICATION TECHNOLOGY   ----

        CommunicationSetting channel = null;
        try {
            String input = "";
            do {
                System.out.println("Insert remote IP (leave empty for localhost)");
                input = new Scanner(System.in).nextLine();
            } while (!input.equals("") && !isValidIP(input));
            if (input.equals(""))
                input = "127.0.0.1";

                // ------- SOCKET! --------

            if (choice == 1) {

                socket = new Socket(input, 50866);
                channel = new ClientSocket(socket);

            } else {

                // --------- RMI! ----------


                ClientRMI clientRMI = null;

                //creation object Client
                try {
                    clientRMI = new ClientRMI();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                clientRMI.connect(input, 50865);
                channel = clientRMI;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        //  -----    CREATION OF THE CHOSEN USER INTERFACE   ----

        System.out.println("Would you like to use GUI or TUI?\n[1]. GUI\n[2]. TUI\n");
        choice = 0;
        do {
            try {
                response = sc.nextLine();
                choice = Integer.parseInt(response);
                if (choice != 1 && choice != 2) {
                    System.out.println("Reply 1 or 2...");
                }
            } catch (NumberFormatException e){
                System.out.println("Reply 1 or 2...");
            }
        }
        while (choice != 1 && choice != 2);

        //      the view is now dinamically implemented either as TUI or GUI
        View view = new View(choice);

        try {
            channel.setView(view);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


        //  -----    PRINT OF THE GAME AND THE CREDITS   ----
        Event eventPrintTitle = new Event(EventType.PRINT_TITLE_AND_CREDITS, "server");
        view.manageEvent(eventPrintTitle);



        //  -----    START EVERYTHING!   -----
        try {
            channel.openCommunication();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Check if the IP is valid
     *
     * @param input The IP
     * @return A boolean: true if the IP is correct; false if it's not
     */

    private static boolean isValidIP(String input) {
        List<String> parsed;
        parsed = Arrays.stream(input.split("\\.")).toList();
        if (parsed.size() != 4) {
            return false;
        }
        for (String part : parsed) {
            try {
                Integer.parseInt(part);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}
