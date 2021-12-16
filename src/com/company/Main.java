package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream());
            Cat cat = new Cat();
        while (true){
            System.out.println(ois.readUTF());
            String str;
            str = in.nextLine();
            String [] sMas = str.split(" ");
            if (sMas[0].equalsIgnoreCase("/cat")) {
                oos.writeUTF(str);
                String specs = ois.readUTF();
                cat.fromString(specs);
                if (cat.isReal() == true) {
                    cat.upload();
                    cat.display();
                }
                else
                    System.out.println("Такого питомца не существует!");
            }
            oos.writeUTF(str);
            oos.flush();

            if (str.equalsIgnoreCase("/exit"))
                break;

        }
        }
        catch (SocketException exception)
        {
            System.out.println("Ошибка соединения!");
        }
    }
}
