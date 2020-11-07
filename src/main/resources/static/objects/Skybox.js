import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import MovableObject from './super/MovableObject.js';

export default class Skybox extends MovableObject{
    /**
     * Builds skybox.
     * The skybox is a sphere and only 360° images can be mapped.
     * @param {Float} diameter 
     */
    constructor(diameter){
        const geometry = new THREE.SphereGeometry(diameter, 32, 32);
        const material = new THREE.MeshBasicMaterial( { 
            color: 0xffffff,
            side: THREE.BackSide,
            fog: false,  //Only the truck is affected by fog.
            map: new THREE.TextureLoader().load("textures/hangar_skybox.jpeg")
        })
        let mesh = new THREE.Mesh(geometry, material);
        super(mesh)
    }
}