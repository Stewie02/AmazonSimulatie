import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
export default function cardBoardBox (width, height, length, heightPos, materials) {
    const geometry = new THREE.BoxGeometry(width, height, length);
    
    let material = materials.low
    if ( heightPos > 1 ) {
        material = materials.high
    }

    let mesh = new THREE.Mesh(geometry, material);
    mesh.position.y = heightPos + (height / 2) + 0.01;
    mesh.castShadow = false;
    mesh.receiveShadow = false;
    return mesh
}