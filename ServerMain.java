package serverr;

public class ServerMain {

    public static void main(String[] args) {
        Server server = new Server();  // Start the server
        server.waitingForClient();     // Wait for a client to connect
    }
}
