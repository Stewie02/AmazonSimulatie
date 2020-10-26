import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import MovableObject from './super/MovableObject.js';

export default class TestNode extends MovableObject{
    constructor(diameter){
        const geometry = new THREE.SphereGeometry(diameter, 32, 32);
        const material = new THREE.MeshBasicMaterial( { 
            color: 0xffffff,
            side: THREE.DoubleSide,
            fog: false,
        })
        let mesh = new THREE.Mesh(geometry, material);
        super(mesh)
    }
}