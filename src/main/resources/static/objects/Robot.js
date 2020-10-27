import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import MovableObject from './super/MovableObject.js';

export default class Robot extends MovableObject{
    constructor(uuid, preMesh, robotNumber) {
        const geometry = new THREE.BoxGeometry(0.9, 0.3, 0.9);
        const material = [
            preMesh.side, //LEFT
            preMesh.side, //RIGHT
            new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load('textures/robot-number/' + robotNumber + '.png'), color: 0xffffff, side: THREE.FrontSide, fog: false }), //TOP
            new THREE.MeshBasicMaterial({ color: 0x999999, side: THREE.FrontSide, fog: false }), //BOTTOM
            preMesh.front, //FRONT
            preMesh.front //BACK
        ];
        let mesh = new THREE.Mesh(geometry, material);
        mesh.position.y = 0.15;
        mesh.layers.enable( 1 );

        let group = new THREE.Group();
        group.add(mesh);
        group.name = "robot";
        group.uuid = uuid;

        const names = ['John', 'Todd', 'Suzanne', 'Nigel', 'Tyler', 'Alice', 'Arnold', 'Frank', 'Tony', 'Rick'];
        let callName = names[robotNumber - 1];

        group.userData.name = callName;
        group.userData.number = robotNumber;
        group.userData.movedItems = 0;
        group.userData.metersRun = 0;
        group.userData.lastMetersRun = 0;
        super(group);
    }

    pickUp(object) {
        if ( this.mesh.children[1] === undefined ) {
            this.mesh.userData.movedItems++;
            object.position.set(0, object.position.y + .3, 0);
            this.mesh.add(object);
        } else {
            console.log("[Robot.js pickUp] Robots can only pick up one rack at a time")
        }
    }

    dropOff(world, dropOffPointUuid) {
        const dropable = this.mesh.children[1];
        const dropOffPoint = world.scene.getObjectByProperty( 'uuid', dropOffPointUuid);
        if ( dropOffPointUuid != undefined && dropOffPoint.name != "truck") {
            dropable.position.x = dropOffPoint.position.x;
            dropable.position.y -= 0.3;
            dropable.position.z = dropOffPoint.position.z;
            world.scene.add(dropable);
        } else {
            delete world.worldObjects[dropable.uuid];
        }
        this.mesh.remove(dropable);
    }
}