<template>
  <div id="app" class="app">
    <div class="navBar">
      <ul>
        <li>Products Number
        <input type="number">
        </li>
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
        <li class="options" @click="this.current = '',set(), clear()">
            <img src="./assets/clear.png" class="image">
            <p>Clear</p>
        </li>
      </ul>  
    </div>
    <div class="screen">
      <div class="board" ref="parent" @click="create">
        <v-stage :config="configKonva">
          <v-layer>
            <v-regular-polygon v-for="shapeConfig in machines" :key="shapeConfig" :config="shapeConfig" ref="machine" @click="rotate,setShapes(shapeConfig)" @dragMove="updateMachine" />
            <v-circle v-for="shapeConfig in queues" :key="shapeConfig" :config="shapeConfig" ref="queue" @click="setShapes(shapeConfig)" @dragMove="updateQueue" />
            <v-text v-for="text in qNames" :key="text" :config="text" ref="text"/>
            <v-text v-for="text in mNames" :key="text" :config="text" ref="text"/>
            <v-text v-for="text in texts" :key="text" :config="text" ref="text"/>
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
          <li class="item connection" @click="this.current = '',set(), enableConnection(!connection)">
            <img src="./assets/connection.png">
            <p>Connect</p>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { Stage, Layer, Rect, Line, Text, Arrow, Group } from 'vue-konva';
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
      texts: [],
      qNames: [],
      mNames: [],
      // points: [100, 100, 200, 200]
    }
  },
  components: {
    VStage: Stage,
    VLayer: Layer,
    VRect: Rect,
    VLine: Line,
    VText: Text,
    VArrow: Arrow,
    VGroup: Group,
  },
  mounted() {
    const parentRect = this.$refs.parent;
    if(parentRect){
      this.configKonva.height = parentRect.offsetHeight;
      this.configKonva.width = parentRect.offsetWidth;
    }
    
  },
  methods: {
    updateSimulation(message) {
      console.log(message)
    },
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
    },
    updateQueue(e){
      // console.log(e.target.id())
      let id = e.target.id();
      let x = e.target.x();
      let y = e.target.y();
      for(let i = 0; i < this.queues.length; i++){
        if(this.queues[i].id == e.target.id()){
          this.queues[i].x = x
          this.queues[i].y = y
        }
      }
      for(let i = 0; i < this.texts.length; i++){
        if(this.texts[i].id == 'queue'+ id){
          this.texts[i].x = x - 5;
          this.texts[i].y = y + 12;
          // console.log(e.target.x() + "  " + e.target.y())
        }
      }
      for(let i = 0; i < this.qNames.length; i++){
        if(this.qNames[i].id == id){
          this.qNames[i].x = x - 25;
          this.qNames[i].y = y -25;
          // console.log(e.target.x() + "  " + e.target.y())
        }
      }
      for(let i = 0; i < this.arrows.length; i++){
        if(this.arrows[i].idFrom == 'queue' +id){
          this.arrows[i].points[0] = x
          this.arrows[i].points[1] = y;
        }
        if(this.arrows[i].idTo == 'queue' +id){
          this.arrows[i].points[2] = x
          this.arrows[i].points[3] = y;
        }
      }
    },
    updateMachine(e){
      let id = e.target.id();
      // console.log(id)
      let x = e.target.x();
      let y = e.target.y();
      for(let i = 0; i < this.machines.length; i++){
        if(this.machines[i].id == e.target.id()){
          this.machines[i].x = x
          this.machines[i].y = y
        }
      }
      for(let i = 0; i < this.mNames.length; i++){
        if(this.mNames[i].id == id){
          this.mNames[i].x = x - 25;
          this.mNames[i].y = y -25;
        }
      }
      for(let i = 0; i < this.arrows.length; i++){
        if(this.arrows[i].idFrom == 'machine' + id){
          this.arrows[i].points[0] = x
          this.arrows[i].points[1] = y;
        }
        if(this.arrows[i].idTo == 'machine' +id){
          this.arrows[i].points[2] = x
          this.arrows[i].points[3] = y;
        }
      }
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
      this.texts = [];
    },
    create(e){
      const parentRect = this.$refs.parent.getBoundingClientRect();
      let id = -1
      if(this.current == 'machine'){
        const machine = {
          id: this.machines.length,
          x: (e.clientX - parentRect.left ),
          y: (e.clientY - parentRect.top ),
          sides:8,
          radius:50,
          fill:"grey",
          stroke:"black",
          draggable: "true",
          strokeWidth: 5,
          rotate: 0,
          name: "M"+(this.machines.length),
          type: "machine",
          machineId: this.machines.length+1
        };
        id = machine.id
        this.machines.push(machine);
        const mname = {
          id: machine.id,
          x: machine.x - 25,
          y: machine.y - 25,
          text: machine.name,
          fontSize: 30,
          fill: 'white',
          fontFamily: 'calibri'
          // draggable: true,
        }
        this.mNames.push(mname);
      }
      else if(this.current == 'queue'){
        const queue = {
          id: this.queues.length,
          x: (e.clientX - parentRect.left ),
          y: (e.clientY - parentRect.top ),
          radius:50,
          fill:"grey",
          stroke:"black",
          draggable: "true",
          name: "Q"+(this.queues.length),
          type: "queue",
          queueId: this.queues.length+1
        };
        id = queue.id
        this.queues.push(queue);
        const text = {
          id: 'queue'+ queue.id,
          x: queue.x - 5,
          y: queue.y + 12,
          text: '0',
          fontSize: 20,
          fill: 'white',
          fontFamily: 'calibri'
          // draggable: true,
        }
        this.texts.push(text);
        const qname = {
          id: queue.id,
          x: queue.x-25,
          y: queue.y-25,
          text: queue.name,
          fontSize: 30,
          fill: 'white',
          fontFamily: 'calibri'
          // draggable: true,
        }
        this.qNames.push(qname);
      }
      let URL = 'http://localhost:8080/'
      if(this.current == 'queue')URL += 'addQueue'
      if(this.current == 'machine')URL += 'addMachine'
      if(this.current == 'queue' || this.current == 'machine'){
        fetch(URL,{
          method:'POST',
          headers:{
            'Content-Type':'application/json',
          },
          body: JSON.stringify(id),
        }).then().catch(error => console.log(error))
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
          idFrom: this.shape1.type + this.shape1.id,
          idTo: this.shape2.type + this.shape2.id,
          points: [center1X, center1Y, center2X, center2Y],
          stroke: 'black',
          draggable: true,
        };

        this.connections.push({shape1: this.shape1, shape2: this.shape2});
        console.log(this.connections);
        console.log(this.connections[0].shape1.type); 
        this.arrows.push(arrow);
        let toBeSent= {
          fromId : this.shape1.id,
          toId : this.shape2.id,
          source : this.shape1.type
        }
        fetch('http://localhost:8080/addConnection',{
          method:'POST',
          headers:{
            'Content-Type':'application/json',
          },
          body: JSON.stringify(toBeSent),
        })
        this.shape1.fill = 'grey'
        this.shape2.fill = 'grey'
        this.shape1 = null;
        this.shape2 = null;
        this.connection = false;
        document.querySelector(".connection").classList.remove("active");
      }
    },
    enableConnection(connection){
      if(connection == false){
        this.connection == false
        document.querySelector(".connection").classList.remove("active");
        this.shape1.fill = 'grey'
        this.shape2.fill = 'grey'
        this.shape1 = null
        this.shape2 = null
      }
      else{
        this.connection = true;
        document.querySelector(".queue").classList.remove("active");
        document.querySelector(".machine").classList.remove("active");
        document.querySelector(".connection").classList.add("active");
      }
    },
    setShapes(shape){
      if(this.connection){
        if(this.shape1 == null){
          this.shape1 = shape;
          this.shape1.fill = 'red'
        }
        else if(this.shape2 == null){
          this.shape2 = shape;
          this.shape2.fill = 'green'
          if(this.connectionValidityChecker()){

            this.connect();
          }else{
            this.shape1.fill = 'grey'
            this.shape2.fill = 'grey'
            this.shape2 = null;
            this.shape1 = null;
            this.connection = false;
          }
          
        }
      }
    },
    connectionValidityChecker(){
      document.querySelector(".connection").classList.remove("active");
      if(this.shape1.type == this.shape2.type){
        alert("Invalid Connection: Same types cannot be connected");
        return false;
      }
      // if(this.connections.length > 0){
        for(let i = 0; i < this.connections.length; i++){
          if(this.connections[i].shape1 == this.shape1 && this.connections[i].shape2 == this.shape2){
            
            this.connection = false;
            alert("Invalid Connection: Connection already exists");
            return false;
          }
          else if(this.connections[i].shape1 == this.shape2 && this.connections[i].shape2 == this.shape1){
            this.connection = false;
            alert("Invalid Connection: This would cause a loop");
            return false;
          }
          if(this.shape1.type == "machine"){
            if(this.connections[i].shape1.type == "machine" && this.connections[i].shape1.machineId == this.shape1.machineId){
              this.connection = false;
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
      // }else{
        return true;
      // }
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
  height: 70px;
  width: 100%;
  padding: 10px;
  display: flex;
  /* justify-content: flex-end; */
  justify-content: space-around;
  align-items: center;
  padding-right: 150px;
  padding-left: 100px;
  background-color: rgb(133, 194, 214);
  color: white;
  font-size: 20px;
  font-family: calibri;
  transform: rotateX('angle');
}
.navBar ul{
  list-style: none;
  display: flex;
  /* justify-content: flex-end; */
  justify-content: space-around;
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