import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';

/**
 * Builds the cardboard boxes.
 * @param {Float} width 
 * @param {Float} height 
 * @param {Float} length 
 * @param {Float} heightPos 
 * @param {Array} materials pre loaded mesh
 */
export default function cardBoardBox (width, height, length, heightPos, materials) {
    const geometry = new THREE.BoxGeometry(width, height, length);
    
    //only the top boxes will be affected by light. This helps the performance.
    let material = materials.low
    if ( heightPos > 1 ) {
        material = materials.high
    }

    let mesh = new THREE.Mesh(geometry, material);
    mesh.position.y = heightPos + (height / 2) + 0.01; //Sets height to set the box on a tray in a rack.
    return mesh
}