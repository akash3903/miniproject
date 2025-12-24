import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 8080;
    private static Set<ClientHandler> clientHandlers = ConcurrentHashMap.newKeySet();
    private static int clientCounter = 0;

    public static void main(String[] args) {
        System.out.println("Chat Server started on port " + PORT);
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Request username
                out.println("Enter your username:");
                username = in.readLine();
                
                if (username == null || username.trim().isEmpty()) {
                    username = "User" + (++clientCounter);
                }

                System.out.println(username + " connected from " + socket.getInetAddress());
                broadcast(username + " has joined the chat!", this);

                // Handle messages
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("/quit")) {
                        break;
                    }
                    System.out.println(username + ": " + message);
                    broadcast(username + ": " + message, this);
                }
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                cleanup();
            }
        }

        private void broadcast(String message, ClientHandler sender) {
            for (ClientHandler client : clientHandlers) {
                if (client != sender && client.out != null) {
                    client.out.println(message);
                }
            }
        }

        private void cleanup() {
            clientHandlers.remove(this);
            broadcast(username + " has left the chat.", this);
            System.out.println(username + " disconnected");
            
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        public void sendMessage(String message) {
            if (out != null) {
                out.println(message);
            }
        }
    }
}