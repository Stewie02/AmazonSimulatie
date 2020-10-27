import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import MovableObject from './super/MovableObject.js';

export default class TestNode extends MovableObject{
    /**
     * Used to display the nodes of the pathfinding algorithm.
     * Used for debugging.
     * @param {Float} diameter 
     */
    constructor(diameter){
        const geometry = new THREE.SphereGeometry(diameter / 8, 32, 32);
        const material = new THREE.MeshBasicMaterial( { 
            color: 0x0,
            side: THREE.DoubleSide,
            fog: false, //Only the truck is affected by fog.
        })
        let mesh = new THREE.Mesh(geometry, material);
        super(mesh)
    }
}