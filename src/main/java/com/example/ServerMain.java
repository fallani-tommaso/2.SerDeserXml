package com.example;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ServerMain {
    public static void main(String[] args) {
        try {
            System.out.println("SERVER PARTITO IN ESECUZIONE...");
            ServerSocket ss = new ServerSocket(8080);

            while (true) {
                try {
                    Socket s = ss.accept();
                    System.out.println("Un client si è connesso");

                    DataOutputStream out = new DataOutputStream(s.getOutputStream());

                    Alunno a1 = new Alunno("Claudio", "Benvenuti", new Date(1979, 9, 22));
                    Alunno a2 = new Alunno("Isacco", "Pieri", new Date(2005, 4, 16));
                    Alunno a3 = new Alunno("Davide", "Aiazzi", new Date(2005, 5, 6));
                    Alunno a4 = new Alunno("Gabriele", "Lodde", new Date(2005, 6, 16));
                    ArrayList<Alunno> alunni = new ArrayList<>();
                    alunni.add(a1);
                    alunni.add(a2);
                    alunni.add(a3);
                    alunni.add(a4);
                    Classe c1 = new Classe(5, "BIA", "12TW", alunni);
                    ObjectMapper objectMapper = new ObjectMapper();
                    String stringInviare = objectMapper.writeValueAsString(c1);

                    out.writeBytes(stringInviare + "\n");

                    System.out.println("Stringa inviata");

                    s.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errore");
            System.exit(1);
        }
    }
}