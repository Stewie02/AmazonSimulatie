import {OrbitControls} from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/examples/jsm/controls/OrbitControls.js';
import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import Skybox from './Skybox.js';

export default class World {
    worldObjects = {};

    constructor() {
        this.scene = new THREE.Scene();
        this.renderer = new THREE.WebGLRenderer({ antialias: true });
        this.camera = new THREE.PerspectiveCamera(70, window.innerWidth / window.innerHeight, 1, 1000);
        this.cameraControls = new OrbitControls(this.camera, this.renderer.domElement);
        this.light = new THREE.AmbientLight(0xffffff, 0.5);
        this.directionalLight1 = new THREE.DirectionalLight(0xffffff, 1)
        this.directionalLight2 = new THREE.DirectionalLight(0xffffff, 1)
        this.skybox = new Skybox(600)
        this.worldObjects = {};

        this.truckDockingAudio = document.getElementById("truckDocking");

        this.then = 0;
        this.flashingLight = {
            t: 0,
            speed: 10
        }
    }

    init(length, width) {
        this.camera.position.set(17, 7, width / 2);

        this.cameraControls.target = new THREE.Vector3(length / 2, 1.7, width / 2)
        this.cameraControls.dampingFactor = 0.1;
        this.cameraControls.enableDamping = true;
        this.cameraControls.autoRotate = true;
        this.cameraControls.autoRotateSpeed = 0.8;
        this.cameraControls.maxDistance = 80;
        this.cameraControls.enablePan = false;

        this.renderer.shadowMap.enabled = true;
        this.renderer.shadowMap.type = THREE.PCFSoftShadowMap;
        this.renderer.setPixelRatio(window.devicePixelRatio);
        this.renderer.setSize(window.innerWidth, window.innerHeight + 5);
        document.body.appendChild(this.renderer.domElement);

        window.addEventListener('resize', () => this.onWindowResize(), false);

        this.renderer.setClearColor( 0xffffff );
        this.directionalLight1.position.set(1,1,-1);
        this.directionalLight2.position.set(-1,1,1);
        this.scene.add(this.directionalLight1)
        this.scene.add(this.directionalLight2)
        this.scene.add(this.light);

        this.skybox.moveTo(length/2, 0, width/2)
        this.scene.add(this.skybox.getMesh())

        this.scene.fog = new THREE.Fog(0xffffff, 65, 80)

        this.animate();
    }

    onWindowResize() {
        this.camera.aspect = window.innerWidth / window.innerHeight;
        this.camera.updateProjectionMatrix();
        this.renderer.setSize(window.innerWidth, window.innerHeight);
    }

    animate = (now) => {
        now *= 0.001;
        const delta = now - this.then;
        this.then = now;     

        let truck = this.scene.getObjectByName("truck");
        if ( truck != undefined && truck.position.z > -10 ) {
            
            truck.position.z < -9 ? this.truckDockingAudio.play() : null;

            let flashingLight = this.scene.getObjectByName("flashingLightPointerObject");
            if ( flashingLight !== undefined ) {
                this.flashingLight.t += this.flashingLight.speed * delta
                const y = 20*Math.cos(this.flashingLight.t)
                const x = 20*Math.sin(this.flashingLight.t)
                flashingLight.position.y = y;
                flashingLight.position.x = x;
            }

        }

        this.cameraControls.update();
        this.renderer.render(this.scene, this.camera);
        requestAnimationFrame(this.animate);
    }

    addObject = (object) => {
        this.worldObjects[object.mesh.uuid] = object;
        this.scene.add(object.getMesh());
    }

    addObjects = (objects) => {
        objects.forEach(object => {
            this.addObject(object);
        });
    }
}