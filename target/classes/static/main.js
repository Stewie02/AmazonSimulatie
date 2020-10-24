import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import PreMeshLoader from './PreMeshLoader.js'
import World from './objects/World.js';
import WareHouse from './objects/WareHouse.js'
import Robot from './objects/Robot.js';
import Truck from './objects/imported/Truck/Truck.js';
import SimpleRack from './objects/SimpleRack.js';
import Manager from './objects/imported/Manager/Manager.js';
import TestNode from './objects/TestNode.js';

let world, capacity;
let robotCount = 1;
const meshLoader = new PreMeshLoader()

window.onload = function () {
    world = new World();

    document.getElementById('darkMode').addEventListener("click", toggleDarkMode);
    document.getElementById('sound').addEventListener("click", toggleSound);
    document.getElementById('rotate').addEventListener("click", toggleRotation);
    document.getElementById('centerCam').addEventListener("click", centerCam);

     const socket = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/connectToSimulation");
     socket.onmessage = event => commandHandler( JSON.parse(event.data) );
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
//            const node = new TestNode(.2);
//            const pos = command.parameters;
//            node.moveTo(pos.x, pos.y, pos.z);
//            world.addObject( node );
            break;
        default:
            console.log("command did not match");
            break;
    }
}

async function build(parameters) {
    switch (parameters.type) {
        case "robot":
            let robot = new Robot( parameters.uuid, meshLoader.robot, robotCount );
            robot.moveTo(parameters.x, parameters.y, parameters.z);
            robot.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
            world.addObject( robot );
            robotCount++
            if ( parameters.rack !== undefined ) {
                build({
                    uuid: parameters.rack,
                    type: "rack",
                    x: 0,
                    y: 0.85,
                    z: 0,
                    rotationX: 0,
                    rotationY: 0,
                    rotationZ: 0
                })
                world.worldObjects[parameters.uuid].pickUp( world.worldObjects[parameters.rack].getMesh() )
            }
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
    capacity = warehouse.rackSpots.mesh.children.length
    document.getElementById('capacity').innerText = capacity;
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
    updateDashboard();
}

function update(parameters) {
    let object = world.worldObjects[parameters.uuid];
    let x =  object.mesh.position.x - parameters.x;
    let y = object.mesh.position.y - parameters.y;
    let z = object.mesh.position.z - parameters.z;
    const deltaX = Math.sqrt(x * x);
    const deltaY = Math.sqrt(y * y);
    const deltaZ = Math.sqrt(z * z);
    object.mesh.userData.metersRun += deltaX + deltaY + deltaZ;
    object.moveTo(parameters.x, parameters.y, parameters.z);
}

function pickUp(parameters) {
    world.worldObjects[parameters.robot].pickUp( world.worldObjects[parameters.rack].getMesh() )
}

function dropOff(parameters) {
    world.worldObjects[parameters.robot].dropOff(world, parameters.position)
}

function toggleDarkMode(event) {
    let walls = world.scene.getObjectByName("walls")
    let buttons = document.getElementsByClassName('tDark');
    if (walls.material.color.getHexString() === "666666") {
        walls.material.color.setHex( 0x0 );
        world.renderer.setClearColor( 0x0 );
        world.scene.fog.color.setHex( 0x0 );
        world.skybox.mesh.material.map = new THREE.TextureLoader().load("textures/starbox.jpg")
        Object.keys(buttons).map(key => {
            let style = buttons[key].style; 
            style.color = 'white';
            style.backgroundColor = 'rgba(0, 0, 0, 0.5)';
        })
    } else {
        walls.material.color.setHex( 0x666666 );
        world.renderer.setClearColor( 0xffffff );
        world.scene.fog.color.setHex( 0xffffff );
        world.skybox.mesh.material.map = new THREE.TextureLoader().load("textures/hangar_skybox.jpeg")
        Object.keys(buttons).map(key => {
            let style = buttons[key].style; 
            style.color = 'black';
            style.backgroundColor = 'rgba(255, 255, 255, 0.75)';
        })
    }
    world.skybox.mesh.material.needsUpdate = true
}

function toggleSound(event) {
    let sound = document.getElementById('truckDocking');
    let soundIcon = document.getElementById('soundIcon');
    if (sound.muted) {
        sound.muted = false
        soundIcon.setAttribute("class", "fas fa-volume-up");
    } else {
        sound.muted = true;
        soundIcon.setAttribute("class", "fas fa-volume-mute");
    }
}

function toggleRotation(event) {
    world.cameraControls.autoRotate ? world.cameraControls.autoRotate = false : world.cameraControls.autoRotate = true;
}

function centerCam(event) {
    world.camera.position.set(-5 , 7, world.width + 5);
    world.cameraControls.target = new THREE.Vector3(world.length / 2, 1.7, world.width / 2)
}

function updateDashboard() {
    let racks = 0, robots = 0;
    let htmlRobotsStatus = [];
    Object.keys(world.worldObjects).map(key => {
        const object = world.worldObjects[key].mesh;
        const metersRun = object.userData.metersRun;
        let deltaMeters = metersRun - object.userData.lastMetersRun;
        object.userData.lastMetersRun = metersRun;
        const kmh = deltaMeters * 3.6;

        switch (object.name) {
            case "simpleRack":
                racks++;
                break;
            case "robot":
                robots++;
                htmlRobotsStatus.push('<tr><td>'+ object.userData.number +'</td><td>' + Math.round( kmh * 10)/ 10 + '</td><td>' + Math.round(metersRun * 10)/ 10 + '</td><td>'+ object.userData.movedItems +'</td></tr>');
                break;    
            default:
                break;
        }
    })

    document.getElementById('occupationLevel').innerText = Math.round((racks / capacity)*100);
    document.getElementById('nrRacks').innerHTML = racks;
    document.getElementById('nrRobots').innerHTML = robots;
    document.getElementById('robotsStatus').innerHTML = htmlRobotsStatus.join('');
    setTimeout(() => updateDashboard(), 1000)
}