// global variables
var socket = null;
var emit_c = 0;
var current_lab = "lab1_sec1";
var current_selected = "";
var keys = {
    key1: "",
    key2: "",
    key3: ""
};
var keys_c = 0;

var pulse = 0;
var points = 200;
var flashlight_points = 15;
var teleport_points = 100;

var keys_caught = 0;
var flashlight_battery = 100;

var game_started = false;

var kinnect = false;

var ghost = {
  ghost: ""
};

var teleport = {
    teleport: ""
};

var spider = {
  spider: ""
};

var enemy = {
    enemy: ""
};

var sound = {
    sound: ""
};

var flashlight = {
    time: "",
    activate: ""
}

var lights = {
    location: "",
    active: ""
};

var lights_state = {
    lab4_sec1: "1",
    lab4_sec2: "1",
    lab5_sec1: "1",
    lab5_sec2: "1",
    lab8_sec1: "1",
};

function createMap() {
    $("#content").empty();
    $("#content").append(
                        "<div id='monsters'>" +
                            "<div id='tests'>Tests<br></div>" +
                            "<div>Monsters</div>" +
                        "</div>" +
                        "<div id='map'>Game" +
                        "</div>" +
                        "<div id='traps'>" +
                            "<div id='keys'><p>Keys</p></div>" +
                            "<div id='traps2'><div>Traps</div></div>" +
                            "<div id='sounds'>Sounds</div>" +
                        "</div>");

    $("#monsters").append(
                        "<table>" +
                            "<tr>" +
                                "<td><img id='boo' src='images/boo.png'></td>" +
                                "<td><button id='boo_click'>Activate - 50 points</button><br>Can only be activated in tunnels</td>" +
                            "</tr>" +
                            "<tr>" +
                                "<td><img id='spider' src='images/spider.jpg'></td>" +
                                "<td><button id='spider_click'>Activate - 50 points</button><br>Can only be activated in labs</td>" +
                            "</tr>" +
                            "<tr>" +
                                "<td><img id='tank' src='images/tank.jpg'></td>" +
                                "<td><button id='tank_click'>Activate - 50 points</button><br>Can only be activated in lab 3</td>" +
                            "</tr>" +
                            "<tr>" +
                                "<td><img id='grunt' src='images/grunt.jpg'></td>" +
                                "<td><button id='grunt_click'>Activate - 50 points</button><br>Can only be activated in labs 4 & 6</td>" +
                            "</tr>" +
                            "<tr>" +
                                "<td><img id='slenderman' src='images/slenderman.png'></td>" +
                                "<td><button id='slenderman_click'>Activate - 50 points</button><br>Can only be activated in lab 8</td>" +
                            "</tr>" +
                        "</table>");
    
    $("#boo_click").on("click", function () {
        if (current_selected.startsWith("tun") && points >= 50) {
            console.log("Ghost trigger");
            if (current_selected.startsWith("tun10")) {
                ghost.ghost = "Ghost10";
            }
            else
                ghost.ghost = "Ghost" + current_selected[3];
            socket.emit("ghost", ghost);
            points -= 50;
            $("#points").text("Points: " + points);
        }
    });

    $("#spider_click").on("click", function () {
        if (current_selected.startsWith("lab") && points >= 50) {
            console.log("Spider trigger");
            spider.spider = "SpiderLab" + current_selected[3];
            socket.emit("spider", spider);
            points -= 50;
            $("#points").text("Points: " + points);
        }
    });
    
    $("#tank_click").on("click", function () {
        if (current_selected.startsWith("lab3") && points >= 50) {
            console.log("tank trigger");
            enemy.enemy = "tank";
            socket.emit("enemy", enemy);
            points -= 50;
            $("#points").text("Points: " + points);
        }
    });

    $("#grunt_click").on("click", function () {
        if ((current_selected.startsWith("lab4") || current_selected.startsWith("lab6")) && points >= 50) {
            console.log("grunt trigger");
            enemy.enemy = "gruntLab" + current_selected[3];
            socket.emit("enemy", enemy);
            points -= 50;
            $("#points").text("Points: " + points);
        }
    });

    $("#slenderman_click").on("click", function () {
        if (current_selected.startsWith("lab8") && points >= 50) {
            console.log("tank trigger");
            enemy.enemy = "slender";
            socket.emit("enemy", enemy);
            points -= 50;
            $("#points").text("Points: " + points);
        }
    });

    $("#traps2").append(
                        "<table>" +
                            "<tr>" +
                                "<td><img id='teleport' src='images/teleport.jpg'>Teleport</td>" +
                                "<td><button id='teleport_click'>Activate - 100 points</button>Teleports the player to the selected lab</lab></td>" +
                            "</tr>" +
                            "<tr>" +
                                "<td><img id='lights' src='images/lights.jpg'>Lights</td>" +
                                "<td>" +
                                    "<button id='lights_click'>Activate - 10 points</button>" +
                                    "<br>Only available in labs 4 & 6 & 8<br>" +
                                    "<button id='lights2_click'>Deactivate</button>" +
                                "</td>" +
                            "</tr>" +
                            "<tr>" +
                                "<td><img id='lights' src='images/flashlight.PNG'>Flashlight</td>" +
                                "<td>" +
                                    "<form name='form'>" +
                                        "Duration (seconds):<br>" +
                                        "<label><input class='flashlight_time' name='flashlight_time' value='1' type='radio' checked>1</label>" +
                                        "<label><input class='flashlight_time' name='flashlight_time' value='5' type='radio'>5</label>" +
                                        "<label><input class='flashlight_time' name='flashlight_time' value='10' type='radio'>10</label>" +
                                        "<hr>" +
                                        "Scary jump monster:<br>" +
                                        "<label><input class='flashlight_scary' name='flashlight_scary' value='1' type='radio'>Yes</label>" +
                                        "<label><input class='flashlight_scary' name='flashlight_scary' value='0' type='radio' checked>No</label>" +
                                    "</form>" +
                                    "<button id='flashlight_click'>Deactivate - 15 points</button>" +
                                "</td>" +
                            "</tr>" +
                        "</table>");
    
    $("#teleport_click").on("click", function () {
        if (current_selected.startsWith("lab") && !current_selected.startsWith("lab1") && points >= teleport_points) {
            console.log("teleport trigger");
            teleport.teleport = "Teleport_lab" + current_selected[3];
            socket.emit("teleport", teleport);
            points -= 100;
            teleport_points += 25;
            $("#teleport_click").text("Activate - " + teleport_points + " points");
            $("#points").text("Points: " + points);
        }
    });

    $("#lights_click").on("click", function () {
        if (current_selected.startsWith("lab") && points >= 10) {
            if (current_selected[3] == "4" || current_selected[3] == "5" || current_selected[3] == "8") {
                if (lights_state[current_selected] == "0") {
                    console.log("lights on trigger");
                    lights.location = current_selected;
                    lights.active = "1";
                    socket.emit("lights", lights);
                    lights_state[current_selected] = "1";
                    $("#lights_click").attr("disabled", true);
                    $("#lights2_click").attr("disabled", false);
                    points -= 10;
                    $("#points").text("Points: " + points);
                }
            }
        }
    });

    $("#lights2_click").on("click", function () {
        if (current_selected.startsWith("lab") && points >= 10) {
            if (current_selected[3] == "4" || current_selected[3] == "5" || current_selected[3] == "8") {
                if (lights_state[current_selected] == "1") {
                    console.log("lights off trigger");
                    lights.location = current_selected;
                    lights.active = "0";
                    socket.emit("lights", lights);
                    lights_state[current_selected] = "0";
                    $("#lights_click").attr("disabled", false);
                    $("#lights2_click").attr("disabled", true);
                    points -= 0;
                    $("#points").text("Points: " + points);
                }
            }
        }
    });
    
    $(".flashlight_time").on("click", function () {
        var val = $(this).val();
        var val2 = document.forms.form.flashlight_scary.value;

        if (val2 === "1") {
            if (val === "1") {
                $("#flashlight_click").text("Deactivate - 25 points");
                flashlight_points = 25;
            }
            else if (val === "5") {
                $("#flashlight_click").text("Deactivate - 30 points");
                flashlight_points = 30;
            }
            else if (val === "10") {
                $("#flashlight_click").text("Deactivate - 35 points");
                flashlight_points = 35;
            }
        }
        else {
            if (val === "1") {
                $("#flashlight_click").text("Deactivate - 15 points");
                flashlight_points = 15;
            }
            else if (val === "5") {
                $("#flashlight_click").text("Deactivate - 20 points");
                flashlight_points = 20;
            }
            else if (val === "10") {
                $("#flashlight_click").text("Deactivate - 25 points");
                flashlight_points = 25;
            }
        }
    });

    $(".flashlight_scary").on("click", function () {
        var val = document.forms.form.flashlight_time.value;
        var val2 = $(this).val();

        if (val2 === "1") {
            if (val === "1") {
                $("#flashlight_click").text("Deactivate - 25 points");
                flashlight_points = 25;
            }
            else if (val === "5") {
                $("#flashlight_click").text("Deactivate - 30 points");
                flashlight_points = 30;
            }
            else if (val === "10") {
                $("#flashlight_click").text("Deactivate - 35 points");
                flashlight_points = 35;
            }
        }
        else {
            if (val === "1") {
                $("#flashlight_click").text("Deactivate - 15 points");
                flashlight_points = 15;
            }
            else if (val === "5") {
                $("#flashlight_click").text("Deactivate - 20 points");
                flashlight_points = 20;
            }
            else if (val === "10") {
                $("#flashlight_click").text("Deactivate - 25 points");
                flashlight_points = 25;
            }
        }
    });
    
    $("#flashlight_click").on("click", function () {
        if (points >= flashlight_points) {
            console.log("flashlight trigger");
            flashlight.time = document.forms.form.flashlight_time.value;
            flashlight.activate = document.forms.form.flashlight_scary.value;
            socket.emit("flashlight", flashlight);
            points -= flashlight_points;
            $("#points").text("Points: " + points);
        }
    });

    $("#sounds").append(
                        "<table>" +
                            "<tr>" +
                                "<td>Thunder sound</td>" +
                                "<td><button id='thunder_click'>Activate - 20 points</button></td>" +
                            "</tr>" +
                            "<tr>" +
                                "<td>Diabolic sound</td>" +
                                "<td><button id='diabolic_click'>Activate - 20 points</button></td>" +
                            "</tr>" +
                            "<tr>" +
                                "<td>Screaming sound</td>" +
                                "<td><button id='scream_click'>Activate - 20 points</button></td>" +
                            "</tr>" +
                            "<tr>" +
                                "<td>Chains sound</td>" +
                                "<td><button id='chains_click'>Activate - 20 points</button></td>" +
                            "</tr>" +
                            "<tr>" +
                                "<td>Door sound</td>" +
                                "<td><button id='door_click'>Activate - 20 points</button></td>" +
                            "</tr>" +
                        "</table>");

    $("#thunder_click").on("click", function () {
        if (points >= 20) {
            console.log("thunder sound trigger");
            sound.sound = "thunder";
            socket.emit("sound", sound);
            points -= 20;
            $("#points").text("Points: " + points);
        }
    });

    $("#diabolic_click").on("click", function () {
        if (points >= 20) {
            console.log("diabolic sound trigger");
            sound.sound = "diabolic";
            socket.emit("sound", sound);
            points -= 20;
            $("#points").text("Points: " + points);
        }
    });

    $("#scream_click").on("click", function () {
        if (points >= 20) {
            console.log("scream sound trigger");
            sound.sound = "scream";
            socket.emit("sound", sound);
            points -= 20;
            $("#points").text("Points: " + points);
        }
    });

    $("#chains_click").on("click", function () {
        if (points >= 20) {
            console.log("chains sound trigger");
            sound.sound = "chains";
            socket.emit("sound", sound);
            points -= 20;
            $("#points").text("Points: " + points);
        }
    });

    $("#door_click").on("click", function () {
        if (points >= 20) {
            console.log("door sound trigger");
            sound.sound = "door";
            socket.emit("sound", sound);
            points -= 20;
            $("#points").text("Points: " + points);
        }
    });

    $("#map").append(
                    "<div>" +
                        "<div id='location_selected'>" +
                            "<h3 id='location'>Player location: " + current_lab + "</h3>" +
                            "<h3 id='selected'>Selected: </h3>" +
                        "</div>" +
                        "<div id='pulse_points'>" +
                            "<h3 id='pulse'>Pulse: " + pulse + "</h3>" +
                            "<h3 id='points'>Points: " + points + "</h3>" +
                            "<h3 id='keys_caught'>Keys: " + keys_caught + "</h3>" +
                            "<h3 id='flashlight_battery'>Flashlight: " + flashlight_battery + "%</h3>" +
                        "</div>" +
                    "</div>");
    
    $("#map").append("<div id='lab8_sec1' class='droppable selectable'></div>");
    $("#map").append("<div id='tun10' class='selectable'></div>");
    $("#map").append("<div id='lab7'></div>");
    $("#map").append("<div id='tun9' class='selectable'></div>");
    $("#map").append("<div id='tun8' class='selectable'></div>");
    $("#map").append("<div id='lab5'></div>");
    $("#map").append("<div id='tun7' class='selectable'></div>");
    $("#map").append("<div id='lab6'></div>");
    $("#map").append("<div id='tun6' class='selectable'></div>");
    $("#map").append("<div id='tun5' class='selectable'></div>");
    $("#map").append("<div id='lab3'></div>");
    $("#map").append("<div id='tun4' class='selectable'></div>");
    $("#map").append("<div id='lab4'></div>");
    $("#map").append("<div id='tun3' class='selectable'></div>");
    $("#map").append("<div id='tun2' class='selectable'></div>");
    $("#map").append("<div id='lab2'></div>");
    $("#map").append("<div id='tun1' class='selectable'></div>");
    $("#map").append("<div id='lab1_sec1' class='selectable'></div>");

    $("#lab2").append("<div id='lab2_sec2' class='droppable selectable touchable'></div>");
    $("#lab2").append("<div id='lab2_sec1' class='droppable selectable touchable'></div>");

    $("#lab4").append("<div id='lab4_sec2' class='droppable selectable touchable'></div>");
    $("#lab4").append("<div id='lab4_sec1' class='droppable selectable touchable'></div>");

    $("#lab3").append("<div id='lab3_sec3' class='droppable selectable touchable'></div>");
    $("#lab3").append("<div id='lab3_sec2' class='droppable selectable touchable'></div>");
    $("#lab3").append("<div id='lab3_sec1' class='droppable selectable touchable'></div>");

    $("#lab5").append("<div id='lab5_sec2' class='droppable selectable touchable'></div>");
    $("#lab5").append("<div id='lab5_sec1' class='droppable selectable touchable'></div>");

    $("#lab6").append("<div id='lab6_sec2' class='droppable selectable touchable'></div>");
    $("#lab6").append("<div id='lab6_sec1' class='droppable selectable touchable'></div>");

    $("#lab7").append("<div id='lab7_sec2' class='droppable selectable touchable'></div>");
    $("#lab7").append("<div id='lab7_sec1' class='droppable selectable touchable'></div>");

    $("#tests").append("<button id='movement' type='button'>Movement</button>");
    $("#movement").on("click", function () {
        movement();
    });

    $("#tests").append("<button id='finish' type='button'>Finish</button>");
    $("#finish").on("click", function () {
        gameOver();
    });

    $("#tests").append("<button id='addPoints' type='button'>Add points</button>");
    $("#addPoints").on("click", function () {
        points += 10;
        $("#points").text("Points: " + points);
    });

    $("#tests").append("<button id='removePoints' type='button'>Remove points</button>");
    $("#removePoints").on("click", function () {
        if (points - 10 < 0)
            points = 0;
        else
            points -= 10;
        $("#points").text("Points: " + points);
    });

    $("#tests").append("<button id='addPulse' type='button'>Add Pulse</button>");
    $("#addPulse").on("click", function () {
        pulse += 10;
        $("#pulse").text("Pulse: " + pulse);
    });

    $("#tests").append("<button id='addPlayer' type='button'>Add Player</button>");
    $("#addPlayer").on("click", function () {
        $("#lab1_sec1").css("background-color", "red");
    });
    $("#tests").append("<button id='addKinnect' type='button'>Add Kinnect</button>");
    $("#addKinnect").on("click", function () {
       kinnect = true;
    });
    $("#tests").append("<button id='lvl' type='button'>lvl</button>");
    $("#lvl").on("click", function () {
        socket.emit("lvl", "ola");
    });

    $("#keys").append("<div id='key1' class='draggable'><img src='/images/key.jpg'><p>Key 1</p></div>");
    $("#keys").append("<div id='key2' class='draggable'><img src='/images/key.jpg'><p>Key 2</p></div>");
    $("#keys").append("<div id='key3' class='draggable'><img src='/images/key.jpg'><p>Key 3</p></div>");

    //$(".draggable").draggable();
    $(".droppable").droppable({
        hoverClass: "highlightDrop",
        over: function (event, ui) {
            var id = $(this).attr("id");
            $("#selected").text("Selected: " + id);
        },
        drop: function (event, ui) {
            keys[$(ui.draggable).attr("id")] = "Key-"+$(this).attr("id");
            $("#" + $(ui.draggable).attr("id")).draggable("destroy");
            $("#" + $(ui.draggable).attr("id") + " img").remove();
            $(this).droppable("destroy");
            keys_c++;
            if (keys_c === 3)
                sendKeys();
        }
    });
    $(".selectable").on("click", function () {
        if (game_started) {
            if (current_selected !== "")
                if (current_selected === current_lab)
                    $("#" + current_selected).css("background-color", "red");
                else
                    $("#" + current_selected).css("background-color", "white");

            if ($(this).attr("id") === current_lab)
                $(this).css("background-color", "orange");
            else
                $(this).css("background-color", "yellow");

            current_selected = $(this).attr("id");
            $("#selected").text("Selected: " + current_selected);

            if (current_selected.startsWith("tun") && points >= 10) {
                $("#lights_click").attr("disabled", true);
            }

            if (current_selected.startsWith("lab") && points >= 10) {
                $("#lights_click").attr("disabled", false);
            }

            if (current_selected.startsWith("tun") && points >= 50) {
                $("#boo_click").attr("disabled", false);
                $("#spider_click").attr("disabled", true);
            }

            if (current_selected.startsWith("lab") && points >= 50) {
                $("#boo_click").attr("disabled", true);
                $("#spider_click").attr("disabled", false);
            }

            if (current_selected.startsWith("tun") && points >= 100) {
                $("#teleport_click").attr("disabled", true);
            }

            if (current_selected.startsWith("lab") && points >= 100) {
                $("#teleport_click").attr("disabled", false);
            }

            if (current_selected.startsWith("lab3") && points >= 50) {
                $("#tank_click").attr("disabled", false);
            }
            else
                $("#tank_click").attr("disabled", true);

            if (current_selected.startsWith("lab8") && points >= 50) {
                $("#slenderman_click").attr("disabled", false);
            }
            else
                $("#slenderman_click").attr("disabled", true);

            if ((current_selected.startsWith("lab4") || current_selected.startsWith("lab4")) && points >= 50) {
                $("#grunt_click").attr("disabled", false);
            }
            else
                $("#grunt_click").attr("disabled", true);

            if (current_selected.startsWith("lab")) {
                if (current_selected[3] == "4" || current_selected[3] == "5" || current_selected[3] == "8") {
                    if (lights_state[current_selected] == "1") {
                        $("#lights_click").attr("disabled", true);
                        $("#lights2_click").attr("disabled", false);
                    }
                    else if (lights_state[current_selected] == "0") {
                        $("#lights_click").attr("disabled", false);
                        $("#lights2_click").attr("disabled", true);
                    }
                }
                else {
                    $("#lights_click, #lights2_click").attr("disabled", true);
                }
            }
            else {
                $("#lights_click, #lights2_click").attr("disabled", true);
            }
        }
    });

    $("#lights_click, #lights2_click").attr("disabled", true);
}

