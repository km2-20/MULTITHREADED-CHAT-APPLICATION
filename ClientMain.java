package client;

public class ClientMain {
    public static void main(String[] args) {
        // Create an instance of the Client class to start the application
        Client client = new Client();
        // Initialize the Input and Output streams (this is now done in the Client constructor)
        client.startReadingMessages();
    }
}
