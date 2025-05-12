import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClient {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Scanner sc;

    public TCPClient(){
        try{
        socket = new Socket("127.0.0.1", TCPServer.PORT);
        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        out = new DataOutputStream(socket.getOutputStream());
        sc = new Scanner(System.in);
        getFile();
        } catch(UnknownHostException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void getFile(){
        try{
        double percent = 0;
        String filesLen = in.readUTF();
        int maxFiles = Integer.parseInt(filesLen);
        String menu = in.readUTF();
        System.out.println(menu);
        int userSelection = -1;
        boolean isSelectionCorrect = false;
        while (!isSelectionCorrect){
            System.out.println("Selecione um arquivo para a transferência");
            userSelection = sc.nextInt();
            isSelectionCorrect = userSelection>0 && userSelection <= maxFiles;
        }

        out.writeUTF(String.valueOf(userSelection));
        out.flush();

        System.out.println(" -- SEND START --");

        String fileName = in.readUTF();
        long fileSize = in.readLong();

        File destDir = new File("dataClient");
        if (!destDir.exists()) destDir.mkdirs();

        File outputFile = new File(destDir, fileName);
        try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            long totalRead = 0;

            while (totalRead < fileSize && (bytesRead = in.read(buffer, 0, (int) Math.min(buffer.length, fileSize - totalRead))) != -1) {
                fileOut.write(buffer, 0, bytesRead);
                totalRead += bytesRead;
                percent = (totalRead/fileSize) * 100;
                System.out.println(percent);
            }
        }

        System.out.println("Arquivo recebido e salvo em: " + outputFile.getAbsolutePath());
        System.out.println(" -- SEND END --");
    }catch(IOException e){
        e.printStackTrace();
    }
    terminateConnection();
    }

    public void terminateConnection(){
        try {
            in.close();
            out.close();
            socket.close();
            sc.close();
            System.out.println("Conexão encerrada.");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }

    public void startClient(){
        getFile();
    }

    public static void main(String[] args) {
        TCPClient client = new TCPClient();
        client.startClient();
    }
}
