import java.io.*;
import java.net.*;

public class TCPServer {

    private ServerSocket serverSocket;
    public static final int PORT = 3030;
    public static final String FILES_PATH = "dataServer";  
   
    public TCPServer() {
        try {
            serverSocket = new ServerSocket(PORT);  
            System.out.println("Servidor iniciado na porta " + PORT);
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void acceptConnections() {
        while (true) {
            try {
                System.out.println("Aguardando conexão...");
                Socket clientSocket = serverSocket.accept(); 
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                new Thread(() -> {
                    ClientConnection client = new ClientConnection(clientSocket);
                    client.sendFile();  
                }).start();

            } catch (IOException e) {
                System.err.println("Erro ao aceitar conexão: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

  
    public void startServer() {
        acceptConnections();  
    }

    public static void main(String[] args) {
        TCPServer server = new TCPServer(); 
        server.startServer(); 
    }
}