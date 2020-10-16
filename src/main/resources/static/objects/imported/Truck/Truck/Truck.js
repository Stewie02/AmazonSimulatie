import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import { loadGLTF } from '../../../../ObjectLoader.js';
import MovableObject from '../../../super/MovableObject.js';

export default class Truck extends MovableObject {
    constructor(){
        super()
    }

    // async loadObject(uuid) {
    //     let mesh = await loadOBJMTL('objects/imported/Truck/Catering Truck.obj', 'objects/imported/Truck/Catering_Truck.mtl');
    //     console.log(mesh)
    //     mesh.position.set(0, 0.1, 0);
    //     mesh.scale = new THREE.Vector3( 1, 1, 1 );
    //     mesh.uuid = uuid;
    //     this.mesh = mesh;
    //     return this.mesh;
    // }

    async loadObject(uuid) {
        let mesh = await loadGLTF('objects/imported/Truck/delivery.glb');
        mesh.scale.set(3,3,3)
        mesh.uuid = uuid;
        this.mesh = mesh;
        return this.mesh;
    }
}