package entities;

public class Computer extends Thread {

    private final int id;
    private boolean isUsed;
    private Client clientUsing;
    private final Object lock;
    private final LanHouse lanHouse;

    public Computer(int id, LanHouse lanHouse) {
        this.id = id;
        this.isUsed = false;
        this.clientUsing = null;
        this.lock = new Object();
        this.lanHouse = lanHouse;
    }
    @Override
    public void run() {
        while (!isInterrupted()) {
            if (!isUsed) {
                synchronized (lock) {
                    Client client = lanHouse.getNextWaitingClients();
                    if (client != null) {
                        isUsed = true;
                        clientUsing = client;
                        client.useComputer(this);
                    }
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }
    public void realease() {
        synchronized (lock) {
            isUsed = false;
            clientUsing = null;
        }
    }

    public int getComputerId() {
        return id;
    }
}
