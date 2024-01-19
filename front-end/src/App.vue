<template>
  <div id="app" class="app">
    <div class="header">Header</div>
    <div class="screen">
      <div class="board">
        <img src="./assets/left-arrow.png" class="close" @click="close">
      </div>
      <div class="sidebar">
        <ul>
          <li class="item machine">
            <img src="./assets/m.png">
            <p>Machine</p>
          </li>
          <li class="item queue">
            <img src="./assets/q.png">
            <p>Queue</p>
          </li>
          <li class="item connection">
            <img src="./assets/connection.png">
            <p>Connect</p>
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
      sideBar: true
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
      }
      else{
        arrow.style.transform= "rotateY(180deg)"
        sideBar.style.width = "200px";
        sideBar.style.padding = "0px";
      }
      this.sideBar = !this.sideBar
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
.header{
  height: 50px;
  width: 100%;
  padding: 10px;
  background-color: red;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  transform: rotateX('angle');
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
  right: -3px;
  width: 20px;
  height: 40px;
  top: 50%;
  background: #eeeeeeb8;
  padding: 5px 0;
  cursor: pointer;
  transform: rotateY(180deg);
  transition:0.5s;
}
.sidebar{
  padding: 10px;
  width: 200px;
  overflow: hidden;
  background: lightblue;
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
  background: #EEE;
}
li img{
  width: 30px;

}
</style>