import Object from './Object.js';

export default class MovableObject extends Object{
    constructor(mesh) {
        super(mesh)
    }

    moveTo = (x, y, z) => {
        this.mesh.position.set(x, y, z);
    }

    incrementalMove = (x, y, z) => {
        this.mesh.position.x += x;
        this.mesh.position.y += y;
        this.mesh.position.z += z;
    }

    rotate = (x, y, z) => {
        this.mesh.rotation.set(
            x * Math.PI / 180, 
            y * Math.PI / 180, 
            z * Math.PI / 180
        );
    }

    incrementalRotation = (x, y, z) => {
        this.mesh.rotation.x += x * Math.PI / 180;
        this.mesh.rotation.y += y * Math.PI / 180;
        this.mesh.rotation.z += z * Math.PI / 180;
    }


}