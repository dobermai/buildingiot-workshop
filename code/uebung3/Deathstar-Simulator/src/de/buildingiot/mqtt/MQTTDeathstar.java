package de.buildingiot.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Random;

/**
 * @author Dominik Obermaier
 */
public class MQTTDeathstar {

    private final MqttClient mqttClient;

    public MQTTDeathstar() throws MqttException {
        mqttClient = new MqttClient("tcp://localhost:1883", "Deathstar_Client", new MemoryPersistence());

    }

    public void start() throws MqttException, InterruptedException {

        mqttClient.setCallback(new CommandReceiver(mqttClient));

        System.out.println("Connecting deathstar to Broker");

        mqttClient.connect();

        mqttClient.subscribe(Topics.SUPERLASER_STATUS);

        publishPeriodically();
    }

    private void reactorAlert() throws MqttException {
        final Random random = new Random();
        final int i = random.nextInt(100);

        //5 percent probability
        if (i < 5) {
            System.out.println("Publishing reactor alert!");
            final MqttMessage message = new MqttMessage("Invaders located near the reactor!".getBytes());
            mqttClient.publish(Topics.REACTOR_ALERT, message);
        }
    }

    private void publishPeriodically() throws MqttException, InterruptedException {

        //Oldskool infinite loop
        while (true) {
            Thread.sleep(1000);
            reactorAlert();
        }
    }
}
