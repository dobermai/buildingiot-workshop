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


        final MQTTDeathstar mqttDeathstar = new MQTTDeathstar();
        mqttDeathstar.start();

    }
}
