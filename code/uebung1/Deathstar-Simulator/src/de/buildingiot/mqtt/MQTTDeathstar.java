package de.buildingiot.mqtt;

/**
 * @author Dominik Obermaier
 */
public class MQTTDeathstar {


    public void start() {
        System.out.println("The death star is still in construction!");

        publishPeriodically();
    }

    private void publishPeriodically() {

        //Oldskool infinite loop
        while (true) {
            try {
                Thread.sleep(1000);
                System.out.println("Constructing...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
