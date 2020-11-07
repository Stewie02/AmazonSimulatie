import {OrbitControls} from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/examples/jsm/controls/OrbitControls.js';
import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';
import Skybox from './Skybox.js';

export default class World {
    worldObjects = {};

    /**
     * Builds the world.
     */
    constructor() {
        this.scene = new THREE.Scene();
        this.renderer = new THREE.WebGLRenderer({ antialias: true });
        this.camera = new THREE.PerspectiveCamera(70, window.innerWidth / window.innerHeight, 1, 1000);
        this.cameraControls = new OrbitControls(this.camera, this.renderer.domElement);
        this.raycaster = new THREE.Raycaster();
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

        this.mouse = new THREE.Vector2( 1, 1 );
    }

    /**
     * Initializes the world and sets some settings.
     * @param {Float} length 
     * @param {Float} width 
     */
    init(length, width) {
        this.length = length;
        this.width = width;

        this.camera.position.set(-5 , 7, width + 5);

        this.cameraControls.target = new THREE.Vector3(length / 2, 1.7, width / 2)
        this.cameraControls.dampingFactor = 0.1;
        this.cameraControls.enableDamping = true;
        this.cameraControls.autoRotateSpeed = 0.8;
        this.cameraControls.maxDistance = 350;

        this.renderer.shadowMap.enabled = true;
        this.renderer.shadowMap.type = THREE.PCFSoftShadowMap;
        this.renderer.setPixelRatio(window.devicePixelRatio);
        this.renderer.setSize(window.innerWidth, window.innerHeight + 5);
        document.body.appendChild(this.renderer.domElement);

        window.addEventListener('resize', () => this.onWindowResize(), false); //For resize browser
        window.addEventListener('click', (event) => this.onMouseClick(event), false ); //For poking robots
        this.raycaster.layers.set( 1 ) //For poking robots

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

    /**
     * Resizes the window.
     */
    onWindowResize() {
        this.camera.aspect = window.innerWidth / window.innerHeight;
        this.camera.updateProjectionMatrix();
        this.renderer.setSize(window.innerWidth, window.innerHeight);
    }

    /**
     * Handles mouse clicks in 3d space with raycaster.
     * @param {Object} event 
     */
    onMouseClick( event ) {
        this.mouse.x = ( event.clientX / window.innerWidth ) * 2 - 1;
        this.mouse.y = - ( event.clientY / window.innerHeight ) * 2 + 1;

        //Detect intersection using a raycaster
        this.raycaster.setFromCamera( this.mouse, this.camera );
        let intersects = this.raycaster.intersectObjects( this.scene.children, true );
	    for ( let i = 0; i < intersects.length; i++ ) {
            const robot = intersects[i].object.parent.userData; //Gets (robot) group of intersected

            //Updates hello-robot message
            document.getElementById('helloRobot').innerHTML = "Hi, my name is <b>" + 
                robot.name + "</b> but you can call me Robot number <b>" + 
                robot.number + "</b>. So far I have travelled <b>" + 
                Math.round(robot.metersRun * 10)/10 + "</b> meters and moved <b>" + 
                robot.movedItems + "</b> racks around this warehouse.";
        }
    }

    /**
     * Frontend animation happens here.
     * @param {Int} now 
     */
    animate = (now) => {
        now *= 0.001;
        const delta = now - this.then;
        this.then = now;     

        let truck = this.scene.getObjectByName("truck");
        if ( truck != undefined && truck.position.z > -10 ) {
            truck.position.z < -9 ? this.truckDockingAudio.play() : null; //Honks
            let flashingLight = this.scene.getObjectByName("flashingLightPointerObject");
            flashingLight !== undefined ? this.runFlashingLight(delta, flashingLight) : null; //Turns on flashing light
        }

        requestAnimationFrame(this.animate);
        this.cameraControls.update();
        this.renderer.render(this.scene, this.camera);
    }

    /**
     * Makes the flashing light target object spin independent of frame rate
     * @param {Float} delta 
     * @param {mesh} flashingLight 
     */
    runFlashingLight = (delta, flashingLight) => {
        this.flashingLight.t += this.flashingLight.speed * delta;
        const y = 20*Math.cos(this.flashingLight.t); //Varies from -20 to 20
        const x = 20*Math.sin(this.flashingLight.t); //Varies from -20 to 20
        flashingLight.position.y = y;
        flashingLight.position.x = x;
    }

    /**
     * Add object to scene and to worldObjects object to keep track of the UUID's
     * @param {mesh} object 
     */
    addObject = (object) => {
        this.worldObjects[object.mesh.uuid] = object;
        this.scene.add(object.getMesh());
    }

    /**
     * Adds multiple objects to scene and worldObjects.
     * @param {Array} objects 
     */
    addObjects = (objects) => {
        objects.forEach(object => {
            this.addObject(object);
        });
    }
}