function checkPoints() {
    if (game_started) {
        if (points < 10) {
            $("#boo_click, #spider_click, #tank_click, #grunt_click, #slenderman_click, #teleport_click, #lights_click").attr("disabled", true);
            $("#thunder_click, #diabolic_click, #scream_click, #chains_click, #door_click").attr("disabled", true);
        }
        else if (points < 20) {
            if (current_selected.startsWith("lab")) {
                if (current_selected[3] == "4" || current_selected[3] == "5" || current_selected[3] == "8") {
                    if (lights_state[current_selected] == "1") {
                        $("#lights_click").attr("disabled", true);
                        $("#lights2_click").attr("disabled", false);
                    }
                    else if (lights_state[current_selected] == "0") {
                        $("#lights_click").attr("disabled", false);
                        $("#lights2_click").attr("disabled", true);
                    }
                }
                else {
                    $("#lights_click").attr("disabled", true);
                    $("#lights2_click").attr("disabled", true);
                }
            }
            else {
                $("#lights_click").attr("disabled", true);
                $("#lights2_click").attr("disabled", true);
            }
            $("#boo_click, #spider_click, #tank_click, #grunt_click, #slenderman_click, #teleport_click").attr("disabled", true);
            $("#thunder_click, #diabolic_click, #scream_click, #chains_click, #door_click").attr("disabled", true);
        }
        else if (points < 50) {
            if (current_selected.startsWith("lab")) {
                if (current_selected[3] == "4" || current_selected[3] == "5" || current_selected[3] == "8") {
                    if (lights_state[current_selected] == "1") {
                        $("#lights_click").attr("disabled", true);
                        $("#lights2_click").attr("disabled", false);
                    }
                    else if (lights_state[current_selected] == "0") {
                        $("#lights_click").attr("disabled", false);
                        $("#lights2_click").attr("disabled", true);
                    }
                }
                else {
                    $("#lights_click").attr("disabled", true);
                    $("#lights2_click").attr("disabled", true);
                }
            }
            else {
                $("#lights_click").attr("disabled", true);
                $("#lights2_click").attr("disabled", true);
            }
            $("#boo_click, #spider_click, #tank_click, #grunt_click, #slenderman_click, #teleport_click").attr("disabled", true);
            $("#thunder_click, #diabolic_click, #scream_click, #chains_click, #door_click").attr("disabled", false);
        }
        else if (points < teleport_points) {
            if (current_selected.startsWith("lab")) {
                if (current_selected[3] == "4" || current_selected[3] == "5" || current_selected[3] == "8") {
                    if (lights_state[current_selected] == "1") {
                        $("#lights_click").attr("disabled", true);
                        $("#lights2_click").attr("disabled", false);
                    }
                    else if (lights_state[current_selected] == "0") {
                        $("#lights_click").attr("disabled", false);
                        $("#lights2_click").attr("disabled", true);
                    }
                }
                else {
                    $("#lights_click").attr("disabled", true);
                    $("#lights2_click").attr("disabled", true);
                }
            }
            else {
                $("#lights_click").attr("disabled", true);
                $("#lights2_click").attr("disabled", true);
            }
            if (current_selected.startsWith("tun")) {
                $("#boo_click").attr("disabled", false);
                $("#spider_click").attr("disabled", true);
            }

            if (current_selected.startsWith("lab")) {
                $("#boo_click").attr("disabled", true);
                $("#spider_click").attr("disabled", false);
            }

            if (current_selected.startsWith("lab3")) {
                $("#tank_click").attr("disabled", false);
            }
            else
                $("#tank_click").attr("disabled", true);

            if (current_selected.startsWith("lab4") || current_selected.startsWith("lab6")) {
                $("#grunt_click").attr("disabled", false);
            }
            else
                $("#grunt_click").attr("disabled", true);

            if (current_selected.startsWith("lab8")) {
                $("#slenderman_click").attr("disabled", false);
            }
            else
                $("#slenderman_click").attr("disabled", true);

            $("#thunder_click, #diabolic_click, #scream_click, #chains_click, #door_click").attr("disabled", false);
            $("#teleport_click").attr("disabled", true);
        }
        else {
            if (current_selected.startsWith("lab")) {
                if (current_selected[3] == "4" || current_selected[3] == "5" || current_selected[3] == "8") {
                    if (lights_state[current_selected] == "1") {
                        $("#lights_click").attr("disabled", true);
                        $("#lights2_click").attr("disabled", false);
                    }
                    else if (lights_state[current_selected] == "0") {
                        $("#lights_click").attr("disabled", false);
                        $("#lights2_click").attr("disabled", true);
                    }
                }
                else {
                    $("#lights_click").attr("disabled", true);
                    $("#lights2_click").attr("disabled", true);
                }
            }
            else {
                $("#lights_click").attr("disabled", true);
                $("#lights2_click").attr("disabled", true);
            }
            if (current_selected.startsWith("tun")) {
                $("#boo_click").attr("disabled", false);
                $("#spider_click, #teleport_click").attr("disabled", true);
            }

            if (current_selected.startsWith("lab")) {
                $("#boo_click").attr("disabled", true);
                $("#spider_click, #teleport_click").attr("disabled", false);
            }

            if (current_selected.startsWith("lab3")) {
                $("#tank_click").attr("disabled", false);
            }
            else
                $("#tank_click").attr("disabled", true);

            if (current_selected.startsWith("lab4") || current_selected.startsWith("lab6")) {
                $("#grunt_click").attr("disabled", false);
            }
            else
                $("#grunt_click").attr("disabled", true);

            if (current_selected.startsWith("lab8")) {
                $("#slenderman_click").attr("disabled", false);
            }
            else
                $("#slenderman_click").attr("disabled", true);

            $("#thunder_click, #diabolic_click, #scream_click, #chains_click, #door_click").attr("disabled", false);
        }

        if (parseInt(points) >= flashlight_points) {
            $("#flashlight_click").attr("disabled", false);
        }
        else {
            $("#flashlight_click").attr("disabled", true);
        }
    }
    else {
        $("#boo_click, #spider_click, #tank_click, #grunt_click, #slenderman_click, #teleport_click, #lights_click").attr("disabled", true);
        $("#thunder_click, #diabolic_click, #scream_click, #chains_click, #door_click").attr("disabled", true);
        $("#flashlight_click").attr("disabled", true);
    }
}

