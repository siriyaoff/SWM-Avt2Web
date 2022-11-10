<template>
    <div id="app">
        <div id="submitbox" v-if="!isLoading">
        <div id="submitunit">
        <!-- <form action="https://ec2-13-124-191-61.ap-northeast-2.compute.amazonaws.com:8080/"
         id="infform" method="post" enctype="multipart/form-data"> -->
        <b-form-file id="psdfile" v-model="file1" :state="Boolean(file1)" placeholder="Choose a file or drop it here..."
            drop-placeholder="Drop file here..." accept=".psd" type="file" name="psd"></b-form-file>
        <div class="mt-3">Selected file: {{ file1 ? file1.name : '' }}</div>
        <div id="HeightCont">
        <b-form-input id="height" v-model="height1" placeholder="Enter height" type="text"></b-form-input>
        <div class="mt-2">height : {{ height1 }}</div>
        </div>
        <button v-on:click="FileSubmit">Submit</button>
        <!-- </form> -->
        </div>
        </div>
        <div class="d-flex justify-content-center mb-3 m-5">
        <b-spinner label="Loading..." v-if="isLoading"></b-spinner>
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
import Modal from './common/AlertModal.vue'
import { postPsdFile } from '../api/index'

export default {
    data() {
        return {
            isLoading:false,
            file1: null,
            showModal: false,
            height1: '160',
        }
    },
    methods: {
        FileSubmit: function() {
            if(this.file1==null)
            {
                this.showModal = !this.showModal;
            }
            else{
                this.isLoading = !this.isLoading;
                this.$emit("IsFileSub");
                var formdata = new FormData();
                formdata.append('psd', this.file1);
                formdata.append('height', this.height1);
                postPsdFile(formdata)
                    .then((res) => {
                        this.isLoading = !this.isLoading;
                        console.log(res);
                        this.$router.push('/result');
                    })
                    .catch((ex) => {
                        console.log('Error:', ex);
                        this.$router.push('/result');
                    })
            //document.getElementById("#app").style = "display: none";
            }
        }
    },
    components: {
        Modal
    }
}
</script>

<style scoped>
#submitbox{
    top: 150px;
    position: absolute;
    left: 50%;
    margin-left: -30%;
    width: 60%;
    border: 2px solid #b3adad;
    border-radius: 5px 5px 5px 5px;
}
#submitunit{   
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
#HeightCont{
    padding-top: 20px;
}
</style>