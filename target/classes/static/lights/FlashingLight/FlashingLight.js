import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';

/**
 * Builds orange flashing light fixture.
 * @param {Float} radius 
 */
const buildFixture = (radius) => {
    const length = radius * 2;
    const radialSegments = 16;  
    const geometry = new THREE.CylinderBufferGeometry(radius, radius, length, radialSegments);
    const material =new THREE.MeshPhongMaterial({
        color: 0xfc9803,
        side: THREE.FrontSide,
        shininess: 100,
        fog: false //Only the truck is affected by fog.
    });
    let mesh = new THREE.Mesh(geometry, material);
    mesh.rotation.x = -90 * Math.PI / 180;
    return mesh
}

export default class FlashingLight {
    /**
     * Builds the flashing light group.
     * @param {Float} x 
     * @param {Float} y 
     * @param {Float} z 
     */
    constructor(x, y, z) {
        let lightGroup = new THREE.Group();

        const radius =  .15;
        let fixture = buildFixture(radius)
        fixture.position.set(x, y, z + radius)
        lightGroup.add( fixture )
    
        let spotLight = new THREE.SpotLight( 0xfc9803, 3 );
        spotLight.position.set(x, y, z + radius);
    
        spotLight.distance = 10;
        spotLight.angle = 0.70;
        spotLight.castShadow = true;
        spotLight.shadow.mapSize.width = 1024;
        spotLight.shadow.mapSize.height = 1024;
        spotLight.shadow.camera.near = 0.5;
        spotLight.shadow.camera.far = 500;
        spotLight.shadow.focus = 1;
    
        //Object to aim the light and make the animation possible
        let targetObject = new THREE.Object3D();
        targetObject.name = "flashingLightPointerObject";
        lightGroup.add( targetObject );
        spotLight.target = targetObject;
        lightGroup.add( spotLight );
    
        this.mesh = lightGroup
    }
    
}