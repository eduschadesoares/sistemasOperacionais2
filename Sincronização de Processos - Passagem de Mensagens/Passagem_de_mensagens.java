package trabalhoso;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Passagem_de_mensagens {

    public static void main(String args[]) {
        Fila fila = new Fila();
        Produtor produtor = new Produtor();
        produtor.start();
        new Consumidor(produtor, fila).start();
        fila.start();
    }

}

class Fila extends Thread {

    //TEMPO DE PROCESSAMENTO NA THREAD EM SEGUNDO
    public static final int TEMPO_VISUALIZACAO_FILA = 10;
    public static final int TEMPO_PRODUTOR = 1;
    public static final int TEMPO_CONSUMIDOR = 2;

    //TAMANHO DA FILA
    public static final int MAX = 5;

    //FILA DE MENSAGENS
    public static List fila = new ArrayList();

    @Override
    public void run() {
        try {
            while (true) {
                if (!Fila.fila.isEmpty()) {
                    try {
                        System.out.println("FILA: " + Fila.fila);
                    } catch (Exception e) {
                        //GAMBIARRA FEROZ
                        //System.err.println(e);
                    }
                } else {
                    System.out.println("FILA VAZIA");
                }
                sleep(Fila.TEMPO_VISUALIZACAO_FILA * 100);
            }
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

}

class Produtor extends Thread {

    @Override
    public void run() {
        try {
            while (true) {

                // PRODUZ A MENSAGEM
                setMensagem();

                // TEMPO DE PROCESSAMENTO DO PRODUTOR
                sleep(Fila.TEMPO_PRODUTOR * 1000);
            }
        } catch (InterruptedException e) {
        }
    }

    private synchronized void setMensagem()
            throws InterruptedException {

        // VERIFICA SE A FILA ESTÁ CHEIA
        while (Fila.fila.size() == Fila.MAX) // AGUARDA ATÉ ESVAZIAR
        {
            wait();
        }

        // VOLTA A CRIAR MENSAGENS
        Random rand = new Random();

        Fila.fila.add(Integer.toString(rand.nextInt(50)));
        //mensagens.add(new java.util.Date().toString());

        System.out.println("ENVIADO: " + Fila.fila.get(Fila.fila.size() - 1));
        notify();
    }

    public synchronized String getMensagem()
            throws InterruptedException {
        notify();
        while (Fila.fila.isEmpty()) {
            wait();
        }
        String mensagem = (String) Fila.fila.get(0);

        // CONSOME MENSAGEM DA FILA
        Fila.fila.remove(mensagem);
        return mensagem;
    }
}

class Consumidor extends Thread {

    Produtor produtor;
    Fila fila;

    Consumidor(Produtor p, Fila f) {
        produtor = p;
        fila = f;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String mensagem = produtor.getMensagem();

                // AVISA QUE RECEBEU A MENSAGEM
                System.out.println("RECEBIDO: " + mensagem);
                sleep(Fila.TEMPO_CONSUMIDOR * 1000);
            }
        } catch (InterruptedException e) {
        }
    }
}
