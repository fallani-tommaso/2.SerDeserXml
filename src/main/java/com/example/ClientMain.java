package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientMain {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 8080);
            BufferedReader inServ = new BufferedReader(new InputStreamReader(s.getInputStream()));
            ObjectMapper objectMapper = new ObjectMapper();
            String stringaRicevuta = inServ.readLine();

            System.out.println("Stringa Ricevuta");

            Classe b = objectMapper.readValue(stringaRicevuta, Classe.class);
            System.out.println(b.numero);
            System.out.println(b.sezione);
            System.out.println(b.aula);

            s.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore");
            System.exit(1);
        }
    }
}