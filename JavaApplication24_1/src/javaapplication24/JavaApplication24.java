
package javaapplication24;

import java.util.ArrayList;
import static java.util.Objects.hash;
import java.util.Scanner;
import static javaapplication24.Message.CheckMessageID;
import static javaapplication24.Message.generateMessageHash;
import static javaapplication24.Message.processMessage;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;

public class JavaApplication24 {

    public static void main(String[] args) {
        Login CheckUsername = new Login();
        Scanner scan = new Scanner(System.in);
        String username;
        String password;
        String cellnumber;
        String checkUsername;
        String checkPassword;
        String checkNumber;
        int NumberofAttempts;

        do {
            System.out.println("Enter your username");
            username = scan.nextLine();
        } while (!CheckUsername.checkUsername(username));

        Login Checkpassword = new Login();
        do {
            System.out.println("Enter your password");
            password = scan.nextLine();
        } while (!Checkpassword.Checkpassword(password));

        Login Checkcellnumber = new Login();
        do {
            System.out.println("Enter your cell number");
            System.out.print("+27");
            cellnumber = scan.nextLine();
        } while (!Checkcellnumber.checkcellnumber(cellnumber));

        NumberofAttempts = 0;
        do {
            System.out.println("Enter the username that you captured ");
            checkUsername = scan.nextLine();
            if (!checkUsername.equals(username)) {
                System.out.println("Your username is incorrect");
                NumberofAttempts++;
            }
        } while (!checkUsername.equals(username) && NumberofAttempts < 2);

        if (NumberofAttempts == 2) {
            System.out.println("Your login was unsuccessful");
            return;
        }

        NumberofAttempts = 0;
        do {
            System.out.println("Enter your password");
            checkPassword = scan.nextLine();
            if (!checkPassword.equals(password)) {
                System.out.println("Your password is incorrect");
                NumberofAttempts++;
            }
        } while (!checkPassword.equals(password) && NumberofAttempts < 2);

        if (NumberofAttempts == 2) {
            System.out.println("Your login was unsuccessful");
            return;
        }

        NumberofAttempts = 0;
        do {
            System.out.println("Enter the captured number");
            System.out.print("+27");
            checkNumber = scan.nextLine();
            if (!checkNumber.equals(cellnumber)) {
                System.out.println("Incorrect number");
                NumberofAttempts++;
            }
        } while (!checkNumber.equals(cellnumber) && NumberofAttempts < 2);

        if (NumberofAttempts == 2) {
            System.out.println("Your login was unsuccessful");
            return;
        }

        System.out.println("Welcome " + username);
        System.out.println("welcome to the quickchart");
        ArrayList<Message.MessageData> sentMessages = new ArrayList<>();
        ArrayList<Message.MessageData> storedMessages = new ArrayList<>();
        ArrayList<Message.MessageData> disregardedMessages = new ArrayList<>();

        String idPrefix = "MSG";
        long messageId = 5678943298L;

        Message msg = new Message();
        String cellNumber = JOptionPane.showInputDialog("Enter your cell number (9 digits, without +27):");

        if (!msg.checkcellnumber(cellNumber)) {
            return;
        }

        int totalMessages = 0;
        while (true) {
            try {
                String input = JOptionPane.showInputDialog("How many messages would you like to enter?");
                if (input == null) return;
                totalMessages = Integer.parseInt(input);
                if (totalMessages <= 0) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid positive number.");
            }
        }

        for (int i = 1; i <= totalMessages; i++) {
            Message.processMessage(idPrefix, i, sentMessages, storedMessages, disregardedMessages, messageId, cellNumber);
        }

        ArrayList<Message.MessageData> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(storedMessages);
        allMessages.addAll(disregardedMessages);

        while (true) {
            String[] menuOptions = {
                "1. Show All Message Hashes",
                "2. Show All Cell Numbers",
                "3. Show Longest Message",
                "4. Show Summary Report",
                "5. Show Sent Messages",
                "6. Show Stored Messages",
                "7. Show Disregarded Messages",
                "8. Save Summary Report to File",
                "9. Show Sent Messages with Cell Numbers",
                "10. Delete Sent Message by Hash",
                "11. Exit"
            };

            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Choose an option:",
                    "Message Report Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    menuOptions,
                    menuOptions[0]
            );

            switch (choice) {
                case 0:
                    StringBuilder hashes = new StringBuilder("All Message Hashes:\n\n");
                    for (Message.MessageData m : allMessages) {
                        hashes.append(m.messageHash).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, hashes.toString(), "Message Hashes", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 1:
                    Message.showAllCellNumbers(allMessages);
                    break;
                case 2:
                    Message.showLongestMessage(allMessages);
                    break;
                case 3:
                    Message.showSummaryReport(sentMessages, storedMessages, disregardedMessages);
                    break;
                case 4:
                    Message.showMessages("Sent Messages", sentMessages);
                    break;
                case 5:
                    Message.showMessages("Stored Messages", storedMessages);
                    break;
                case 6:
                    Message.showMessages("Disregarded Messages", disregardedMessages);
                    break;
                case 7:
                    saveSummaryToFile(sentMessages, storedMessages, disregardedMessages);
                    break;
                case 8:
                    Message.showSentMessagesWithCellNumbers(sentMessages);
                    break;
                case 9:
                    Message.deleteSentMessageByHash(sentMessages);
                    break;
                case 10:
                default:
                    JOptionPane.showMessageDialog(null, "Thank you. Exiting program.");
                    System.exit(0);
            }
        }
    }

    public static void saveSummaryToFile(
            ArrayList<Message.MessageData> sent,
            ArrayList<Message.MessageData> stored,
            ArrayList<Message.MessageData> disregarded
    ) {
        int totalSent = sent.size();
        int totalStored = stored.size();
        int totalDisregarded = disregarded.size();
        int totalMessages = totalSent + totalStored + totalDisregarded;

        String report = String.format(
            "---- Summary Report ----\n\n" +
            "Total Messages Processed: %d\n" +
            "Messages Sent: %d\n" +
            "Messages Stored: %d\n" +
            "Messages Disregarded: %d\n",
            totalMessages, totalSent, totalStored, totalDisregarded
        );

        try (FileWriter writer = new FileWriter("summary_report.txt")) {
            writer.write(report);
            JOptionPane.showMessageDialog(null, "Report saved to 'summary_report.txt'");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage());
        }
    }
}