package com.are.nfcreader;

import java.util.List;
import java.math.BigInteger;
import javax.smartcardio.*;

public class tagscan {

    static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
    }

    public static void main(String[] args) {

        try {

            // Display the list of terminals
            TerminalFactory factory = TerminalFactory.getDefault();
            List<CardTerminal> terminals = factory.terminals().list();
            System.out.println("Terminals: " + terminals);

            // Use the first terminal
            CardTerminal terminal = terminals.get(0);

            while (true) {
                terminal.waitForCardPresent(0);
                try {
                    Card card = terminal.connect("*");
                    CardChannel channel = card.getBasicChannel();

                    CommandAPDU command = new CommandAPDU(new byte[]{(byte) 0xFF, (byte) 0xCA, (byte) 0x00, (byte) 0x00, (byte) 0x04});

                    ResponseAPDU response = channel.transmit(command);

                    //byte[] byteArray = response.getBytes();

                    //JOptionPane.showMessageDialog(null, bytesToHex( byteArray ) );
                    System.out.println("UID: " + bin2hex(response.getData()));

                    //System.out.println("Name : " + bytesToHex.convertHexToString(byteArray));
                    Thread.sleep(1000);
                } catch (CardException e) {
                    e.printStackTrace();
                } finally {
                }

            }

            // Connect wit hthe card
//            Card card = terminal.connect("*");
//            System.out.println("Card: " + card);
//            CardChannel channel = card.getBasicChannel();
//
//            // Send test command
//            ResponseAPDU response = channel.transmit(new CommandAPDU(new byte[]{(byte) 0xFF, (byte) 0xCA, (byte) 0x00, (byte) 0x00, (byte) 0x00}));
//            System.out.println("Response: " + response.toString());
//
//            if (response.getSW1() == 0x63 && response.getSW2() == 0x00) {
//                System.out.println("Failed");
//            }
//
//            System.out.println("UID: " + bin2hex(response.getData()));

            // Disconnect the card
            //card.disconnect(false);

        } catch (Exception e) {

            System.out.println("Ouch: " + e.toString());

        }
    }
}
