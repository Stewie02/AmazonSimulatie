import Object from './Object.js';

export default class MovableObject extends Object{
    constructor(mesh) {
        super(mesh)
    }

    /**
     * Overrides the position.
     * @param {Float} x 
     * @param {Float} y 
     * @param {Float} z 
     */
    moveTo = (x, y, z) => {
        this.mesh.position.set(x, y, z);
    }

    /**
     * Adds to the position.
     * @param {Float} x 
     * @param {Float} y 
     * @param {Float} z 
     */
    incrementalMove = (x, y, z) => {
        this.mesh.position.x += x;
        this.mesh.position.y += y;
        this.mesh.position.z += z;
    }

    /**
     * Rotate the object in degrees.
     * @param {Float} x 
     * @param {Float} y 
     * @param {Float} z 
     */
    rotate = (x, y, z) => {
        this.mesh.rotation.set(
            x * Math.PI / 180, 
            y * Math.PI / 180, 
            z * Math.PI / 180
        );
    }

    /**
     * Rotate the object incremental in degrees.
     * So it adds to, instead of overriding the rotation.
     * @param {Float} x 
     * @param {Float} y 
     * @param {Float} z 
     */
    incrementalRotation = (x, y, z) => {
        this.mesh.rotation.x += x * Math.PI / 180;
        this.mesh.rotation.y += y * Math.PI / 180;
        this.mesh.rotation.z += z * Math.PI / 180;
    }


}