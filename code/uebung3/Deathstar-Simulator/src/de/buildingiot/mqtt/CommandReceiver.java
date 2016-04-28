package de.buildingiot.mqtt;

import org.eclipse.paho.client.mqttv3.*;

/**
 * @author Dominik Obermaier
 */
public class CommandReceiver implements MqttCallback {

    private MqttClient client;

    public CommandReceiver(MqttClient client) {
        this.client = client;
    }

    @Override
    public void connectionLost(final Throwable throwable) {

    }

    @Override
    public void messageArrived(final String topic, final MqttMessage mqttMessage) throws Exception {
        if (Topics.SUPERLASER_STATUS.equals(topic)) {
            final String payload = new String(mqttMessage.getPayload());

            if ("activated".equals(payload)) {
                activateSuperlaser();
            }
        }
    }

    private void activateSuperlaser() throws MqttException, InterruptedException {
        System.out.println("Activating Superlaser...");
        Thread.sleep(1000);
        System.out.println("Firing Superlaser....");
        Thread.sleep(1000);
        System.out.println("BOOM");
        System.out.println("Recharging Superlaser...");
        Thread.sleep(2000);

        final MqttMessage deactivateMessage = new MqttMessage("deactivated".getBytes());
        deactivateMessage.setQos(1);
        deactivateMessage.setRetained(true);

        System.out.println("Superlaser recharged");
        client.publish(Topics.SUPERLASER_STATUS, deactivateMessage);


    }

    @Override
    public void deliveryComplete(final IMqttDeliveryToken iMqttDeliveryToken) {
    }
}