function startup2() {
    $("body").append(
                    "<div id='startup'>" +
                        "<h3>The Game is waiting for all the devices to connect</h3>" +
                        "<h4 id='bitalino'>Bitalino: not connected</h4>" +
                        "<h4 id='dive'>Dive: not connected</h4>" +
                        "<h4 id='kinnect'>Kinnect: not connected</h4>" +
                    "</div>");
    var t = setInterval(function () {
        var b = false;
        var d = false;
        var k = false;
        if ($("#pulse").text() != "Pulse: 0") {
            b = true;
            $("#bitalino").text("Bitalino: connected!");
        }
        if ($("#lab1_sec1").css("background-color") != "rgba(0, 0, 0, 0)") {
            d = true;
            $("#dive").text("Dive: connected!");
        }
        if (kinnect === true) {
            k = true;
            $("#kinnect").text("Kinnect: connected!");
        }
        if (b && d && k) {
            $(".draggable").draggable();
            clearInterval(t);
            t = null;
            $("#startup").empty().append("<h2>Please insert the keys in the labs to start the game!</h2>"+
                                            "<ul><li>Cannot place a key in lab1</li><li>Cannot place 2 keys in the same section of the same lab</li></ul>" +
                                            "<button id='ok'>Ok</button>").css("height", "12em");
            $("#ok").on("click", function () {
                $("#startup").remove();
            });
        }
    }, 500);
}

