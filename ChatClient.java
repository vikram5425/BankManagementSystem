import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345; 

    private BufferedReader in;
    private PrintWriter out;
    private Scanner consoleInput;
    private String username;

    public ChatClient() {
        consoleInput = new Scanner(System.in);
    }

    public void startClient() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true); 
          
            Thread listenerThread = new Thread(this::listenForMessages);
            listenerThread.start();

           
            String line;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("SUBMITNAME")) {
                    System.out.print("Enter your username: - ChatClient.java:34");
                    username = consoleInput.nextLine();
                    out.println(username);
                    break; 
                } else {
                    System.out.println(line); 
                }
            }

            
            System.out.print("You are now connected. Type your messages: - ChatClient.java:44");
            while (consoleInput.hasNextLine()) {
                String message = consoleInput.nextLine();
                if (message.equalsIgnoreCase("quit")) {
                    break; 
                }
                out.println(message); 
            }

        } catch (IOException e) {
            System.err.println("Client error: - ChatClient.java:54" + e.getMessage());
        } finally {
            System.out.println("Disconnected from server. - ChatClient.java:56");
            consoleInput.close(); 
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (IOException e) { 
                System.err.println("Error closing client streams: - ChatClient.java:62" + e.getMessage());
            }
        }
    }

    
    private void listenForMessages() {
        try {
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
               
                if (!serverMessage.startsWith("SUBMITNAME")) {
                    System.out.println(serverMessage);
                }
            }
        } catch (IOException e) { 
            
            System.err.println("Server connection lost or error receiving message: - ChatClient.java:79" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.startClient();
    }
}
