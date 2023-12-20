package org.midasvision.cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClienteTCP {
    public static void main(String[] args) throws Exception{

        Socket socket = new Socket("localhost", 4000);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream out = new PrintStream(socket.getOutputStream());

        System.out.println("Enviando dados ao servidor...");

        out.println("PAGAMENTO");
        out.println(2);
        out.println("avnerclslima;9999999999999999;10/25;2;512.45");
        out.println("avnerclslima;9999999999999999;10/25;10;5000");

        String status = in.readLine();

        if(status.equals("OK")){
            System.out.println("Pagamento realizado com sucesso!");
        } else {
            System.out.println("Pagamento não efetuado: " + status);
        }

        socket.close();
    }
}
