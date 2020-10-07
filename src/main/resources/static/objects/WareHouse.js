import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import Object from './super/Object.js';

export default class WareHouse extends Object{
    constructor() {
        const geometry = new THREE.PlaneGeometry(30, 30, 32);
        const material = new THREE.MeshBasicMaterial({ color: 0x364643, side: THREE.DoubleSide });
        let mesh = new THREE.Mesh(geometry, material);
        mesh.rotation.x = Math.PI / 2.0;
        mesh.position.x = 15;
        mesh.position.z = 15;
        super(mesh);
    }
}