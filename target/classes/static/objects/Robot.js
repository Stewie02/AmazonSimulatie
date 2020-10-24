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

        let callName = "Robert"
        switch (robotNumber) {
            case 1:
                callName = "John"
                break;
            case 2:
                callName = "Todd"
                break;
            case 3:
                callName = "Suzanne"
                break;   
            case 4:
                callName = "Nigel"
                break;   
            case 5:
                callName = "Tyler"
                break;   
            case 6:
                callName = "Alice"
                break;  
            case 7:
                callName = "Arnold"
                break;   
            case 8:
                callName = "Frank"
                break;   
            case 9:
                callName = "Tony"
                break;   
            case 10:
                callName = "Rick"
                break;    
            default:
                break;
        }

        group.userData.name = callName;
        group.userData.number = robotNumber;
        group.userData.movedItems = 0;
        group.userData.metersRun = 0;
        group.userData.lastMetersRun = 0;

        
       
        super(group);
    }

    pickUp(object) {
        this.mesh.userData.movedItems++;
        object.position.set(0, object.position.y + .3, 0);
        this.mesh.add(object);
    }

    dropOff(world, dropOffPointUuid) {
        const dropable = this.mesh.children[1];
        if ( dropOffPointUuid != undefined ) {
            const dropOffPoint = world.scene.getObjectByProperty( 'uuid', dropOffPointUuid);
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