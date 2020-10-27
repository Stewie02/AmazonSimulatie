import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import Object from './super/Object.js';

/**
 * Builds the platform for walking on.
 * @param {Float} walkwayLength 
 * @param {Float} walkwayWidth 
 */
const buildPlatform = (walkwayLength, walkwayWidth) => {
    const geometry = new THREE.PlaneGeometry(walkwayWidth, walkwayLength, 32);

    //Wraps a texture to the platform
    let texture = new THREE.TextureLoader().load("textures/metal_walkway.png");
    texture.wrapS = THREE.RepeatWrapping;
    texture.wrapT = THREE.RepeatWrapping;
    texture.repeat.set( 2, walkwayLength );

    const material = new THREE.MeshStandardMaterial({
        color: 0x333333,
        side: THREE.DoubleSide,
        emissive: 0x0,
        map: texture,
        fog: false, //Only the truck is affected by fog.
        transparent: true
    });
    let mesh = new THREE.Mesh(geometry, material);
    mesh.rotation.x = -90 * Math.PI / 180;

    //Set shadows on
    mesh.recieveShadow = true;
    mesh.castShadow = true;
    return mesh
}

/**
 * Builds a horizontal handrail that spans the length of the walkway.
 * @param {Float} walkwayLength 
 * @param {Float} walkwayWidth 
 */
const rail = (walkwayLength, walkwayWidth) => {
    const radius =  .03;  
    const radialSegments = 16;  
    const geometry = new THREE.CylinderBufferGeometry(radius, radius, walkwayLength, radialSegments);
    const material = new THREE.MeshStandardMaterial({
        color: 0x333333,
        fog: false //Only the truck is affected by fog.
    });
    let mesh = new THREE.Mesh(geometry, material);
    mesh.rotation.x = -90 * Math.PI / 180;
    mesh.position.set(-(walkwayWidth/2),1,0);
    mesh.castShadow = true; //Makes the handrail cast a nice shadow if affected by light
    return mesh
}

/**
 * Builds a vertical bar.
 */
const bar = () => {
    const height = 1;
    const radius =  .03;  
    const radialSegments = 16;  
    const geometry = new THREE.CylinderBufferGeometry(radius, radius, height, radialSegments);
    const material = new THREE.MeshStandardMaterial({
        color: 0x333333,
        fog: false //Only the truck is affected by fog.
    });
    let mesh = new THREE.Mesh(geometry, material);
    mesh.castShadow = true; //Cast a shadow if affected by light
    mesh.position.y = height / 2; 
    return mesh
}

/**
 * Makes an line of bars that spans the length of the walkway.
 * @param {Float} walkwayLength 
 * @param {Float} walkwayWidth 
 */
const bars = (walkwayLength, walkwayWidth) => {
    let group = new THREE.Group();
    for (let space = 0.5; space < walkwayLength; space++) {
        let xbar = bar();
        xbar.position.z = space;
        group.add(xbar);
    }
    group.position.set( -(walkwayWidth / 2), 0, -(walkwayLength /2) )
    return group
}

/**
 * Builds a door
 * @param {Texture} texture 
 */
const door = (texture) => {
    const height = 2.8;
    const geometry = new THREE.PlaneGeometry(1.5, height, 32);
    const material = new THREE.MeshStandardMaterial({
        color: 0xcccccc,
        emissive: 0x0,
        map: texture,
        fog: false, //Only the truck is affected by fog.
        transparent: true
    });
    let mesh = new THREE.Mesh(geometry, material);
    mesh.recieveShadow = true;
    mesh.position.set(0, height / 2, 0)
    return mesh
}

export default class Walkway extends Object{
    /**
     * Builds the walkway
     * @param {Float} warehouseLength 
     * @param {Float} warehouseWidth 
     * @param {Float} margin 
     */
    constructor(warehouseLength, warehouseWidth, margin){
        const walkwayWidth = 1.5;
        const walkwayLength = warehouseWidth + margin;
        let group = new THREE.Group();
        group.add( buildPlatform(walkwayLength, walkwayWidth) ); //Adds platform
        group.add( rail(walkwayLength, walkwayWidth) ); //Adds handrail
        group.add( bars(walkwayLength, walkwayWidth) ); //Adds vertical bars that 'support' the handrail
        
        //Add doors to each end of the walkway
        const doorTexture = new THREE.TextureLoader().load("textures/man_door.jpg")
        let leftDoor = door(doorTexture);
        leftDoor.position.z = -(walkwayLength/2) + 0.01;
        let rightDoor = door(doorTexture);
        rightDoor.position.z = walkwayLength/2 - 0.01;
        rightDoor.rotation.y = -180 * Math.PI / 180;

        group.add( leftDoor )
        group.add( rightDoor )

        //Sets the correct position of the walkway group, relative to the size of the warehouse. 
        group.position.set( warehouseLength + (margin / 2) - (walkwayWidth / 2), 5, warehouseWidth / 2);
        super(group);
    }
}