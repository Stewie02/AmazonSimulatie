//import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
//import PreMeshLoader from './PreMeshLoader.js'
//import World from './objects/World.js';
//import WareHouse from './objects/WareHouse.js'
//import Robot from './objects/Robot.js';
//import Truck from './objects/imported/Truck/Truck.js';
//import SimpleRack from './objects/SimpleRack.js';
//import Manager from './objects/imported/Manager/Manager.js';
//
//let world;
//const meshLoader = new PreMeshLoader()
//
//window.onload = function () {
//    world = new World();
//
//    document.getElementById('darkMode').addEventListener("click", toggleDarkMode);
//    const socket = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/connectToSimulation");
//    socket.onmessage = event => commandHandler( JSON.parse(event.data) );
//}
//
//function commandHandler(command) {
//    switch (command.command) {
//        case "rack_positions":
//            buildWarehouse(command.parameters);
//            break;
//        case "build":
//            build(command.parameters);
//            break;
//        case "update":
//            update(command.parameters);
//            break;
//        case "pick_up":
//            pickUp(command.parameters);
//            break;
//        case "drop_off":
//            dropOff(command.parameters);
//            break;
//        default:
//            console.log("command did not match");
//            break;
//    }
//}
//
//async function build(parameters) {
//    switch (parameters.type) {
//        case "robot":
//            let robot = new Robot( parameters.uuid, meshLoader.robot );
//            robot.moveTo(parameters.x, parameters.y, parameters.z);
//            robot.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
//            world.addObject( robot );
//            break;
//        case "rack":
//            let simpleRack = new SimpleRack( parameters.uuid, meshLoader.simpleRack );
//            simpleRack.moveTo(parameters.x, parameters.y, parameters.z);
//            simpleRack.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
//            world.addObject( simpleRack );
//            break;
//        case "truck":
//            let truck = new Truck();
//            await truck.loadObject( parameters.uuid );
//            truck.moveTo(parameters.x, parameters.y, parameters.z);
//            truck.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
//            world.addObject( truck );
//            break;
//        case "manager":
//            let manager = new Manager()
//            await manager.loadObject( parameters.uuid );
//            manager.moveTo(parameters.x, parameters.y, parameters.z);
//            manager.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
//            world.addObject( manager );
//            break;
//        default:
//            console.log("build command did nog match")
//            break;
//    }
//}
//
//function buildWarehouse(parameters) {
//    const warehouse = new WareHouse( parameters );
//    warehouse.rackSpots.mesh.children.map(spot => {
//        if (spot.userData.occupied) {
//            build({
//                uuid: spot.userData.uuid,
//                type: "rack",
//                x: spot.position.x,
//                y: 0.85,
//                z: spot.position.z,
//                rotationX: 0,
//                rotationY: 0,
//                rotationZ: 0
//            })
//        }
//    })
//    world.init(warehouse.length, warehouse.width);
//    world.addObject( warehouse );
//}
//
//function update(parameters) {
//    world.worldObjects[parameters.uuid].moveTo(parameters.x, parameters.y, parameters.z);
//}
//
//function pickUp(parameters) {
//    world.worldObjects[parameters.robot].pickUp( world.worldObjects[parameters.rack].getMesh() )
//}
//
//function dropOff(parameters) {
//    world.worldObjects[parameters.robot].dropOff(world.scene, parameters.position)
//}
//
//function toggleDarkMode(event) {
//    let walls = world.scene.getObjectByName("walls")
//    if (walls.material.color.getHexString() === "666666") {
//        walls.material.color.setHex( 0x0 );
//        world.renderer.setClearColor( 0x0 );
//        world.scene.fog.color.setHex( 0x0 );
//        world.skybox.mesh.material.map = new THREE.TextureLoader().load("textures/starbox.jpg")
//        world.skybox.mesh.material.needsUpdate = true
//        event.target.style.color = 'black';
//    } else {
//        walls.material.color.setHex( 0x666666 );
//        world.renderer.setClearColor( 0xffffff );
//        world.scene.fog.color.setHex( 0xffffff );
//        world.skybox.mesh.material.map = new THREE.TextureLoader().load("textures/warehouse_skybox.jpg")
//        world.skybox.mesh.material.needsUpdate = true
//        event.target.style.color = 'white';
//    }
//}

