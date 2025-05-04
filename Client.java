import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import javazoom.jl.player.Player;

public class Client {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public Client() {
        String ip = JOptionPane.showInputDialog("Enter Server IP Address:");
        if (ip == null || ip.trim().isEmpty()) return;

        try {
            socket = new Socket(ip.trim(), 1111); // Connect to server
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            setupGUI(); // Set up the GUI for the chat window
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not connect to server.");
        }
    }

    private void setupGUI() {
        frame = new JFrame("Chat Client");
        chatArea = new JTextArea();
        inputField = new JTextField();

        chatArea.setEditable(false);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        frame.add(inputField, BorderLayout.SOUTH);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        inputField.addActionListener(e -> {
            sendMessage(inputField.getText());
            chatArea.append("Me: " + inputField.getText() + "\n");
            inputField.setText("");
        });

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeConnection();
            }
        });
    }

    // Start a thread to read incoming messages from the server
    public void startReadingMessages() {
        new Thread(() -> {
            try {
                String message;
                while ((message = dis.readUTF()) != null) {
                    chatArea.append("Server: " + message + "\n");
                    playSound(); // Play sound whenever a message is received
                }
            } catch (IOException e) {
                chatArea.append("Disconnected from server.\n");
                closeConnection(); // Close connection if server is disconnected
            }
        }).start();
    }

    // Send a message to the server
    private void sendMessage(String message) {
        try {
            dos.writeUTF(message);
            dos.flush();
        } catch (IOException e) {
            chatArea.append("Failed to send message.\n");
        }
    }

    // Play sound when a message is received
    private void playSound() {
        try {
            FileInputStream fis = new FileInputStream("sound/chat_sound.mp3");
            Player player = new Player(fis);
            player.play();
        } catch (Exception e) {
            System.out.println("Sound Error: " + e.getMessage());
        }
    }

    // Close the connection and release resources
    private void closeConnection() {
        try {
            if (dis != null) dis.close();
            if (dos != null) dos.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.out.println("Close Error: " + e.getMessage());
        }
    }
}
