import { loadGLTF } from '../../../ObjectLoader.js';
import MovableObject from '../../super/MovableObject.js';

export default class Truck extends MovableObject {
    constructor(){
        super()
    }

    async loadObject(uuid) {
        let mesh = await loadGLTF('objects/imported/Truck/delivery.glb');
        mesh.scale.set(3,3,3)
        mesh.uuid = uuid;
        mesh.name = "truck"
        this.mesh = mesh;
        return this.mesh;
    }
}