import {OBJLoader2} from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/examples/jsm/loaders/OBJLoader2.js';
import {GLTFLoader} from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/examples/jsm/loaders/GLTFLoader.js';
import {MTLLoader} from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/examples/jsm/loaders/MTLLoader.js';
import {MtlObjBridge} from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/examples/jsm/loaders/obj2/bridge/MtlObjBridge.js';
import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';

function loadOBJMTL( pathObj, pathMtl ) {
    console.log(pathObj)
    return new Promise(function(resolve){
        const mtlLoader = new MTLLoader();
        mtlLoader.load(pathMtl, (mtlParseResult) => {
            const objLoader = new OBJLoader2();
            const materials =  MtlObjBridge.addMaterialsFromMtlLoader(mtlParseResult);
            objLoader.addMaterials(materials);
            objLoader.load(pathObj, resolve)  
        })
    }).then(obj => {
        return obj
    });
}

function loadOBJ( path ) {
    return new Promise(function(resolve){
        const objLoader = new OBJLoader2();
        objLoader.load(path, resolve);
    }).then(obj => {
        return obj
    })
}

function loadGLTF( path ) {
    return new Promise(function(resolve){
        const gltfLoader = new GLTFLoader();
        gltfLoader.load(path, resolve);
    }).then(obj => {
        return obj.scene
    })
}

export default null;
export { loadOBJMTL, loadOBJ, loadGLTF}