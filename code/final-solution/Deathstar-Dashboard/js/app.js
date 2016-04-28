var client = new Messaging.Client("localhost", 8000, "deathstar-dashboard");


var gauge;
$(document).ready(function () {


    gauge = new JustGage({
        id: "gauge",
        value: 0,
        min: 0,
        max: 35,
        title: " ",
        showMinMax: false,
        levelColors: ["#007CFF", "#a9d70b", "#ff0000"],
        levelColorsGradient: false,
        label: "° Celsius"
    });


    var options = {

        //connection attempt timeout in seconds
        timeout: 3,

        //Gets Called if the connection has successfully been established
        onSuccess: function () {
            client.subscribe("deathstar/reactor/alert");
            client.subscribe("deathstar/superlaser/status");
            client.subscribe("deathstar/greenhouse/temperature");
            client.subscribe("deathstar/status");
        },

        //Gets Called if the connection could not be established
        onFailure: function (message) {
            alert("Connection failed: " + message.errorMessage);
        }
    };

    //Gets called whenever you receive a message for your subscriptions
    client.onMessageArrived = function (message) {

        var topic = message.destinationName;

        if (topic === "deathstar/reactor/alert") {
            invaderAlert(message.payloadString);
        } else if (topic === "deathstar/superlaser/status") {
            if (message.payloadString === "deactivated") {
                stopSuperLaser();
            }
        } else if (topic === "deathstar/greenhouse/temperature") {
            updateTemperature(message.payloadString);
        } else if (topic === "deathstar/status") {
            if (message.payloadString === "0") {
                $("#client_disconnected").html('Deathstar is disconnected!').show();
                $("#client_connected").hide();
            } else {
                $("#client_connected").html('Deathstar is connected!').show();
                $("#client_disconnected").hide()
            }
        }
    };


//Attempt to connect
    client.connect(options);
});


function invaderAlert(text) {
    $("#invader_warning_label").text(text);

    var alertComponent = $("#invader_warning");

    alertComponent.show().delay(5000).fadeOut();

}

function fireSuperLaser() {
    var message = new Messaging.Message("activated");
    message.destinationName = "deathstar/superlaser/status";
    message.qos = 0;
    message.retained = true;

    client.send(message);

    $("#deathstar_animation").show();
    $("#deathstar_laser_recharged").hide();

    return false;
}

function stopSuperLaser() {

    $("#deathstar_animation").hide();
    $("#deathstar_laser_recharged").show();

    return false;
}

function changeFrequency(interval) {

    var message = new Messaging.Message(interval);
    message.destinationName = "deathstar/communication/frequency";
    message.qos = 2;

    client.send(message);
}

function updateTemperature(temp) {
    gauge.refresh(parseFloat(temp));
}
