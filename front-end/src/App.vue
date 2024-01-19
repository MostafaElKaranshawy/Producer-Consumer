<template>
  <div id="app" class="app">
    <div class="navBar">
      <ul>
        <li class="options" v-if="execution" @click="execution = false">
          <img src="./assets/pause.png" class="image">
          <p> Pause</p>
        </li>
        <li class="options" v-if="!execution" @click="execution = true">
          <img src="./assets/start.png" class="image">
          <p> Run </p>
        </li>
        <li class="options">
          <img src="./assets/restart.png" class="image">
          <p> Restart</p>
        </li>
      </ul>
    </div>
    <div class="screen">
      <div class="board" ref="parent">
        <v-stage :config="configKonva">
          <v-layer>
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
          <li class="item connection" @click="this.current = '',set()">
            <img src="./assets/connection.png">
            <p>Connect</p>
          </li>
          <li class="item clear" @click="this.current = '',set()">
            <img src="./assets/clear.png" class="image">
            <p>Clear</p>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      sideBar: true,
      execution: false,
      configKonva: {
        width: 200,
        height: 200
      },
      machines: [],
      queues: [],
      current: ''
    }
  },
  mounted() {
    const parentRect = this.$refs.parent;
    if(parentRect){
      this.configKonva.height = parentRect.offsetHeight;
      this.configKonva.width = parentRect.offsetWidth;
    }
  },
  methods: {
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
      console.log(this.current)
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