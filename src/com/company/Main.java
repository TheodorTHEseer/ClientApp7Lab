package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.company.logs.home;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket socket= new Socket();
        Scanner in = new Scanner(System.in);
        Thread logs = new Thread(new logs());
        logs.start();
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
        File f = new File(home + File.separator + "Desktop" + File.separator +
                "CatsFolder");
        f.mkdir();
        try{
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream());
            Cat cat = new Cat();
        while (true){
            String input = ois.readUTF();
            System.out.println(input);
            String str;
            str = in.nextLine();
            String [] sMas = input.split(" ");
            if (sMas[0].equalsIgnoreCase("/cat")) {
                sMas[0]="";
                System.out.println(sMas[0]+sMas[1]);
                String specs = input;
                String [] params = specs.split(",");
                if (params[5].equalsIgnoreCase("0.0"))
                    System.out.println("Кошки не существует!");
                else {
                    cat.fromString(specs);
                    cat.upload();
                    cat.display();
                }
            }
            if (sMas[0].equalsIgnoreCase("//cat")){
                try {
                    List<String> catMas = Arrays.asList(sMas);
                    cat.fromString(catMas.get(5));
                    cat.upload();
                    cat.display();
                }
                    catch (Exception e){
                        System.out.println("Получен кот, но кот явно с браком" + e.getStackTrace());
                    }
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
