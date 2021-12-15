package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket socket= new Socket();
        Scanner in = new Scanner(System.in);
        try {
            System.out.println("Введит ip adr сервера");
            String str = in.nextLine();
            try {
                socket = new Socket(str, 8000);
            }
            catch (SocketException exception){
                System.out.println("Указан неверный ip adr|port");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
        while (true){
            String str;
            System.out.println("!exit - выход");
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream());

            System.out.println("Введите сообщение>>");
            str = in.nextLine();
            if (str.equalsIgnoreCase("!exit"))
                break;
            oos.writeUTF(str);
            oos.flush();
            System.out.println(ois.readUTF());
            System.out.println(ois.readUTF());
        }
        }
        catch (SocketException exception)
        {
            System.out.println("Ошибка соединения!");
        }
    }
}
