package javaapplication24;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import java.util.ArrayList;

public class Message {
    
    public static boolean CheckMessageID(long messageId) {
        return messageId == 5678943298L;
    }

    public boolean checkcellnumber(String cellnumber) {
        if (cellnumber.length() == 9 && cellnumber.matches("^\\d{9}$")) {
            JOptionPane.showMessageDialog(null, "Cell phone number successfully added.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Cell number must be 9 digits (excluding +27).");
            return false;
        }
    }

    public static String generateMessageHash(String idPrefix, int messageNumber, String message) {
        String[] words = message.trim().split("\\s+");
        if (words.length == 0 || words[0].isEmpty()) {
            return "INVALID MESSAGE";
        }
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        return lastWord + idPrefix + ":" + messageNumber + "-" + firstWord;
    }

    public static class MessageData {
        public String messageId;
        public String messageHash;
        public String messageText;
        public String cellNumber;

        public MessageData(String messageId, String messageHash, String messageText, String cellNumber) {
            this.messageId = messageId;
            this.messageHash = messageHash;
            this.messageText = messageText;
            this.cellNumber = cellNumber;
        }
    }

    public static MessageData generateMessageData(String idPrefix, int messageNumber, String message, long messageId, String cellNumber) {
        String hash = generateMessageHash(idPrefix, messageNumber, message);
        return new MessageData(String.valueOf(messageId), hash, message, cellNumber);
    }

    public static void processMessage(
            String idPrefix,
            int messageNumber,
            ArrayList<MessageData> sentMessages,
            ArrayList<MessageData> storedMessages,
            ArrayList<MessageData> disregardedMessages,
            long messageId,
            String cellNumber) {

        String message = JOptionPane.showInputDialog("Enter message " + messageNumber + ":");
        if (message == null || message.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Message skipped.");
            return;
        }

        MessageData msgData = generateMessageData(idPrefix, messageNumber, message, messageId, cellNumber);
        String[] options = {"Send", "Disregard", "Store", "Exit"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "What would you like to do with this message?\n\n" +
                        "Message: " + message +
                        "\nHash: " + msgData.messageHash,
                "Choose an option",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (choice) {
            case 0:
                JOptionPane.showMessageDialog(null,
                        "Message sent:\n\n" +
                                "Message ID: " + msgData.messageId +
                                "\nMessage Hash: " + msgData.messageHash +
                                "\nMessage Text: " + msgData.messageText +
                                "\nCell Number: +27" + msgData.cellNumber);
                sentMessages.add(msgData);
                break;
            case 1:
                JOptionPane.showMessageDialog(null,
                        "Message disregarded:\n\n" +
                                "Message ID: " + msgData.messageId +
                                "\nMessage Hash: " + msgData.messageHash +
                                "\nMessage Text: " + msgData.messageText +
                                "\nCell Number: +27" + msgData.cellNumber);
                disregardedMessages.add(msgData);
                break;
            case 2:
                JOptionPane.showMessageDialog(null,
                        "Message stored for later:\n\n" +
                                "Message ID: " + msgData.messageId +
                                "\nMessage Hash: " + msgData.messageHash +
                                "\nMessage Text: " + msgData.messageText +
                                "\nCell Number: +27" + msgData.cellNumber);
                storedMessages.add(msgData);
                break;
            case 3:
            default:
                JOptionPane.showMessageDialog(null, "Exiting application early.");
                System.exit(0);
        }
    }

    public static void showMessages(String title, ArrayList<MessageData> messages) {
        if (messages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages to show in: " + title);
            return;
        }

        StringBuilder sb = new StringBuilder("--- " + title + " ---\n\n");
        for (MessageData msg : messages) {
            sb.append("Message ID: ").append(msg.messageId)
              .append("\nMessage Hash: ").append(msg.messageHash)
              .append("\nMessage Text: ").append(msg.messageText)
              .append("\nCell Number: +27").append(msg.cellNumber)
              .append("\n\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString(), title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showAllCellNumbers(ArrayList<MessageData> allMessages) {
        if (allMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages available.");
            return;
        }

        StringBuilder sb = new StringBuilder("Cell Numbers:\n\n");
        for (MessageData m : allMessages) {
            sb.append("+27").append(m.cellNumber).append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString(), "All Cell Numbers", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showLongestMessage(ArrayList<MessageData> allMessages) {
        if (allMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages available.");
            return;
        }

        MessageData longest = allMessages.get(0);
        for (MessageData m : allMessages) {
            if (m.messageText.length() > longest.messageText.length()) {
                longest = m;
            }
        }

        JOptionPane.showMessageDialog(null,
                "Longest Message:\n\n" +
                        "Text: " + longest.messageText +
                        "\nLength: " + longest.messageText.length(),
                "Longest Message",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showSummaryReport(ArrayList<MessageData> sent, ArrayList<MessageData> stored, ArrayList<MessageData> disregarded) {
        int total = sent.size() + stored.size() + disregarded.size();

        String report = String.format(
                "---- Summary Report ----\n\n" +
                "Total Messages Processed: %d\n" +
                "Messages Sent: %d\n" +
                "Messages Stored: %d\n" +
                "Messages Disregarded: %d",
                total, sent.size(), stored.size(), disregarded.size()
        );

        JOptionPane.showMessageDialog(null, report, "Summary Report", JOptionPane.INFORMATION_MESSAGE);
    }

    // ✅ New Feature: Show sent messages with cell numbers
    public static void showSentMessagesWithCellNumbers(ArrayList<MessageData> sentMessages) {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages to display.");
            return;
        }

        StringBuilder sb = new StringBuilder("Sent Messages with Cell Numbers:\n\n");
        for (MessageData msg : sentMessages) {
            sb.append("Message Hash: ").append(msg.messageHash)
              .append("\nMessage Text: ").append(msg.messageText)
              .append("\nCell Number: +27").append(msg.cellNumber)
              .append("\n\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString(), "Sent Messages", JOptionPane.INFORMATION_MESSAGE);
    }

    // ✅ New Feature: Delete sent message by hash
    public static void deleteSentMessageByHash(ArrayList<MessageData> sentMessages) {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages available for deletion.");
            return;
        }

        String hashToDelete = JOptionPane.showInputDialog("Enter the message hash to delete:");
        if (hashToDelete == null || hashToDelete.trim().isEmpty()) {
            return;
        }

        boolean removed = false;
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).messageHash.equalsIgnoreCase(hashToDelete.trim())) {
                sentMessages.remove(i);
                removed = true;
                JOptionPane.showMessageDialog(null, "Message with hash '" + hashToDelete + "' deleted successfully.");
                break;
            }
        }

        if (!removed) {
            JOptionPane.showMessageDialog(null, "No sent message found with the specified hash.");
        }
    }
}


    
