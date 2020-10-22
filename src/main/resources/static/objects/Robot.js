import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import MovableObject from './super/MovableObject.js';

export default class Robot extends MovableObject{
    constructor(uuid, material) {
        const geometry = new THREE.BoxGeometry(0.9, 0.3, 0.9);

        let mesh = new THREE.Mesh(geometry, material);
        mesh.position.y = 0.15;

        let group = new THREE.Group();
        group.add(mesh);
        group.uuid = uuid

        super(group);
    }

    pickUp(object) {
        this.mesh.add(object)
    }

    dropOff(scene, dropOffPointUuid) {
        const dropable = this.mesh.children[1];
        const dropOffPoint = scene.getObjectByProperty( 'uuid', dropOffPointUuid);
        if ( dropOffPoint != undefined ) {
            dropable.position.x = dropOffPoint.position.x;
            dropable.position.z = dropOffPoint.position.z;
            scene.add(dropable);
        }
        this.mesh.remove(dropable);
    }
}