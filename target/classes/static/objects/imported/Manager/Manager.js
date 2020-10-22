import { loadGLTF } from '../../../../ObjectLoader.js';
import MovableObject from '../../super/MovableObject.js';

export default class Truck extends MovableObject {
    constructor(){
        super()
    }

    async loadObject(uuid) {
        let mesh = await loadGLTF('objects/imported/Manager/uploads_files_2578203_M009.glb');
        mesh.scale.set(15, 15, 15);
        mesh.uuid = uuid;
        this.mesh = mesh;
        return this.mesh;
    }
}