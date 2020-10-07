import {OrbitControls} from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/examples/jsm/controls/OrbitControls.js';
import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';

export default class World {
    cameraPos = {
        x: 15,
        y: 5,
        z: 15
    }

    animation = {
        frames: 0,
        object: undefined,
        position: {
            x: undefined,
            y: undefined,
            z: undefined
        },
        rotation: {
            x: undefined,
            y: undefined,
            z: undefined
        }
    }

    constructor() {
        this.scene = new THREE.Scene();
        this.renderer = new THREE.WebGLRenderer({ antialias: true });
        this.camera = new THREE.PerspectiveCamera(70, window.innerWidth / window.innerHeight, 1, 1000);
        this.cameraControls = new OrbitControls(this.camera, this.renderer.domElement);
        this.light = new THREE.AmbientLight(0x404040);
        this.worldObjects = {};
    }

    init() {
        this.camera.position.set(this.cameraPos.x, this.cameraPos.y, this.cameraPos.z);
        this.cameraControls.update();

        this.renderer.setPixelRatio(window.devicePixelRatio);
        this.renderer.setSize(window.innerWidth, window.innerHeight + 5);
        document.body.appendChild(this.renderer.domElement);

        window.addEventListener('resize', this.onWindowResize, false);

        this.light.intensity = 4;
        this.renderer.setClearColor( 0xffffff )
        this.scene.add(this.light);

        this.animate();
    }

    onWindowResize() {
        this.camera.aspect = window.innerWidth / window.innerHeight;
        this.camera.updateProjectionMatrix();
        this.renderer.setSize(window.innerWidth, window.innerHeight);
    }

    animate = () => {
        requestAnimationFrame(this.animate);

        this.animation.frames > 0 ? this.runAnimation() : null;

        this.cameraControls.update();
        this.renderer.render(this.scene, this.camera);
    }

    runAnimation() {
        const pos = this.animation.position;
        const rotate = this.animation.rotation;
        this.animation.object.incrementalMove(pos.x, pos.y, pos.z)
        this.animation.object.incrementalRotation(rotate.x, rotate.y, rotate.z)
        this.animation.frames -= 1;
    }

    setAnimation = (object, frames, moveTo = {x:0, y:0, z:0}, rotate = {x:0, y:0, z:0}) => {
        console.log(frames);
        this.animation.frames = frames;
        this.animation.object = object;
        this.animation.position = {
            x: moveTo.x / frames,
            y: moveTo.y / frames,
            z: moveTo.z / frames,
        },
        this.animation.rotation = {
            x: rotate.x / frames,
            y: rotate.y / frames,
            z: rotate.z / frames,
        }
    }

    addObject = (object) => {
        this.scene.add(object);
    }

    addObjects = (objects) => {
        objects.forEach(object => {
            this.addObject(object);
        });
    }
}