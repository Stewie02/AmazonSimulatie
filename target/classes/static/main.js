import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import World from './objects/World.js';
import WareHouse from './objects/WareHouse.js'
import Robot from './objects/Robot.js';
import Rack from './objects/imported/Rack/Rack.js';
import Truck from './objects/imported/Truck/Truck/Truck.js';
import SimpleRack from './objects/SimpleRack.js';

let world;

window.onload = function () {
    world = new World();
    world.init();

   

    // commandHandler({"command": "rack_positions", "parameters": [{"uuid": "5b969e7d-1a82-487a-8e7f-aa72cc27e653","x": "0.0","y": "0.01","z": "0.0"},{"uuid": "1f27e411-4391-4741-93cc-3d0fc3fdda0f","x": "1.2","y": "0.01","z": "0.0"},{"uuid": "8cf89242-8fcd-4094-b52b-3198313a179e","x": "3.5999999999999996","y": "0.01","z": "0.0"},{"uuid": "ee6558b5-d6d0-44c2-9c4d-374aa5d17bb5","x": "4.8","y": "0.01","z": "0.0"},{"uuid": "81a22f18-142e-4858-a57d-2561292baaf4","x": "7.199999999999999","y": "0.01","z": "0.0"},{"uuid": "0388e098-7817-4dfc-8375-f754a658c3ee","x": "8.399999999999999","y": "0.01","z": "0.0"},{"uuid": "e35b5036-58ab-4975-96b2-c12c4c6cc77c","x": "10.799999999999999","y": "0.01","z": "0.0"},{"uuid": "dd673b12-c910-49ee-ad0c-8cf92e5562ce","x": "11.999999999999998","y": "0.01","z": "0.0"},{"uuid": "2c675b1c-09a6-4285-ad8b-26dd575366eb","x": "0.0","y": "0.01","z": "1.2"},{"uuid": "f4417293-4a23-4333-a531-12a67180d5c6","x": "1.2","y": "0.01","z": "1.2"},{"uuid": "db57d52c-5539-4d88-b788-ab1ab691a325","x": "3.5999999999999996","y": "0.01","z": "1.2"},{"uuid": "1fe0684f-e602-4afc-ad34-ba7688a694b3","x": "4.8","y": "0.01","z": "1.2"},{"uuid": "58c5ea14-f8a2-4140-a36c-afa76c6a70ec","x": "7.199999999999999","y": "0.01","z": "1.2"},{"uuid": "9c0d3b6b-2677-4068-87dc-8c09db544cf3","x": "8.399999999999999","y": "0.01","z": "1.2"},{"uuid": "21f776bb-3172-4902-8972-98fd7f54b207","x": "10.799999999999999","y": "0.01","z": "1.2"},{"uuid": "1a582409-a1a3-4a83-9d99-e394facb29ae","x": "11.999999999999998","y": "0.01","z": "1.2"},{"uuid": "25f0f999-d1dd-45a4-8a93-ee73951248a2","x": "0.0","y": "0.01","z": "3.5999999999999996"},{"uuid": "69a74f8f-ba4f-40d3-a6a1-802059bd63e7","x": "1.2","y": "0.01","z": "3.5999999999999996"},{"uuid": "b89ad433-9bf5-462e-af68-8dc597fe8322","x": "3.5999999999999996","y": "0.01","z": "3.5999999999999996"},{"uuid": "702b247b-26da-4f70-b709-c9c43c186a54","x": "4.8","y": "0.01","z": "3.5999999999999996"},{"uuid": "d914f0b3-c54d-4731-9c4b-c5d234583d38","x": "7.199999999999999","y": "0.01","z": "3.5999999999999996"},{"uuid": "861bf095-2532-4875-aa08-70dcac960a35","x": "8.399999999999999","y": "0.01","z": "3.5999999999999996"},{"uuid": "1971638e-ad40-4204-b2ca-bde9ad340785","x": "10.799999999999999","y": "0.01","z": "3.5999999999999996"},{"uuid": "7dc94ac8-af49-4cc6-9cb1-bde6b9462bb3","x": "11.999999999999998","y": "0.01","z": "3.5999999999999996"},{"uuid": "a1c5271c-c02a-458b-8813-29e5b139c991","x": "0.0","y": "0.01","z": "4.8"},{"uuid": "eacdb57f-473c-465c-82cd-d7f90946e6a2","x": "1.2","y": "0.01","z": "4.8"},{"uuid": "565daa33-750e-4348-a1b8-39f2124abcb1","x": "3.5999999999999996","y": "0.01","z": "4.8"},{"uuid": "de63b9c2-b0d8-4388-acc0-bee335456bd3","x": "4.8","y": "0.01","z": "4.8"},{"uuid": "f3d3daff-71b9-452c-9b27-cc10d81dfd97","x": "7.199999999999999","y": "0.01","z": "4.8"},{"uuid": "524b9847-709e-4e1f-8377-df10f85101ae","x": "8.399999999999999","y": "0.01","z": "4.8"},{"uuid": "3f6b9f1c-d042-46d7-a049-65de06011795","x": "10.799999999999999","y": "0.01","z": "4.8"},{"uuid": "8e958fbe-9018-4d9a-a191-367e26f7a4c4","x": "11.999999999999998","y": "0.01","z": "4.8"},{"uuid": "c63d1a97-163a-42e5-87b0-2cee63620740","x": "0.0","y": "0.01","z": "7.199999999999999"},{"uuid": "6e824ca0-2ecb-4b61-9cfe-f2eaa15d8125","x": "1.2","y": "0.01","z": "7.199999999999999"},{"uuid": "b910b452-4d6c-42a5-aa93-ac8f6e21fbb6","x": "3.5999999999999996","y": "0.01","z": "7.199999999999999"},{"uuid": "78fb2773-8a75-4e9c-8d44-a7f6a6d66526","x": "4.8","y": "0.01","z": "7.199999999999999"},{"uuid": "c7bd242b-3dae-462d-bcfd-bcd9deace844","x": "7.199999999999999","y": "0.01","z": "7.199999999999999"},{"uuid": "9d96de51-37be-4cae-b270-4eb0755ac57a","x": "8.399999999999999","y": "0.01","z": "7.199999999999999"},{"uuid": "aacefff7-94c6-4209-a03b-5ee88277edfb","x": "10.799999999999999","y": "0.01","z": "7.199999999999999"},{"uuid": "4912dd97-b224-41fb-956d-0eed7e78c0c0","x": "11.999999999999998","y": "0.01","z": "7.199999999999999"},{"uuid": "2755f2cd-f8ab-408b-9dfa-afd927fc19c3","x": "0.0","y": "0.01","z": "8.399999999999999"},{"uuid": "7850a1ef-6227-47e0-9a56-77f5ec7a7049","x": "1.2","y": "0.01","z": "8.399999999999999"},{"uuid": "3cdf01e8-8020-4b83-a865-7e633edb9665","x": "3.5999999999999996","y": "0.01","z": "8.399999999999999"},{"uuid": "97aca5b8-46e9-47d2-81ca-cfc992ceb255","x": "4.8","y": "0.01","z": "8.399999999999999"},{"uuid": "25728cca-d195-4ca1-b371-39d61e4f5d22","x": "7.199999999999999","y": "0.01","z": "8.399999999999999"},{"uuid": "5c62b036-7f36-435a-822d-0489cfe6d33d","x": "8.399999999999999","y": "0.01","z": "8.399999999999999"},{"uuid": "95d9a067-417c-42db-9ebb-171a679d74f0","x": "10.799999999999999","y": "0.01","z": "8.399999999999999"},{"uuid": "9918e627-87e2-4983-adeb-17c4bdd13b90","x": "11.999999999999998","y": "0.01","z": "8.399999999999999"},{"uuid": "2d5658c8-70c4-4a94-90a2-4db09459ea1d","x": "0.0","y": "0.01","z": "10.799999999999999"},{"uuid": "334c66aa-e983-4b24-afcf-8d7274afa24e","x": "1.2","y": "0.01","z": "10.799999999999999"},{"uuid": "f87cca3a-23db-498e-81ce-567c4529e4c6","x": "3.5999999999999996","y": "0.01","z": "10.799999999999999"},{"uuid": "68245629-6c86-4dcd-9841-fce83f0c9526","x": "4.8","y": "0.01","z": "10.799999999999999"},{"uuid": "5493f711-2730-4a1b-90d8-0f62314eb599","x": "7.199999999999999","y": "0.01","z": "10.799999999999999"},{"uuid": "28e99554-4651-441e-b4eb-9f2989f18236","x": "8.399999999999999","y": "0.01","z": "10.799999999999999"},{"uuid": "ddf46164-90c5-4ef9-808f-9f742ddf555f","x": "10.799999999999999","y": "0.01","z": "10.799999999999999"},{"uuid": "818dd234-1752-491f-bd9a-a3c8144c72f9","x": "11.999999999999998","y": "0.01","z": "10.799999999999999"},{"uuid": "fe5db81c-adac-43f7-ad3c-faf2cbdfd758","x": "0.0","y": "0.01","z": "11.999999999999998"},{"uuid": "9a3463f7-9e68-4810-b874-d18c6dff8f69","x": "1.2","y": "0.01","z": "11.999999999999998"},{"uuid": "c344074f-a833-4380-89ff-3f25c8f97128","x": "3.5999999999999996","y": "0.01","z": "11.999999999999998"},{"uuid": "15d69a8c-e83c-4559-8037-07fcad619e6b","x": "4.8","y": "0.01","z": "11.999999999999998"},{"uuid": "32d45fd9-e7c2-4ff6-a0d6-43ea2a44c486","x": "7.199999999999999","y": "0.01","z": "11.999999999999998"},{"uuid": "39229a54-c9d5-4816-b07d-ea5a0049ae4e","x": "8.399999999999999","y": "0.01","z": "11.999999999999998"},{"uuid": "ba8705d2-8aaa-4c9d-8b97-51df29d428dd","x": "10.799999999999999","y": "0.01","z": "11.999999999999998"},{"uuid": "3f5db6b6-f568-4d6a-aeb7-9e97116e1833","x": "11.999999999999998","y": "0.01","z": "11.999999999999998"}]})

    // // commandHandler({
    // //     command: "rack_positions",
    // //     parameters: [ { uuid: "1", x: 0, y: 0.01, z: 0 }, { uuid: "2", x: 1.2, y: 0.01, z: 0 }, { uuid: "3", x: 1.2, y: 0.01, z: 1.2 }, { uuid: "4", x: 0, y: 0.01, z: 1.2 } ]
    // // })

    // commandHandler({
    //     command: "build",
    //     parameters: {
    //         uuid: "robot1",
    //         type: "robot",
    //         x: 0,
    //         y: 0,
    //         z: 0,
    //         rotationX: 0,
    //         rotationY: 0,
    //         rotationZ: 0
    //     }
    // })

    // for (let x = 0; x < 15; x += 3) {
    //     for (let y = 0; y < 15; y += 3) {
    //         commandHandler({
    //             command: "build",
    //             parameters: {
    //                 uuid: "rack1",
    //                 type: "rack",
    //                 x: x,
    //                 y: 1,
    //                 z: y,
    //                 rotationX: 0,
    //                 rotationY: 0,
    //                 rotationZ: 0
    //             }
    //         })
    //     }   
    // }

    // commandHandler({
    //     command: "build",
    //     parameters: {
    //         uuid: "truck1",
    //         type: "truck",
    //         x: 2,
    //         y: -1.55,
    //         z: -7.1,
    //         rotationX: 0,
    //         rotationY: 0,
    //         rotationZ: 0
    //     }
    // })

    // commandHandler({
    //     command: "update",
    //     parameters: {
    //         uuid: "robot1",
    //         x: 10,
    //         y: 0.05,
    //         z: 10,
    //         rotationX: 0,
    //         rotationY: 0,
    //         rotationZ: 0
    //     }
    // })

    const socket = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/connectToSimulation");
    socket.onmessage = event => commandHandler( JSON.parse(event.data) );
}

