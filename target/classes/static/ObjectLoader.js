import {OBJLoader2} from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/examples/jsm/loaders/OBJLoader2.js';
import {GLTFLoader} from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/examples/jsm/loaders/GLTFLoader.js';
import {MTLLoader} from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/examples/jsm/loaders/MTLLoader.js';
import {MtlObjBridge} from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/examples/jsm/loaders/obj2/bridge/MtlObjBridge.js';

/**
 * For loading .obj and .mtl 3d objects.
 * @param {String} pathObj 
 * @param {String} pathMtl 
 */
function loadOBJMTL( pathObj, pathMtl ) {
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

/**
 * For loading .obj 3d objects.
 * @param {String} path 
 */
function loadOBJ( path ) {
    return new Promise(function(resolve){
        const objLoader = new OBJLoader2();
        objLoader.load(path, resolve);
    }).then(obj => {
        return obj
    })
}

/**
 * For loading .gltf and .glb 3d objects.
 * @param {String} path 
 */
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