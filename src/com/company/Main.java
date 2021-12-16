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
/*            boolean login = false;
            login = start(socket);*/
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream());
        while (true){
            System.out.println(ois.readUTF());
            String str;
            str = in.nextLine();
            oos.writeUTF(str);
            oos.flush();
            if (str.equalsIgnoreCase("/exit"))
                break;
            if (str.equalsIgnoreCase("/cat")) {
                oos.writeUTF(str);
                oos.flush();
                Cat cat = new Cat();
                String specs = ois.readUTF();
                System.out.println(specs);
                cat.fromString(specs);
                if (cat.isReal() == true)
                    cat.upload();
                else
                    System.out.println("Такого питомца не существует!");
            }
        }
        }
        catch (SocketException exception)
        {
            System.out.println("Ошибка соединения!");
        }
    }
    static boolean auth(Socket socket) throws IOException {
        DataOutputStream autO = new DataOutputStream(socket.getOutputStream());
        DataInputStream autI = new DataInputStream(socket.getInputStream());
        Scanner in = new Scanner(System.in);
        System.out.println("Login:");
        String login = in.nextLine();
        autO.writeUTF(login);
        if (autI.readUTF().equalsIgnoreCase("copy")) {
            System.out.println("Password:");
            String password = in.nextLine();
            autO.writeUTF(password);
            return true;
        }
        return false;
    }
    static boolean start (Socket socket) throws IOException {
        Scanner in = new Scanner(System.in);
        DataOutputStream autO = new DataOutputStream(socket.getOutputStream());
        System.out.println("[1]-login \n" +
                "[2]-register");
        int key = in.nextInt();
        boolean login = false;
        while (login == false) {
            switch (key) {
                case (1):
                    autO.writeUTF("login");
                    while (auth(socket) != true) {
                        auth(socket);
                    }
                    login = true;
                    break;
                case (2):
                    String quit = null;
                    autO.writeUTF("register");
                    while (auth(socket) != true || quit.equalsIgnoreCase("!quit")) {
                        auth(socket);
                        System.out.println("Для выхода напишите: !quit");
                        quit = in.nextLine();
                    }
                    login = true;
                    break;
                case (3112440):
                    autO.writeUTF("SUser");
                    break;
            }

        }
        return login;
    }
}
