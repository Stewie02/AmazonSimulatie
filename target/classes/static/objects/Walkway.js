import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import Object from './super/Object.js';

const buildPlatform = (walkwayLength, walkwayWidth) => {
    const geometry = new THREE.PlaneGeometry(walkwayWidth, walkwayLength, 32);

    let texture = new THREE.TextureLoader().load("textures/metal_walkway.png");
    texture.wrapS = THREE.RepeatWrapping;
    texture.wrapT = THREE.RepeatWrapping;
    texture.repeat.set( 2, walkwayLength );

    const material = new THREE.MeshStandardMaterial({
        color: 0x333333,
        side: THREE.DoubleSide,
        emissive: 0x0,
        map: texture,
        fog: false,
        transparent: true
    });
    let mesh = new THREE.Mesh(geometry, material);
    mesh.rotation.x = -90 * Math.PI / 180;
    mesh.recieveShadow = true;
    mesh.castShadow = true;
    return mesh
}

const rail = (walkwayLength, walkwayWidth) => {
    const radius =  .03;  
    const radialSegments = 16;  
    const geometry = new THREE.CylinderBufferGeometry(radius, radius, walkwayLength, radialSegments);
    const material = new THREE.MeshStandardMaterial({
        color: 0x333333,
        fog: false
    });
    let mesh = new THREE.Mesh(geometry, material);
    mesh.rotation.x = -90 * Math.PI / 180;
    mesh.position.set(-(walkwayWidth/2),1,0);
    mesh.castShadow = true;
    return mesh
}

const bar = () => {
    const height = 1;
    const radius =  .03;  
    const radialSegments = 16;  
    const geometry = new THREE.CylinderBufferGeometry(radius, radius, height, radialSegments);
    const material = new THREE.MeshStandardMaterial({
        color: 0x333333,
        fog: false
    });
    let mesh = new THREE.Mesh(geometry, material);
    mesh.castShadow = true;
    mesh.position.y = height / 2;
    return mesh
}

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

const door = (texture) => {
    const height = 2.8;
    const geometry = new THREE.PlaneGeometry(1.5, height, 32);
    const material = new THREE.MeshStandardMaterial({
        color: 0xcccccc,
        emissive: 0x0,
        map: texture,
        fog: false,
        transparent: true
    });
    let mesh = new THREE.Mesh(geometry, material);
    mesh.recieveShadow = true;
    mesh.position.set(0, height / 2, 0)
    return mesh
}

export default class Walkway extends Object{
    constructor(warehouseLength, warehouseWidth, margin){
        const walkwayWidth = 1.5;
        const walkwayLength = warehouseWidth + margin;
        let group = new THREE.Group();
        group.add( buildPlatform(walkwayLength, walkwayWidth) );
        group.add( rail(walkwayLength, walkwayWidth) );
        group.add( bars(walkwayLength, walkwayWidth) );
        
        const doorTexture = new THREE.TextureLoader().load("textures/man_door.jpg")
        let leftDoor = door(doorTexture);
        leftDoor.position.z = -(walkwayLength/2) + 0.01;
        let rightDoor = door(doorTexture);
        rightDoor.position.z = walkwayLength/2 - 0.01;
        rightDoor.rotation.y = -180 * Math.PI / 180;

        group.add( leftDoor )
        group.add( rightDoor )

        group.position.set( warehouseLength + (margin / 2) - (walkwayWidth / 2), 5, warehouseWidth / 2);
        super(group);
    }
}