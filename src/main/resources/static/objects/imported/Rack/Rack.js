import {loadGLTF} from '../../../ObjectLoader.js';
import MovableObject from '../../super/MovableObject.js';

export default class Rack extends MovableObject {
    constructor(){
        super()
    }
    
    async loadObject() {
        let mesh = await loadGLTF('objects/imported/Rack/server_rack/scene.gltf');
        mesh.position.set(0, 1.3, 0);
        this.mesh = mesh;
        return this.mesh;
    }
}