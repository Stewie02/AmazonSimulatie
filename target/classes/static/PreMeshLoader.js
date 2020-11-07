import * as THREE from 'https://threejsfundamentals.org/threejs/resources/threejs/r119/build/three.module.js';

export default class PreMeshLoader{
    /**
     * Loads mesh one time when this constructor is called.
     */
    constructor(){
        //SimpleRack
        const sideTextureRack = new THREE.TextureLoader().load("textures/rack_side.png");
        const rack = [
            new THREE.MeshBasicMaterial({ transparent: true, map: sideTextureRack, side: THREE.DoubleSide, fog: false }), //LEFT
            new THREE.MeshBasicMaterial({ transparent: true, map: sideTextureRack, side: THREE.DoubleSide, fog: false }), //RIGHT
            new THREE.MeshBasicMaterial({ transparent: true, opacity: 0 }), //TOP
            new THREE.MeshBasicMaterial({ transparent: true, opacity: 0 }), //BOTTOM
            new THREE.MeshBasicMaterial({ transparent: true, map: sideTextureRack, side: THREE.DoubleSide, fog: false }), //FRONT
            new THREE.MeshBasicMaterial({ transparent: true, map: sideTextureRack, side: THREE.DoubleSide, fog: false }), //BACK
        ];

        //CardBoardBox
        const sideTextureBox = new THREE.TextureLoader().load("textures/box1_side.png");
        const extraSideTexture = new THREE.TextureLoader().load("textures/box1_side_extra.png");
        const topTextureBox = new THREE.TextureLoader().load("textures/box1_top.png")
        const cardBoardBoxHigh = [
            new THREE.MeshStandardMaterial({ map: sideTextureBox, fog: false }), //LEFT
            new THREE.MeshStandardMaterial({ map: sideTextureBox, fog: false }), //RIGHT
            new THREE.MeshStandardMaterial({ map: topTextureBox, fog: false }), //TOP
            new THREE.MeshStandardMaterial({ fog: false }), //BOTTOM
            new THREE.MeshStandardMaterial({ map: extraSideTexture, fog: false }), //FRONT
            new THREE.MeshStandardMaterial({ map: extraSideTexture, fog: false }), //BACK
        ];
        const cardBoardBoxLow = [
            new THREE.MeshBasicMaterial({ map: sideTextureBox, fog: false }), //LEFT
            new THREE.MeshBasicMaterial({ map: sideTextureBox, fog: false }), //RIGHT
            new THREE.MeshBasicMaterial({ map: topTextureBox, fog: false }), //TOP
            new THREE.MeshBasicMaterial({ fog: false }), //BOTTOM
            new THREE.MeshBasicMaterial({ map: extraSideTexture, fog: false }), //FRONT
            new THREE.MeshBasicMaterial({ map: extraSideTexture, fog: false }), //BACK
        ]

        //Add to one object to be used in SimpleRack class
        this.simpleRack = {
            rack: rack,
            cardBoardBox: { low: cardBoardBoxLow, high: cardBoardBoxHigh }
        }

        //Robot
        this.robot = {
            side: new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/robot_side.png"), side: THREE.FrontSide, fog: false }), //LEFT
            front: new THREE.MeshBasicMaterial({ map: new THREE.TextureLoader().load("textures/robot_front.png"), side: THREE.FrontSide, fog: false }) //FRONT
        }
    }
}