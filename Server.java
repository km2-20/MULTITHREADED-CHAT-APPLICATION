package serverr;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamInputStream;
import javazoom.jl.decoder.BitstreamReader;

public class Server {

    private JFrame serverFrame;
    private JTextArea ta;
    private JScrollPane scrollPane;
    private JTextField tf;

    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public Server() {
        setupGUI();
    }

    // Setup the GUI for the server
    private void setupGUI() {
        serverFrame = new JFrame("Server");
        serverFrame.setSize(500, 500);
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ta = new JTextArea();
        ta.setEditable(false);
        ta.setFont(new Font("Arial", Font.PLAIN, 16));
        scrollPane = new JScrollPane(ta);
        serverFrame.add(scrollPane, BorderLayout.CENTER);

        tf = new JTextField();
        tf.setEditable(false);
        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(tf.getText());
                ta.append("Server: " + tf.getText() + "\n");
                tf.setText("");
            }
        });
        serverFrame.add(tf, BorderLayout.SOUTH);

        serverFrame.setVisible(true);
    }

    // Wait for client connection
    public void waitingForClient() {
        try {
            String ipAddress = getIpAddress();
            serverSocket = new ServerSocket(1111);
            ta.setText("Server IP Address: " + ipAddress + "\nWaiting for client...");
            socket = serverSocket.accept();
            ta.setText("Client Connected!\n----------------------------------------------------\n");
            tf.setEditable(true);
            setupIOStreams();
        } catch (Exception e) {
            ta.append("Error: " + e.getMessage() + "\n");
        }
    }

    // Get the server's IP address
    public String getIpAddress() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (Exception e) {
            ta.append("Error: " + e.getMessage() + "\n");
            return "Unknown IP";
        }
    }

    // Set up the input/output streams
    private void setupIOStreams() {
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            startReadingMessages();
        } catch (Exception e) {
            ta.append("Error setting up IO streams: " + e.getMessage() + "\n");
        }
    }

    // Start reading messages from the client
    private void startReadingMessages() {
        new Thread(() -> {
            while (true) {
                try {
                    String message = dis.readUTF();
                    ta.append("Client: " + message + "\n");
                    playChatSound();
                } catch (Exception e) {
                    ta.append("Error reading message: " + e.getMessage() + "\n");
                    break;
                }
            }
        }).start();
    }

    // Send message to the client
    public void sendMessage(String message) {
        try {
            dos.writeUTF(message);
            dos.flush();
        } catch (Exception e) {
            ta.append("Error sending message: " + e.getMessage() + "\n");
        }
    }

    // Play sound when a message is received or sent
    public void playChatSound() {
        try {
            File file = new File("src/sound/chat_sound.mp3");
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                BitstreamInputStream bitstreamInputStream = new BitstreamInputStream(fis);
                BitstreamReader reader = new BitstreamReader();
                reader.readFile(bitstreamInputStream);
                fis.close();
            }
        } catch (Exception e) {
            ta.append("Error playing sound: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.waitingForClient();
    }
}
