<template>
  <div id="app" class="app">
    <div class="navBar">
      <ul>
        <li class="options" v-if="execution" @click="stopExecution()">
          <img src="./assets/pause.png" class="image">
          <p> Pause</p>
        </li>
        <li class="options" v-if="!execution" @click="startExecution()">
          <img src="./assets/start.png" class="image">
          <p> Run </p>
        </li>
        <li class="options" @click="restartExecution()">
          <img src="./assets/restart.png" class="image" >
          <p> Restart</p>
        </li>
      </ul>
    </div>
    <div class="screen">
      <div class="board" ref="parent" @click="create">
        <v-stage :config="configKonva">
          <v-layer>
            <v-regular-polygon v-for="shapeConfig in machines" :key="shapeConfig" :config="shapeConfig" ref="machine" @click="rotate,setShapes(shapeConfig)"/>
            <v-circle v-for="shapeConfig in queues" :key="shapeConfig" :config="shapeConfig" ref="queue" @click="setShapes(shapeConfig)"/>
            <v-arrow v-for="shapeConfig in arrows" :key="shapeConfig" :config="shapeConfig" ref="arrow" />
          </v-layer>
        </v-stage>
        <img src="./assets/left-arrow.png" class="close" @click="close">
      </div>
      <div class="sidebar">
        <ul>
          <li class="item machine" @click="this.current='machine',set()">
            <img src="./assets/m.png">
            <p>Machine</p>
          </li>
          <li class="item queue" @click="this.current='queue',set()">
            <img src="./assets/q.png">
            <p>Queue</p>
          </li>
          <li class="item connection" @click="this.current = '',set(),this.connection=true">
            <img src="./assets/connection.png">
            <p>Connect</p>
          </li>
          <li class="item clear" @click="this.current = '',set(), clear()">
            <img src="./assets/clear.png" class="image">
            <p>Clear</p>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { Stage, Layer, Rect, Line, Text, Arrow } from 'vue-konva';
export default {
  name: 'App',
  data() {
    return {
      sideBar: true,
      execution: false,
      configKonva: {
        width: 2000,
        height: 1000
      },
      machines: [],
      onMachines: [],
      queues: [],
      current: '',
      anim: [],
      shape1: null,
      shape2: null,
      connection: false,
      connections: [],
      // lines: [],
      arrows: [],
      // points: [100, 100, 200, 200]
    }
  },
  components: {
    VStage: Stage,
    VLayer: Layer,
    VRect: Rect,
    VLine: Line,
    VText: Text,
    VArrow: Arrow
  },
  mounted() {
    const parentRect = this.$refs.parent;
    if(parentRect){
      this.configKonva.height = parentRect.offsetHeight;
      this.configKonva.width = parentRect.offsetWidth;
    }
    
  },
  methods: {
    startExecution(){
      this.execution = true;
      for(let i = 0; i < this.machines.length; i++){
        if(this.machines[i].fill != "green"){
          let hexagon = this.$refs.machine[i].getNode();
          this.anim[i] = new Konva.Animation(function(frame) {
            hexagon.rotate(1);
          }, hexagon.getLayer());
          this.anim[i].start();
        }
      }
    },
    stopExecution(){
      this.execution = false;
      for(let i = 0; i < this.machines.length; i++){
        if(i < this.anim.length)this.anim[i].stop();
        this.machines[i].rotate = 0;
      }
    },
    restartExecution(){
      this.stopExecution();
    }
    ,
    close(){
      let sideBar = document.querySelector(".sidebar")
      let arrow = document.querySelector(".close")
      if(this.sideBar){
        arrow.style.transform= "rotateY(0deg)"
        sideBar.style.width = "0px";
        sideBar.style.padding = "0px";
        arrow.style.right = "0px";
      }
      else{
        arrow.style.transform= "rotateY(180deg)"
        sideBar.style.width = "200px";
        sideBar.style.padding = "0px";
        arrow.style.right = "200px"
      }
      this.sideBar = !this.sideBar
    },
    set(){
      document.querySelector(".queue").classList.remove("active");
      document.querySelector(".machine").classList.remove("active");
      if(this.current == 'machine'){
        document.querySelector(".machine").classList.add("active");
      }
      if(this.current == 'queue'){
        document.querySelector(".queue").classList.add("active");
      }
    },
    clear(){
      this.machines = [];
      this.queues = [];
      this.arrows = [];
    },
    create(e){
      const parentRect = this.$refs.parent.getBoundingClientRect();
      if(this.current == 'machine'){
        const machine = {
          x: (e.clientX - parentRect.left ),
          y: (e.clientY - parentRect.top ),
          sides:8,
          radius:70,
          fill:"grey",
          stroke:"black",
          draggable: "true",
          strokeWidth: 5,
          rotate: 0,
          name: "M"+(this.machines.length+1),
          type: "machine",
          machineId: this.machines.length+1
        };
        this.machines.push(machine);
      }
      else if(this.current == 'queue'){
        const queue = {
          x: (e.clientX - parentRect.left ),
          y: (e.clientY - parentRect.top ),
          radius:70,
          fill:"grey",
          stroke:"black",
          draggable: "true",
          name: "Q"+(this.queues.length+1),
          type: "queue",
          queueId: this.queues.length+1
        };
        this.queues.push(queue);
      }
      this.current = '';
      this.set();
    },
    connect(){
      if(this.shape1!=null && this.shape2!=null && this.connection){
        let center1X = this.shape1.x ;
        let center1Y = this.shape1.y ;
        let center2X = this.shape2.x;
        let center2Y = this.shape2.y;
        const arrow = {
          points: [center1X, center1Y, center2X, center2Y],
          stroke: 'black',
          draggable: true,
        };
        // console.log(arrow);
        this.connections.push({shape1: this.shape1, shape2: this.shape2});
        console.log(this.connections);
        console.log(this.connections[0].shape1.type); 
        this.arrows.push(arrow);
        this.shape1 = null;
        this.shape2 = null;
        this.connection = false;
      }
    },
    setShapes(shape){
      if(this.connection){
        if(this.shape1 == null){
          this.shape1 = shape;
        }
        else if(this.shape2 == null){
          this.shape2 = shape;
          if(this.connectionValidityChecker()){
            this.connect(); 
          }else{
            this.shape2 = null;
            this.shape1 = null;
            this.connection = false;
          }
          
        }
      }
    },
    connectionValidityChecker(){
      if(this.shape1.type == this.shape2.type){
        alert("Invalid Connection: Same types cannot be connected");
        return false;
      }
      if(this.connections.length > 0){
        for(let i = 0; i < this.connections.length; i++){
          if(this.connections[i].shape1 == this.shape1 && this.connections[i].shape2 == this.shape2){
            alert("Invalid Connection: Connection already exists");
            return false;
          }
          else if(this.connections[i].shape1 == this.shape2 && this.connections[i].shape2 == this.shape1){
            alert("Invalid Connection: This would cause a loop");
            return false;
          }
          if(this.shape1.type == "machine"){
            if(this.connections[i].shape1.type == "machine" && this.connections[i].shape1.machineId == this.shape1.machineId){
              alert("Invalid Connection: Machine already connected");
              return false;
            }
          }
          // else if(this.shape1.type == "queue"){
          //   if(this.connections[i].shape1.type == "queue" && this.connections[i].shape1.queueId == this.shape1.queueId){
          //     return false;
          //   }
          //   else if(this.connections[i].shape2.type == "queue" && this.connections[i].shape2.queueId == this.shape1.queueId){
          //     return false;
          //   }
          // }
        }
      }else{
        return true;
      }
    }
  }
}
</script>

