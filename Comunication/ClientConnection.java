import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientConnection(Socket socket){
        this.socket = socket;
        try{
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendFile(){
        try {
        sendMenu();
        int index = getSelectedFileIndex();
        sendSelectedFile(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendSelectedFile(int index){
        File[] fileList = new File(TCPServer.FILES_PATH).listFiles();
        File selectedFile = fileList[index];
        try(FileInputStream fileIn = new FileInputStream(selectedFile)){
        long fileSize = selectedFile.length();
        out.writeUTF(selectedFile.getName());
        out.writeLong(fileSize);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while((bytesRead = in.read(buffer)) != -1){
            out.write(buffer, 0, bytesRead);
        }
        out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private int getSelectedFileIndex(){
        try{
        String input = in.readUTF();
        return Integer.parseInt(input) - 1;
        }catch (IOException e){
            e.printStackTrace();
            return 0;
        }
    }

    private void sendMenu(){
        String menu = "         ARQUIVOS             \n";
        File[] fileList = new File(TCPServer.FILES_PATH).listFiles();
    
        if (fileList == null || fileList.length == 0) {
            menu += "Nenhum arquivo disponível no servidor.\n";
            try {
                out.writeUTF("0"); // número de arquivos
                out.writeUTF(menu);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
    
        for(int i = 0; i < fileList.length; i++){
            menu += String.format(" %d - %s\n", i+1, fileList[i].getName());
        }
    
        try {
            out.writeUTF(String.valueOf(fileList.length)); // envia quantidade de arquivos
            out.writeUTF(menu); // envia o menu
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
