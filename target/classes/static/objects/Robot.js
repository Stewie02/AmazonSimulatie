import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import MovableObject from './super/MovableObject.js';

export default class Robot extends MovableObject{
    /**
     * Builds a robot
     * @param {String} uuid 
     * @param {Array} preMesh Pre loaded mesh
     * @param {Int} robotNumber 
     */
    constructor(uuid, preMesh, robotNumber) {
        const geometry = new THREE.BoxGeometry(0.9, 0.3, 0.9);
        const material = [
            preMesh.side, //LEFT
            preMesh.side, //RIGHT
            new THREE.MeshBasicMaterial({
                map: new THREE.TextureLoader().load('textures/robot-number/' + robotNumber + '.png'), //Adds corresponding top sticker
                color: 0xffffff, 
                side: THREE.FrontSide, 
                fog: false  //Only the truck is affected by fog.
            }), //TOP
            new THREE.MeshBasicMaterial({ color: 0x999999, side: THREE.FrontSide, fog: false //Only the truck is affected by fog. 
            }), //BOTTOM
            preMesh.front, //FRONT
            preMesh.front //BACK
        ];

        let mesh = new THREE.Mesh(geometry, material);
        mesh.position.y = 0.15;
        mesh.layers.enable( 1 ); //Enables the users to click on the robot.

        //Add robot to group so that racks can be added later to this group as well. 
        let group = new THREE.Group();
        group.add(mesh);
        group.name = "robot";
        group.uuid = uuid;

        //Name the robot according to its number.
        const names = ['John', 'Todd', 'Suzanne', 'Nigel', 'Tyler', 'Alice', 'Arnold', 'Frank', 'Tony', 'Rick'];
        let callName = names[robotNumber - 1];

        //Sets userData
        group.userData.name = callName;
        group.userData.number = robotNumber;
        group.userData.movedItems = 0;
        group.userData.metersRun = 0;
        group.userData.lastMetersRun = 0;

        //Passes group mesh to super class
        super(group);
    }

    /**
     * Add rack mesh to robot group
     * @param {mesh} object 
     */
    pickUp(object) {
        if ( this.mesh.children[1] === undefined ) {
            this.mesh.userData.movedItems++;
            object.position.set(0, object.position.y + .3, 0);
            this.mesh.add(object);
        } else {
            console.log("[Robot.js pickUp] Robots can only pick up one rack at a time")
        }
    }

    /**
     * If the robot is carrying a rack (droppable), then get the drop off point (rack spot).
     * And if there is a drop off point and its not the truck, Put is on that spot.
     * Else delete it.
     * @param {Object} world 
     * @param {String} dropOffPointUuid 
     */
    dropOff(world, dropOffPointUuid) {
        const droppable = this.mesh.children[1]; //Gets rack that the robot is carrying.
        if ( droppable != undefined ) {
            const dropOffPoint = world.scene.getObjectByProperty( 'uuid', dropOffPointUuid); //Gets the rack spot.
            if ( dropOffPointUuid != undefined && dropOffPoint.name != "truck") {
                //Set new orientation of rack, same as rack spot.
                droppable.position.x = dropOffPoint.position.x;
                droppable.position.y -= 0.3;
                droppable.position.z = dropOffPoint.position.z;
                world.scene.add(droppable); //Adds the rack to the scene.
            } else {
                delete world.worldObjects[droppable.uuid];
            }
            this.mesh.remove(droppable); //Removes the rack from the robot group.
        }
    }
}