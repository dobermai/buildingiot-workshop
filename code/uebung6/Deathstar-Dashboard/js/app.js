//FIXME: Create Client

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
            //FIXME: Subscribe here
        },

        //Gets Called if the connection could not be established
        onFailure: function (message) {
            alert("Connection failed: " + message.errorMessage);
        }
    };


//Attempt to connect
    client.connect(options);
});


function updateTemperature(temp) {
    gauge.refresh(parseFloat(temp));
}
