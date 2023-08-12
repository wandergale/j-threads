package entities;

import java.util.Random;

public class Client extends Thread {
    private final int id;
    private int computerId;
    private final LanHouse lanHouse;

    public Client(int id, LanHouse lanHouse) {
        this.id = id;
        this.computerId = -1;
        this.lanHouse = lanHouse;
    }

    public int getClientId() {
        return id;
    }

    public void useComputer(Computer computer) {
        computerId = computer.getComputerId();
        simulateComputerUsage();
        computerId = -1;
    }

    public Computer getAssignedComputer() {
        int index = computerId;
        return lanHouse.getComputers()[index];
    }

    public void simulateComputerUsage() {
        try {
            Thread.sleep(new Random().nextInt(20) + 80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        lanHouse.addClientToWaitingList(this);
        try {
            Thread.sleep(new Random().nextInt(40) + 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (lanHouse.getWaitingClients().size() <= 15) {
            System.out.println("CLiente "+id + " chegou e esta esperando");
        } else {
            System.out.println("Ciente "+ id +" chegou, mas a sala de espera esta cheia. Cliente saiu" );
        }
    }
}
