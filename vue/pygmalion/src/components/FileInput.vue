<template>
  <div id="app">
    <div id="submitbox">
      <div id="submitunit">
        <b-form-file
          id="psdfile"
          v-model="file1"
          :state="Boolean(file1)"
          placeholder="Choose a file or drop it here..."
          drop-placeholder="Drop file here..."
          accept=".psd"
        ></b-form-file>
        <div class="mt-3">Selected file: {{ file1 ? file1.name : "" }}</div>
        <button v-on:click="FileSubmit">Submit</button>
      </div>
    </div>
    <Modal v-if="showModal" @close="showModal = false">
      <!--
              you can use custom content here to overwrite
              default content
            -->
      <h3 slot="header" id="modalHeader">
        경고!
        <i class="fas fa-times closeModalBtn" @click="showModal = false"></i>
      </h3>
      <div slot="body">파일을 선택해 주십시오.</div>
      <div slot="footer"></div>
    </Modal>
  </div>
</template>

<script>
import Modal from "./common/AlertModal.vue";
import { postPsdFile } from "../api/index";

export default {
  data() {
    return {
      file1: null,
      showModal: false,
    };
  },
  methods: {
    FileSubmit: function () {
      if (this.file1 == null) {
        this.showModal = !this.showModal;
      } else {
        this.$emit("IsFileSub");
        var formdata = new FormData();
        formdata.append("psd", this.file1);
        postPsdFile(formdata)
          .then((res) => {
            console.log(res);
          })
          .catch((ex) => {
            console.log("Error:", ex);
          });
        //document.getElementById("#app").style = "display: none";
      }
    },
  },
  components: {
    Modal,
  },
};
</script>

<style scoped>
#submitbox {
  top: 150px;
  position: absolute;
  left: 50%;
  margin-left: -30%;
  width: 60%;
  border: 2px solid #b3adad;
  border-radius: 5px 5px 5px 5px;
}
#submitunit {
  margin: 30px 30px 30px 30px;
}
button {
  float: right;
  margin: 0 0 30px;
}
#modalHeader {
  color: black;
}
.closeModalBtn {
  color: #b3adad;
  margin-left: 125px;
}
</style>
