package de.buildingiot.mqtt;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Dominik Obermaier
 */
public class MQTTDeathstar {

    private final MqttAsyncClient mqttClient;

    private AtomicInteger communicationFrequency = new AtomicInteger(1);


    public MQTTDeathstar() throws MqttException {
        mqttClient = new MqttAsyncClient("tcp://localhost:1883", "Deathstar_Client", new MemoryPersistence());

    }

    public void start() throws MqttException, InterruptedException {

        mqttClient.setCallback(new CommandReceiver(mqttClient, communicationFrequency));

        System.out.println("Connecting deathstar to Broker");

        final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setWill(Topics.DEATHSTAR_STATUS, "0".getBytes(), 2, true);

        mqttClient.connect(mqttConnectOptions).waitForCompletion();

        mqttClient.publish(Topics.DEATHSTAR_STATUS, "1".getBytes(), 2, true).waitForCompletion();

        mqttClient.subscribe(new String[]{Topics.SUPERLASER_STATUS, Topics.COMMUNICATION_FREQUENCY, Topics.GREENHOUSE_TEMPERATURE}, new int[]{0, 0, 0}).waitForCompletion();

        publishPeriodically();
    }

    private void reactorAlert() throws MqttException {
        final Random random = new Random();
        final int i = random.nextInt(100);

        //5 percent probability
        if (i < 5) {
            System.out.println("Publishing reactor alert!");
            final MqttMessage message = new MqttMessage("Invaders located near the reactor!".getBytes());
            mqttClient.publish(Topics.REACTOR_ALERT, message).waitForCompletion();
        }
    }

    private void publishPeriodically() throws MqttException, InterruptedException {

        //Oldskool infinite loop
        while (true) {
            Thread.sleep(1000 * communicationFrequency.get());
            reactorAlert();
            greenHouseTemperature();
        }
    }

    private void greenHouseTemperature() throws MqttException {
        final int greenhouseTemperature = new Random().nextInt(35);
        mqttClient.publish(Topics.GREENHOUSE_TEMPERATURE, ("" + greenhouseTemperature).getBytes(), 0, false).waitForCompletion();
    }
}
