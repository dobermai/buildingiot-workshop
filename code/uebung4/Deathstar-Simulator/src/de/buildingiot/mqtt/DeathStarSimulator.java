package de.buildingiot.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Dominik Obermaier
 */
public class DeathStarSimulator {

    public static void main(String[] args) throws MqttException, InterruptedException {
        System.out.println("Starting the Deathstar Simulator...");
        System.out.println();
        System.out.println("May the force be with you!");
        System.out.println();


        final MQTTDeathstar mqttDeathstar = new MQTTDeathstar();
        mqttDeathstar.start();

    }
}
