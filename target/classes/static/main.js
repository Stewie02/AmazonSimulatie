import World from './objects/World.js';
import WareHouse from './objects/WareHouse.js'
import Robot from './objects/Robot.js';
import Rack from './objects/imported/Rack/Rack.js';

let world, command;
let buildWorld = true;

window.onload = function () {
    world = new World();
    world.init();

    const socket = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/connectToSimulation");
    socket.onmessage = event => {
        command = JSON.parse(event.data);
        console.log(command);
        switch (command.command) {
            case "object_update":
                buildWorld ? build(command.parameters) : update(command.parameters);
                break;
            case "done_init":
                buildWorld = false;
                finishInit();
                break;
            default:
                console.log("command did not match")
                break;
        }
    }
}

async function build(parameters) {
    switch (parameters.type) {
        case "robot":
            console.log("robot");
            let robot = new Robot();
            robot.moveTo(parameters.x, parameters.y, parameters.z);
            robot.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
            robot.mesh.uuid = parameters.uuid;
            world.addObject( robot.getMesh() );
            break;
        case "rack":
            console.log("rack");
            let rack = new Rack();
            await rack.loadObject();
            console.log("build", parameters.x);
            rack.moveTo(parameters.x, parameters.y, parameters.z);
            rack.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
            rack.mesh.uuid = parameters.uuid;
            world.addObject( rack.getMesh() );
            break;
    
        default:
            break;
    }
}

function update(parameters) {
    console.log("update", parameters);
    const object = world.scene.getObjectByProperty( 'uuid', parameters.uuid );
    console.log(object);
    const frames = 6; //convert to frames!!!
    world.setAnimation( object, frames, {
        x: parameters.x,
        y: parameters.y,
        z: parameters.z
    }, {
        x: parameters.rotationX,
        y: parameters.rotationY,
        z: parameters.rotationZ
    } );
}

function finishInit() {
    let warehouse = new WareHouse();
    world.addObject( warehouse.getMesh() );
}