<style>
.app{
  width: 100vw;
  height: 100vh;
  background-color:#EEE;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
.navBar{
  height: 50px;
  width: 100%;
  padding: 10px;
  display: flex;
  /* justify-content: flex-end; */
  justify-content: space-around;
  align-items: center;
  background-color: rgb(133, 194, 214);
  color: white;
  font-size: 20px;
  font-family: calibri;
  transform: rotateX('angle');
}
.navBar ul{
  list-style: none;
  display: flex;
  justify-content: flex-end;
  width: 100%;
  margin: 0;
  padding: 0;
}
.navBar li{
  color:black;
  font-weight: bold;
  transition: 0.3s;
} 
.navBar li:hover{
  color: #EEE;
}
.navBar .options{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  cursor: pointer;
  transition: 0.3s;
  margin: 0 20px;
}
.image{
  margin-right: 5px;
}
.screen{
  width: 100%;
  height: calc(100% - 50px);
  display: flex;
  position: relative;
}
.board{
  width: 100%;
  /*background-image:url('./assets/background.png') ;
  background-size: cover;*/
  position: relative;
}
.close{
  position: absolute;
  right: 197px;
  width: 20px;
  height: 40px;
  top: 50%;
  background: #eeeeeeb8;
  padding: 5px 0;
  cursor: pointer;
  transform: rotateY(180deg);
  transition:0.5s;
  z-index: 10;
}
.close:hover{
  background:#aeaeae;
}
.sidebar{
  height: 100%;
  right: 0;
  position: absolute;
  padding: 10px;
  width: 200px;
  overflow: hidden;
  /* background: lightblue; */
  background-color: lightgray;
  font-weight: bold;
  font-size: 18px;
  font-family: calibri;
  transition: width 0.5s ease;
}
.sidebar ul{
  list-style: none;
  display: flex;
  flex-direction: column;
}
.sidebar li{
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  cursor: pointer;
  transition: 0.3s;
}
.sidebar li:hover{
  background: lightblue;
}
li img{
  width: 30px;

}
.active{
  background: lightblue;
}
</style>