import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';

export default class SpotLight {
    constructor(x, y, z) {
        let lightGroup = new THREE.Group();

        var spotLight = new THREE.SpotLight( 0xefebd8, 3 );
        spotLight.position.set(x, y, z);
        
        spotLight.distance = 13;
        spotLight.angle = 0.65;
        spotLight.castShadow = true;
        spotLight.shadow.mapSize.width = 1024;
        spotLight.shadow.mapSize.height = 1024;
        spotLight.shadow.camera.near = 0.5;
        spotLight.shadow.camera.far = 500;
        spotLight.shadow.focus = 1;
        
        var targetObject = new THREE.Object3D();
        targetObject.position.x = x;
        targetObject.position.z = z;
        
        lightGroup.add( targetObject );
        spotLight.target = targetObject;
        lightGroup.add( spotLight );
        this.lightGroup = lightGroup;
    }
}
