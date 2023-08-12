package entities;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class LanHouse {
    private final int numComputers;
    private final int maxWaitTime;
    private final int maxUsageTime;
    private final Computer[] computers;
    private final Queue<Client> waitingClients;

    public LanHouse(int numComputers, int maxWaitTime, int maxUsageTime) {
        this.numComputers = numComputers;
        this.maxWaitTime = maxWaitTime;
        this.maxUsageTime = maxUsageTime;
        this.computers = new Computer[numComputers];
        for (int i = 0; i < numComputers; i++) {
            computers[i] = new Computer(i, this);
        }
        this.waitingClients = new LinkedList<>();
    }

    public synchronized void addClientToWaitingList(Client client) {
        waitingClients.add(client);
    }

    public synchronized Client getNextWaitingClients() {
        return waitingClients.poll();
    }

    public Computer[] getComputers() {
        return computers;
    }

    public synchronized void simulateComputerUsage(Client client) {
        Computer computer = client.getAssignedComputer();
        System.out.println("Cliente "+client.getClientId()+" Comecou usar computador "+computer.getComputerId());
        try {
            Thread.sleep(new Random().nextInt(20) + 80);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Cliente "+client.getClientId()+" Terminou de usar computador "+computer.getComputerId());
        computer.realease();
    }

    public void close() {
        for (Computer computer : computers) {
            computer.interrupt();
            try {
                computer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Lan house fechada");
    }

    public Queue<Client> getWaitingClients() {
        return waitingClients;
    }
}