import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import PreMeshLoader from './PreMeshLoader.js'
import World from './objects/World.js';
import WareHouse from './objects/WareHouse.js'
import Robot from './objects/Robot.js';
import Truck from './objects/imported/Truck/Truck.js';
import SimpleRack from './objects/SimpleRack.js';
import Manager from './objects/imported/Manager/Manager.js';
import TestNode from './objects/TestNode.js';

let world;
const meshLoader = new PreMeshLoader()

window.onload = function () {
    world = new World();

    document.getElementById('darkMode').addEventListener("click", toggleDarkMode);
    const socket = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/connectToSimulation");
    socket.onmessage = event => { console.log(event.data); commandHandler( JSON.parse(event.data) ) };
}

function commandHandler(command) {
    switch (command.command) {
        case "rack_positions":
            buildWarehouse(command.parameters);
            break;
        case "build":
            build(command.parameters);
            break;
        case "update":
            update(command.parameters);
            break;
        case "pick_up":
            pickUp(command.parameters);
            break;
        case "drop_off":
            dropOff(command.parameters);
            break;
        case "node":
            const node = new TestNode(1);
            const pos = command.parameters;
            node.moveTo(pos.x, pos.y, pos.z);
            world.addObject( node );
            break;
        default:
            console.log("command did not match");
            break;
    }
}

async function build(parameters) {
    switch (parameters.type) {
        case "robot":
            let robot = new Robot( parameters.uuid, meshLoader.robot );
            robot.moveTo(parameters.x, parameters.y, parameters.z);
            robot.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
            world.addObject( robot );
            break;
        case "rack":
            let simpleRack = new SimpleRack( parameters.uuid, meshLoader.simpleRack );
            simpleRack.moveTo(parameters.x, parameters.y, parameters.z);
            simpleRack.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
            world.addObject( simpleRack );
            break;
        case "truck":
            let truck = new Truck();
            await truck.loadObject( parameters.uuid );
            truck.moveTo(parameters.x, parameters.y, parameters.z);
            truck.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
            world.addObject( truck );
            break;
        case "manager":
            let manager = new Manager()
            await manager.loadObject( parameters.uuid );
            manager.moveTo(parameters.x, parameters.y, parameters.z);
            manager.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
            world.addObject( manager );
            break;
        default:
            console.log("build command did nog match")
            break;
    }
}

function buildWarehouse(parameters) {
    const warehouse = new WareHouse( parameters );
    warehouse.rackSpots.mesh.children.map(spot => {
        if (spot.userData.occupied) {
            build({
                uuid: spot.userData.uuid,
                type: "rack",
                x: spot.position.x,
                y: 0.85,
                z: spot.position.z,
                rotationX: 0,
                rotationY: 0,
                rotationZ: 0
            })
        }
    })
    world.init(warehouse.length, warehouse.width);
    world.addObject( warehouse );
}

function update(parameters) {
    world.worldObjects[parameters.uuid].moveTo(parameters.x, parameters.y, parameters.z);
}

function pickUp(parameters) {
    world.worldObjects[parameters.robot].pickUp( world.worldObjects[parameters.rack].getMesh() )
}

function dropOff(parameters) {
    world.worldObjects[parameters.robot].dropOff(world.scene, parameters.position)
}

function toggleDarkMode(event) {
    let walls = world.scene.getObjectByName("walls")
    if (walls.material.color.getHexString() === "666666") {
        walls.material.color.setHex( 0x0 );
        world.renderer.setClearColor( 0x0 );
        world.scene.fog.color.setHex( 0x0 );
        world.skybox.mesh.material.map = new THREE.TextureLoader().load("textures/starbox.jpg")
        world.skybox.mesh.material.needsUpdate = true
        event.target.style.color = 'black';
    } else {
        walls.material.color.setHex( 0x666666 );
        world.renderer.setClearColor( 0xffffff );
        world.scene.fog.color.setHex( 0xffffff );
        world.skybox.mesh.material.map = new THREE.TextureLoader().load("textures/warehouse_skybox.jpg")
        world.skybox.mesh.material.needsUpdate = true
        event.target.style.color = 'white';
    }
}