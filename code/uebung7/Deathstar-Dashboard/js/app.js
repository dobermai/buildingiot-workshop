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


function updateTemperature(temp) {
    gauge.refresh(parseFloat(temp));
}
