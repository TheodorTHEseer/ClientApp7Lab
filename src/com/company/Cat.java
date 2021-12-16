package com.company;

import java.io.*;

import static com.company.logs.home;

public class Cat implements Serializable {
    private String name;
    private String race;
    private double weight;


    public Cat(){}

    public String toString() {
        return "name," + name + ",race," + race +
                ",weight," +weight;
    }
    public void display(){
        System.out.println("Name: " + this.name + ", Race: " + this.race+", Weight: " + this.weight);
    }
    public void fromString(String catString){
        String [] CatMas = catString.split(",");
        this.name=CatMas[1];
        this.race=CatMas[3];
        this.weight=Double.valueOf(CatMas[5]);
    }
    public boolean isReal() {
        if (this.weight>0.0)
            return true;
        return false;
    }

    public void upload(){
        try {
            FileWriter fileWriter = new FileWriter(home + File.separator + "Desktop" + File.separator +
                    "CatsFolder" + File.separator + name +".txt", false);
            fileWriter.write(this.toString());
            fileWriter.close();
            logs.add("Cat|upload|Done");
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
            logs.add("Cat|upload|Failed");
        }
    }
}