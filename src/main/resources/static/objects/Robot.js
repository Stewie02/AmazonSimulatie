import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import MovableObject from './super/MovableObject.js';

export default class Robot extends MovableObject{
    constructor(uuid) {
        const geometry = new THREE.BoxGeometry(0.9, 0.3, 0.9);
        const cubeMaterials = [
            new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/robot_side.png"), side: THREE.DoubleSide }), //LEFT
            new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/robot_side.png"), side: THREE.DoubleSide }), //RIGHT
            new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/robot_top.png"), side: THREE.DoubleSide }), //TOP
            new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/robot_bottom.png"), side: THREE.DoubleSide }), //BOTTOM
            new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/robot_front.png"), side: THREE.DoubleSide }), //FRONT
            new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/robot_front.png"), side: THREE.DoubleSide }), //BACK
        ];
        const material = new THREE.MeshFaceMaterial(cubeMaterials);
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

    dropOf(object, scene) {
        object.position.set(this.mesh.position);
        scene.add(object);
        this.mesh.remove(object);
    }

}