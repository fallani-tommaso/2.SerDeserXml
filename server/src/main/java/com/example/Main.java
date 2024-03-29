package com.example;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("SERVER PARTITO IN ESECUZIONE...");
            ServerSocket ss = new ServerSocket(8080);

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

            while (true) {
                try {
                    Socket s = ss.accept();
                    System.out.println("Un client si è connesso");

                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    DataOutputStream out = new DataOutputStream(s.getOutputStream());

                    String linea1 = in.readLine();
                    System.out.println(linea1);
                    String vuota = "";
                    do {
                        vuota = in.readLine();
                        System.out.println(vuota);

                    } while (!vuota.equals(""));

                    // analizzare il contenuto di stringa2
                    String[] arrayLinea1 = linea1.split(" ");
                    System.out.println("La stringa numero 2 è composta da: " + arrayLinea1[1]);
                    // controllo se viene richiesto un file json
                    if (arrayLinea1[1].contains(".json")) {
                        JsonMapper(c1);
                    }
                    File file = new File("htdocs" + arrayLinea1[1]);
                    if (file.exists())
                        sendBinaryFile(out, file);
                    else {
                        String msg = "The resource was not found";
                        out.writeBytes("HTTP/1.1 404 Not Found" + "\n");
                        out.writeBytes("Date: " + LocalDateTime.now().toString() + "\n");
                        out.writeBytes("Server: meucci-server" + "\n");
                        out.writeBytes("Content-Type: text/plain; charset=UTF-8" + "\n");
                        out.writeBytes("Content-Length: " + msg.length() + "\n");
                        out.writeBytes("" + "\n");
                        out.writeBytes(msg + "\n");
                    }
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

    public static void sendBinaryFile(DataOutput output, File file) throws IOException {
        output.writeBytes("HTTP/1.1 200 OK" + "\n");
        output.writeBytes("Content-Length: " + file.length() + "\n");
        output.writeBytes("Content-Type: " + getContentType(file) + "\n");
        output.writeBytes("\n");
        InputStream input = new FileInputStream(file);
        byte[] buf = new byte[8192];
        int n;
        while ((n = input.read(buf)) != -1) {
            output.write(buf, 0, n);
        }
        input.close();
    }

    public static String getContentType(File f) {
        String[] s = f.getName().split("\\.");
        String ext = s[s.length - 1];
        switch (ext) {
            case "html":
                return "text/html";
            case "css":
                return "text/css";
            case "png":
                return "image/png";
            case "jpg":
                return "image/jpg";
            case "jpeg":
                return "image/jpeg";
            case "gif":
                return "image/gif";
            case "js":
                return "test/js";
        }
        return "text/plain";
    }

    public void JsonMapper(Classe classe) {
        String s1;
        try {
            System.out.println("Serializzazione della lista...");
            ObjectMapper Mapper = new ObjectMapper();
            s1 = Mapper.writeValueAsString(classe);
            Mapper.writeValue(new File("server/htdocs/Classe.json"), obj);
            System.out.println("Lista serializzata: " + s1);
        } catch (Exception i) {
            i.printStackTrace();
        }
    }
}