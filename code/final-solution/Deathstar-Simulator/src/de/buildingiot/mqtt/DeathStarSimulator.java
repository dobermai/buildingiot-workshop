package de.buildingiot.mqtt;

/**
 * @author Dominik Obermaier
 */
public class DeathStarSimulator {

    public static void main(String[] args) {
        System.out.println("Starting the Deathstar Simulator...");
        System.out.println();
        System.out.println("May the force be with you!");
        System.out.println();


        try {

            final MQTTDeathstar mqttDeathstar = new MQTTDeathstar();
            mqttDeathstar.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
