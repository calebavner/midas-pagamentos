package org.midasvision.servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServidorPagamentoTCP {
    public void run() throws Exception {

        ServerSocket server = new ServerSocket(4000);

        while(true) {
            Socket clientSocket = null;

            try {
                System.out.println("Aguardando requisições");
                clientSocket = server.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintStream out = new PrintStream(clientSocket.getOutputStream());

                String op = in.readLine();

                if(op.equals("PAGAMENTO")){
                    efetuarPagamento(in, out);
                } else {
                    System.out.println("Dados incompletos");
                }
            } finally {
                if(clientSocket != null){
                    clientSocket.close();
                }
            }
        }
    }

    private void efetuarPagamento(BufferedReader in, PrintStream out) {

        int numPagtos;

        try {
            numPagtos = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            out.print("FALHA " + e.getMessage());
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

        for(int i = 0; i < numPagtos; i++) {
            try {
                String dados = in.readLine();
                String[] tokens = dados.split(";");

                String nomeCliente = tokens[0];
                String numCartao = tokens[1];
                Date validadeCartao = sdf.parse(tokens[2]);
                int numParcelas = Integer.parseInt(tokens[3]);
                double valorCompra = Double.parseDouble(tokens[4]);

                System.out.println("Pagamento: " + (i + 1));
                System.out.println("Cliente: " + nomeCliente);
                System.out.println("Numero do Cartão: " + numCartao);
                System.out.println("Validade do cartão: " + validadeCartao);
                System.out.println("Numero de Parcelas: " + numParcelas);
                System.out.println("Valor da compra: " + valorCompra);

                Thread.sleep(1000);
                out.println("OK");
                System.out.println();
                System.out.println("***************************************");
                System.out.println();
            } catch (Exception e) {
                out.println("FALHA " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception{
        new ServidorPagamentoTCP().run();
    }
}
