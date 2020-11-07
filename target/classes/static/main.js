import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import PreMeshLoader from './PreMeshLoader.js'
import World from './objects/World.js';
import WareHouse from './objects/WareHouse.js'
import Robot from './objects/Robot.js';
import Truck from './objects/imported/Truck/Truck.js';
import SimpleRack from './objects/SimpleRack.js';
import Manager from './objects/imported/Manager/Manager.js';

let world, capacity;
let robotCount = 1;
const meshLoader = new PreMeshLoader()

/**
 * Where is all begins...
 */
window.onload = function () {
    world = new World();

    //click event listener for the button in the menu
    document.getElementById('darkMode').addEventListener("click", toggleDarkMode);
    document.getElementById('sound').addEventListener("click", toggleSound);
    document.getElementById('rotate').addEventListener("click", toggleRotation);
    document.getElementById('centerCam').addEventListener("click", centerCam);

    //websocket, connects to server and parses the data to pass it on to the commandHandler function.
    const socket = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/connectToSimulation");
    socket.onmessage = event => commandHandler( JSON.parse(event.data) );
}

/**
 * Handles the incoming commands from the websocket
 * @param {string} command
 */
function commandHandler(command) {
    switch (command.command) {
        case "rack_positions":
            console.log(command.parameters);
            buildWarehouse(command.parameters);
            break;
        case "build":
            build(command.parameters);
            break;
        case "update":
            update(command.parameters);
            break;
        case "pick_up":
            console.log(command);
            pickUp(command.parameters);
            break;
        case "drop_off":
            dropOff(command.parameters);
            break;
        default:
            console.log("command did not match");
            break;
    }
}

/**
 * Builds the warehouse to the desired dimensions.
 * And reads the capacity for display.
 * Also if a rack position is meant to be occupied a rack will be build on the position.
 * Finally the world will be created and the warehouse will be added to it.
 * At last the dashboard update loop wil be initialised. 
 * @param {object} parameters Rack positions
 */
function buildWarehouse(parameters) {
    const warehouse = new WareHouse( parameters ); //Build warehouse
    capacity = warehouse.rackSpots.mesh.children.length; //Set capacity for occupation calculation in updateDashboard()
    document.getElementById('capacity').innerText =  capacity; //Set capacity on dashboard
    
    //Build racks on spots marked as occupied
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

/**
 * Builds any 3d object that can be build in this world.
 * For 3d objects that we be display multiple times a pre mesh loader helps to ensure that the textures will only be loaded once.
 * This function is async because we have to wait before the an imported object is loaded before we can manipulate its mesh.
 * @param {object} parameters 
 */
async function build(parameters) {
    switch (parameters.type) {
        case "robot":
            //Build robot with UUID to keep track of it, pre loaded mesh for performance, and robotCount to give it a name and a top-sticker.
            let robot = new Robot( parameters.uuid, meshLoader.robot, robotCount ); 
            robot.moveTo(parameters.x, parameters.y, parameters.z);
            robot.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
            world.addObject( robot );
            robotCount++

            //In cases that the robot is build carrying a rack
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
            let simpleRack = new SimpleRack( parameters.uuid, meshLoader.simpleRack ); //Pre mesh loader helps with performance
            simpleRack.moveTo(parameters.x, parameters.y, parameters.z);
            simpleRack.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
            world.addObject( simpleRack );
            break;    
        case "truck":
            let truck = new Truck();
            await truck.loadObject( parameters.uuid ); //Here we wait till the 3d model is loaded, before we try to move and rotate it.
            truck.moveTo(parameters.x, parameters.y, parameters.z);
            truck.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
            world.addObject( truck );
            break;
        case "manager":
            let manager = new Manager()
            await manager.loadObject( parameters.uuid ); //Here we wait till the 3d model is loaded, before we try to move and rotate it.
            manager.moveTo(parameters.x, parameters.y, parameters.z);
            manager.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
            world.addObject( manager );
            break;
        default:
            console.log("build command did nog match")
            break;
    }
}

/**
 * Updates position and rotation of any 3d object based on their UUID.
 * It also updates the userData foreach 3d object and keeps track of the meters run that way.
 * In the case that the simulation is started midway, racks will be build on the robots if they where carrying any.
 * @param {object} parameters 
 */
function update(parameters) {
    //update meters run
    let object = world.worldObjects[parameters.uuid];
    let x =  object.mesh.position.x - parameters.x;
    let y = object.mesh.position.y - parameters.y;
    let z = object.mesh.position.z - parameters.z;
    const deltaX = Math.sqrt(x * x);
    const deltaY = Math.sqrt(y * y);
    const deltaZ = Math.sqrt(z * z);
    object.mesh.userData.metersRun += deltaX + deltaY + deltaZ;

    //build rack if requested
    if (parameters.rack !== undefined && object.mesh.children[1] === undefined) {
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
        object.pickUp( world.worldObjects[parameters.rack].getMesh() )
    }

    //update position en rotation
    object.moveTo(parameters.x, parameters.y, parameters.z);
    object.rotate(parameters.rotationX, parameters.rotationY, parameters.rotationZ);
}

/**
 * Finds the robot you want to control then finds the rack you want to pick up.
 * And adds the rack mesh to the robot Group.
 * @param {object} parameters 
 */
function pickUp(parameters) {
    world.worldObjects[parameters.robot].pickUp( world.worldObjects[parameters.rack].getMesh() )
}

/**
 * Finds the robot you want to control then dropOff the rack he is currently carrying.
 * @param {object} parameters 
 */
function dropOff(parameters) {
    world.worldObjects[parameters.robot].dropOff( world, parameters.position )
}

/**
 * Toggles the dark mode.
 * Sets: warehouse wall color, scene color, fog color, buttons background and letter color, and changes the skybox map texture.
 */
function toggleDarkMode() {
    let walls = world.scene.getObjectByName("walls")
    let buttons = document.getElementsByClassName('tDark');
    if ( walls.material.color.getHexString() === "666666" ) {
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

/**
 * Sets sounds on or off
 */
function toggleSound() {
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

/**
 * Sets auto rotation on or off
 */
function toggleRotation() {
    world.cameraControls.autoRotate = world.cameraControls.autoRotate ? false : true;
}

/**
 * Centers the camera to the begin position again.
 */
function centerCam() {
    world.camera.position.set(-5 , 7, world.width + 5);
    world.cameraControls.target = new THREE.Vector3(world.length / 2, 1.7, world.width / 2)
}

/**
 * Updates the dashboard variables every second.
 */
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