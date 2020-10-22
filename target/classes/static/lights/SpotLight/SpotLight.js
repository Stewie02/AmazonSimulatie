import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';

const lightBulb = (x, y, z) => {
    const radius = 0.1;
    const widthSegments = 10;
    const heightSegments = 10;
    const geometry = new THREE.SphereBufferGeometry(radius, widthSegments, heightSegments);
    const material =  new THREE.MeshPhongMaterial( { 
        color: 0x333333,
        emissive: 0xffffff,
        specular: 0xffffff,
        fog: false
    } );
    let mesh = new THREE.Mesh(geometry, material);
    mesh.position.set(x, y, z);
    return mesh;
}

const fixture = (x, y, z, radius, height) => {
    const radialSegments = 25;  
    const heightSegments = 1;  
    const openEnded = true;  

    const geometry = new THREE.ConeBufferGeometry(
        radius, height,
        radialSegments, heightSegments,
        openEnded
        );
    const material =  new THREE.MeshPhongMaterial( { 
        color: 0x898984,
        emissive: 0x0,
        specular: 0xffffff,
        fog: false,
        side: THREE.BackSide
    } );
    let mesh = new THREE.Mesh(geometry, material);
    mesh.position.set(x, y, z);
    return mesh;
}

export default class SpotLight {
    constructor(x, y, z) {
        let lightGroup = new THREE.Group();
        const radius =  0.5;  
        const height =  0.5;  
        lightGroup.add( fixture(x, y - (height / 2), z, radius, height) );
        lightGroup.add( lightBulb(x, y - (height / 2), z) );

        let spotLight = new THREE.SpotLight( 0xefebd8, 3 );
        spotLight.position.set(x, y, z);
        
        spotLight.distance = 13;
        spotLight.angle = 0.70;
        spotLight.castShadow = true;
        spotLight.shadow.mapSize.width = 1024;
        spotLight.shadow.mapSize.height = 1024;
        spotLight.shadow.camera.near = 0.5;
        spotLight.shadow.camera.far = 500;
        spotLight.shadow.focus = 1;
        
        let targetObject = new THREE.Object3D();
        targetObject.position.x = x;
        targetObject.position.z = z;
        
        lightGroup.add( targetObject );
        spotLight.target = targetObject;
        lightGroup.add( spotLight );
        this.lightGroup = lightGroup;
    }
}