function movement() {
    socket.emit("movement", "joao - " + emit_c);
    emit_c++;
}

function sendKeys() {
    socket.emit("keys", keys);
    $("#keys").remove();
    game_started = true;
}

function gameOver() {
    $("#content").empty();
    $("#content").append("<h2>Game Over</h2>");
    $("#content").append("<button id='playAgain' type='button'>Play Again</button>");
    $("#playAgain").on("click", function () {
        location.reload();
    });
}

$(document).ready(function () {
   $("#connect").on("click", function () {
       socket = io.connect($("#ip").val()+":"+$("#port").val());

       socket.on("connect", function () {
           $("#title").remove();
           createMap();
           checkPoints();
           setInterval(function () {
               checkPoints();
           }, 500);
           startup2();
       });

       socket.on("lvl", function (data) {

           if (!data["sec"].startsWith("GameObject"))
               data["lvl"] = data["sec"];
           $("#location").text("Player location: " + data["lvl"]);
           if (current_lab !== "")
               if (current_lab === current_selected)
                   $("#" + current_lab).css("background-color", "yellow");
               else
                   $("#" + current_lab).css("background-color", "white");

           if (data["lvl"] === current_selected)
               $("#" + data["lvl"]).css("background-color", "orange");
           else
               $("#" + data["lvl"]).css("background-color", "red");
           current_lab = data["lvl"];
       });

       socket.on("kinnect", function (data) {
           kinnect = true;
       });

       socket.on("batteryState", function (data) {
           flashlight_battery = data["battery"];
           $("#flashlight_battery").text("Flashlight: " + flashlight_battery + "%");
       });

       socket.on("keyPickup", function (data) {
           keys_caught = data["pos"];
           $("#keys_caught").text("Keys: " + keys_caught);
       });
       
       socket.on("heartBeat", function (data) {
           points += parseInt(data.pontos);
           pulse = parseInt(data.heartBeat);
           $("#pulse").text("Pulse: " + pulse);
           $("#points").text("Points: " + points);
           checkPoints();
       });
       
       socket.on("finish", function (data) {
           gameOver();
       });
    });
});