function commandHandler(command) {
//    console.log("[main.js] function commandHandler")
    console.log(command);
    switch (command.command) {
        case "rack_positions":
            buildWarehouse(command.parameters);
            break;
        case "build":
            build(command.parameters)
            break;
        case "update":
            update(command.parameters);
            break;
        case "pick_up":
            pickUp()
            break;
        case "drop_off":
            dropOff()
            break;
        default:
            console.log("command did not match")
            break;
    }
}

async function build(parameters) {
    console.log("[main.js] function build")
    switch (parameters.type) {
        case "robot":
            let robot = new Robot( parameters.uuid );
            robot.moveTo(parameters.x, parameters.y, parameters.z);
            robot.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
            world.addObject( robot );
            break;
        case "rack":
            let simpleRack = new SimpleRack( parameters.uuid );
            simpleRack.moveTo(parameters.x, parameters.y, parameters.z);
            simpleRack.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
            world.addObject( simpleRack );
            break;    
        // case "rack":
        //     let rack = new Rack();
        //     await rack.loadObject( parameters.uuid );
        //     rack.moveTo(parameters.x, parameters.y, parameters.z);
        //     rack.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
        //     world.addObject( rack );
        //     break;
        case "truck":
            let truck = new Truck();
            await truck.loadObject( parameters.uuid );
            truck.moveTo(parameters.x, parameters.y, parameters.z);
            truck.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
            world.addObject( truck );
            break;

        default:
            console.log("[main.js] function build")
            break;
    }
}

function buildWarehouse(parameters) {
    console.log("[main.js] function buildWarehouse")
    let warehouse = new WareHouse( parameters );
    world.addObject( warehouse );
}

function update(parameters) {
//    console.log("[main.js] function update");
//    console.log(world.worldObjects)
    const mesh = world.worldObjects[parameters.uuid].moveTo(parameters.x, parameters.y, parameters.z);
    const frames = 6; //WIP convert to frames!
//    world.setAnimation( mesh, frames, {
//        x: parameters.x,
//        y: parameters.y,
//        z: parameters.z
//    }, {
//        x: parameters.rotationX,
//        y: parameters.rotationY,
//        z: parameters.rotationZ
//    } );
}

function pickUp(robotUuid, rackUuid) {
    console.log("[main.js] function pickUp", robotUuid, rackUuid)
}

function dropOff(robotUuid, positionUuid) {
    console.log("[main.js] function dropOff", robotUuid, positionUuid)
}