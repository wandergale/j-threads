package application;

import entities.Client;
import entities.LanHouse;

public class Program {
    public static void main(String[] args) {

        int numClients = 10;
        LanHouse lanHouse = new LanHouse(3, 15, 90);

        Client[] clients = new Client[numClients];
        for (int i = 0; i < numClients; i++) {
            clients[i] = new Client(i, lanHouse);
            clients[i].start();
        }

        for (Client client : clients) {
            try {
                client.join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        lanHouse.close();

    }
}
