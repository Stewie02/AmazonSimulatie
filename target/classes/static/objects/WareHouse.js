import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import Object from './super/Object.js';
import SpotLight from '../lights/SpotLight/SpotLight.js';

export default class WareHouse extends Object{
    constructor(rackPositions) {
        const wallHeight = 10;

        let group = new THREE.Group();
        let length = 0, width = 0;
        rackPositions.forEach(pos => {
            const geometry = new THREE.PlaneGeometry(1, 1, 32);
            const material = new THREE.MeshPhongMaterial({
                color: 0xa52019,
                side: THREE.FrontSide,
                emissive: 0x0,
                specular: 0x1a1a1a,
                shininess: 100
            });
            let mesh = new THREE.Mesh(geometry, material);
            mesh.receiveShadow = true;
            mesh.position.set(pos.x, pos.y, pos.z);
            mesh.rotation.x = -90 * Math.PI / 180;;
            group.add(mesh);
            
            parseFloat(pos.x) > length ? length = parseFloat(pos.x) : null;
            parseFloat(pos.z) > width ? width = parseFloat(pos.z) : null;
        });

        const stepSize = 1;
        const stepL = length / stepSize;
        const stepW = width / stepSize;
        for (let L = 0; L <= length ; L += stepL) {
            for (let W = 0; W <= width ; W += stepW) {
                let spotLight = new SpotLight(L, wallHeight, W);
                group.add( spotLight.lightGroup );
            }
        }

        const margin = 5;
        length += margin;
        width += margin;

        const wallsGeometry = new THREE.BoxGeometry(length, wallHeight, width);
        const wallsMaterial = new THREE.MeshPhongMaterial({
            color: 0x666666, 
            emissive: 0x0,
            specular: 0x333333,
            shininess: 10,
            side: THREE.BackSide,
            map: new THREE.TextureLoader().load("textures/concrete-map-2.jpg")
        });
        let wallsMesh = new THREE.Mesh(wallsGeometry, wallsMaterial);
        wallsMesh.receiveShadow = true;
        wallsMesh.position.x = (length - margin) / 2;
        wallsMesh.position.y = wallHeight / 2;
        wallsMesh.position.z = (width - margin) / 2;
        group.add(wallsMesh);

        const doorheight = 5
        const doorWidth = 5;
        const geometry = new THREE.PlaneGeometry(doorWidth, doorheight, 32);
            const material = new THREE.MeshPhongMaterial({
                color: 0x0,
                side: THREE.FrontSide,
                emissive: 0x0,
                specular: 0x0,
                shininess: 0
            });
        let mesh = new THREE.Mesh(geometry, material);
        mesh.position.set( ( doorWidth / 2 ) -0.5, doorheight / 2, -(margin / 2) + 0.01);
        group.add(mesh);

        super(group);
    }
}