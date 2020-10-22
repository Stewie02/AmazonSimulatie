import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import MovableObject from './super/MovableObject.js';
import cardBoardBox from './CardBoardBox.js'

const tray = (x, y, heightPos) => {
    const geometry = new THREE.PlaneGeometry(x, y, 32);
    let material = new THREE.MeshBasicMaterial({ 
        color: 0x666666,
        side: THREE.DoubleSide, 
        fog: false });

    if ( heightPos > 1 ) {
        material = new THREE.MeshStandardMaterial({ 
            color: 0x333333,
            side: THREE.DoubleSide, 
            fog: false });
    }

    let mesh = new THREE.Mesh(geometry, material);
    mesh.rotation.x = -90 * Math.PI / 180;
    mesh.position.y = heightPos;
    mesh.castShadow = false;
    mesh.receiveShadow = false;
    return mesh
}

export default class SimpleRack extends MovableObject{
    constructor(uuid, materials) {
        const width = 0.8;
        const length = 0.8;
        const height = 2;
        let group = new THREE.Group();

        const geometry = new THREE.BoxGeometry(width, height, length);
        let mesh = new THREE.Mesh(geometry, materials.rack)
        mesh.castShadow = true;
        mesh.position.y = 0.15;
        group.add(mesh)

        const trayHeight = [-0.75, -0.3, 0.15, 0.65, 1.1];
        trayHeight.forEach(height => {
            group.add(tray(width, length, height));
            Math.random() <= 0.5 ?  group.add(cardBoardBox( 0.5, 0.3, 0.5, height, materials.cardBoardBox)) : null ;
        })

        group.uuid = uuid;

        super(group);
    }
}