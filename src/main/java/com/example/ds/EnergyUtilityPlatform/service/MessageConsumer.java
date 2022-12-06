package com.example.ds.EnergyUtilityPlatform.service;

import com.example.ds.EnergyUtilityPlatform.RabbitConfig;
import com.example.ds.EnergyUtilityPlatform.model.Consumption;
import com.example.ds.EnergyUtilityPlatform.model.Device;
import com.example.ds.EnergyUtilityPlatform.model.dto.TextMessageDTO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class MessageConsumer {
    private final static String QUEUE_NAME = "dsQueue1";
    private JSONObject json;

    private int counter=0;

    private Double totalHourConsumption=0.0;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private ConsumptionService consumptionService;

    @Autowired
    SimpMessagingTemplate template;

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public void consumeMessages() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("host.docker.internal");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConfig.QUEUE, false, false, false, null);

       DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            JSONParser parser = new JSONParser();
            try {
                setJson((JSONObject) parser.parse(message));
                System.out.println(" [x] Received '" + json + "'");
                String deviceTimestamp = (String) json.get("timestamp");
                Long deviceId = (Long) json.get("deviceId");
                Double deviceConsumption = (Double) json.get("measurementValue");
                Optional<Device> currentDevice = deviceService.findById((int) (long) deviceId);
                Consumption consumption = new Consumption(deviceTimestamp, "12:45", deviceConsumption.toString(), currentDevice.get());
                counter++;
                totalHourConsumption+=deviceConsumption;
                if(counter>6){
                    counter=0;
                    if(totalHourConsumption>Double.parseDouble(currentDevice.get().getMaxHourEnergyConsumption()) && currentDevice.get().getRegularUser()!=null) {
                        totalHourConsumption = 0.0;
                        template.convertAndSend("/topic/message",
                                new TextMessageDTO("Your device with id:  "+currentDevice.get().getDeviceId() +
                                        "  registered an hourly consumption greater than the threshold.",currentDevice.get().getRegularUser().getUserAccount().getUsername(),currentDevice.get().getRegularUser().getUserAccount().getAccountId(),currentDevice.get().getDeviceId()));
                        consumptionService.save(consumption);
                    }
                    else if(totalHourConsumption>Double.parseDouble(currentDevice.get().getMaxHourEnergyConsumption()) && currentDevice.get().getRegularUser()==null){
                        System.out.println("nu are user");
                    }

                }

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        };

        channel.basicConsume(RabbitConfig.QUEUE, true, deliverCallback, consumerTag -> {
        });
    }
}
