import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import Object from './super/Object.js';
import SpotLight from '../lights/SpotLight/SpotLight.js';
import FlashingLight from '../lights/FlashingLight/FlashingLight.js';
import Walkway from './Walkway.js';

/**
 * Build the red squares on the floor.
 * @param {Array} rackPositions 
 */
const buildRackPositions = (rackPositions) => {
    let group = new THREE.Group();
    let length = 0, width = 0;
    
    rackPositions.forEach(pos => {
        const geometry = new THREE.PlaneGeometry(1, 1, 32);
        const material = new THREE.MeshPhongMaterial({
            color: 0xa52019,
            side: THREE.FrontSide,
            emissive: 0x0,
            specular: 0x1a1a1a,
            shininess: 100,
            fog: false //Only the truck is affected by fog.
        });
        let mesh = new THREE.Mesh(geometry, material);
        mesh.receiveShadow = true;
        mesh.position.set(pos.x, pos.y, pos.z);
        mesh.rotation.x = -90 * Math.PI / 180;

        //Sets occupation of spot by rack
        mesh.userData.occupied = false;
        if (pos.rack != undefined ) {
            mesh.userData.occupied = true;
            mesh.userData.uuid = pos.rack;
        }
        mesh.uuid = pos.uuid;
        group.add(mesh);
        
        //Gets the max length and width of the warehouse
        parseFloat(pos.x) > length ? length = parseFloat(pos.x) : null;
        parseFloat(pos.z) > width ? width = parseFloat(pos.z) : null;
    });

    return {mesh: group, length: length, width: width}
}

/**
 * Builds amount of lights based on the surface area it needs to light.
 * @param {Float} length 
 * @param {Float} width 
 * @param {Float} wallHeight 
 */
const buildSpotLights = (length, width, wallHeight) => {
    let group = new THREE.Group();
    const margin = 1
    group.add(new SpotLight(margin, wallHeight, margin).lightGroup);
    group.add(new SpotLight(margin, wallHeight, width - margin).lightGroup);
    group.add(new SpotLight(length - margin, wallHeight, margin).lightGroup);
    group.add(new SpotLight(length - margin , wallHeight, width - margin ).lightGroup);

    const surface = length * width;

    if (surface > 180) {
        group.add(new SpotLight(length / 2, wallHeight, width / 2).lightGroup);
    }

    if (surface > 300) {
        group.add(new SpotLight(length / 2, wallHeight, margin).lightGroup);
        group.add(new SpotLight(margin, wallHeight, width / 2).lightGroup);
        group.add(new SpotLight(length / 2, wallHeight, width - margin).lightGroup);
        group.add(new SpotLight(length - margin, wallHeight, width / 2).lightGroup);
    }

    return group;
}

/**
 * Builds a inside-out box.
 * @param {Float} length 
 * @param {Float} wallHeight 
 * @param {Float} width 
 * @param {Float} margin 
 */
const buildWalls = (length, wallHeight, width, margin) => {
    const wallLength = length + margin;
    const wallWidth = width + margin;
    const wallsGeometry = new THREE.BoxGeometry(wallLength, wallHeight, wallWidth);
    const wallsMaterial = new THREE.MeshPhongMaterial({
        color: 0x666666, 
        emissive: 0x0,
        specular: 0x333333,
        shininess: 10,
        side: THREE.BackSide,
        map: new THREE.TextureLoader().load("textures/concrete-map.jpg"),
        fog: false //Only the truck is affected by fog.
    });
    let wallsMesh = new THREE.Mesh(wallsGeometry, wallsMaterial);

    wallsMesh.receiveShadow = true;
    wallsMesh.position.x = length / 2;
    wallsMesh.position.y = wallHeight / 2;
    wallsMesh.position.z = width / 2;
    wallsMesh.name = "walls"

    return wallsMesh;
}

/**
 * Builds a warehouse door.
 * @param {Float} doorWidth 
 * @param {Float} doorHeight
 * @param {Float} margin 
 */
const buildDoor = (doorWidth, doorHeight, margin) => {
    const geometry = new THREE.PlaneGeometry(doorWidth, doorHeight, 32);
    const material = new THREE.MeshStandardMaterial({
        color: 0x999999,
        side: THREE.FrontSide,
        emissive: 0x0,
        map: new THREE.TextureLoader().load("textures/door.jpg"),
        fog: false //Only the truck is affected by fog.
    });
    let mesh = new THREE.Mesh(geometry, material);
    mesh.position.set( ( doorWidth / 2 ) -0.5, doorHeight / 2, -(margin / 2) + 0.01);
    return mesh;
}

/**
 * Builds a button.
 * @param {Float} margin 
 */
const buildDoorButton = (margin) => {
    let group = new THREE.Group();

    //Case
    const boxWidth = .15;
    const boxGeometry = new THREE.BoxGeometry(.3, .5, boxWidth);
    const boxMaterial = new THREE.MeshStandardMaterial({
        color: 0x999999,
        side: THREE.FrontSide,
        fog: false //Only the truck is affected by fog.
    });
    let box = new THREE.Mesh(boxGeometry, boxMaterial);
    box.castShadow = true;
    box.receiveShadow = true;
    group.add(box);

    //Button
    const radius =  .1;
    const length = .08;
    const radialSegments = 16;  
    const buttonGeometry = new THREE.CylinderBufferGeometry(radius, radius, length, radialSegments);
    const buttonMaterial = new THREE.MeshStandardMaterial({
        color: 0xff0000,
        side: THREE.FrontSide,
        fog: false //Only the truck is affected by fog.
    });
    let button = new THREE.Mesh(buttonGeometry, buttonMaterial);
    button.position.set(0, 0, (length / 2) + (boxWidth /2));
    button.rotation.x = -90 * Math.PI / 180;
    button.castShadow = true;

    group.add(button);

    group.position.set(5, 1.6, (boxWidth / 2) -(margin / 2));
    return group;
}

export default class WareHouse extends Object{
    /**
     * Builds whole the warehouse. These are all the 3d objects that will not move during the simulation.
     * @param {Array} rackPositions 
     */
    constructor(rackPositions) {
        let group = new THREE.Group();
        const wallHeight = 10;
        const margin = 5;

        const rackSpots = buildRackPositions( rackPositions ); //The red squares
        group.add(rackSpots.mesh);

        //Gets the length and width based on the rack positions
        const length = rackSpots.length;
        const width = rackSpots.width;
        
        group.add( buildSpotLights(length, width, wallHeight) ); //Adds the lights
        group.add( buildWalls(length, wallHeight, width, margin) ); //Adds the walls
        group.add( buildDoor(5, 4.5, margin)); //Adds the door
        const flashingLight = new FlashingLight(4.4, 5, -(margin / 2));
        group.add( flashingLight.mesh ); //Adds the flashing light
        group.add( buildDoorButton(margin) ); //Adds door button
        let walkway = new Walkway(length, width, margin);
        group.add( walkway.getMesh() ); //Adds walkway

        group.name = "warehouse";
        group.uuid = "warehouse";
        super(group);

        //Sets some variables to use in main.js
        this.rackSpots = rackSpots;
        this.width = width;
        this.length = length;
        this.height = wallHeight;
    }
}