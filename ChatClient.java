import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner scanner;

    public ChatClient() {
        scanner = new Scanner(System.in);
    }

    public void start() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Connected to chat server!");

            // Start thread to receive messages
            Thread receiveThread = new Thread(new ReceiveHandler());
            receiveThread.start();
            
            String prompt = in.readLine();
            System.out.println(prompt);
            String username = scanner.nextLine();
            out.println(username);

            // Main thread
            String message;
            while (true) {
                message = scanner.nextLine();
                out.println(message);
                
                if (message.equalsIgnoreCase("/quit")) {
                    break;
                }
            }

            cleanup();
            
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    private void cleanup() {
        try {
            if (scanner != null) scanner.close();
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
            System.out.println("Disconnected from server.");
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }

    class ReceiveHandler implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.err.println("Connection lost.");
            }
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.start();
    }
}
