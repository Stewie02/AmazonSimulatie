import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import MovableObject from './super/MovableObject.js';


export default class SimpleRack extends MovableObject{
    constructor(uuid) {
        const geometry = new THREE.BoxGeometry(0.8, 2, 0.8);
        const material =  new THREE.MeshPhongMaterial( { 
            color: 0x0,
            emissive: 0x0,
            specular: 0x222222,
            shinniness: 100
        } );
        let mesh = new THREE.Mesh(geometry, material);
        mesh.castShadow = true;
        mesh.receiveShadow = false;
        mesh.position.y = 0.15;

        super(mesh);
    }
}