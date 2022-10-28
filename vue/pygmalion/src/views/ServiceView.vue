<template>
    <div>
        <FileInput v-on:IsFileSub="ShowUnity" v-bind:class="{ShowInputBox: BoolFileInput}"></FileInput>
        <UnityWebgl v-bind:class="{ShowUnityContainer: BoolFileSub}"></UnityWebgl>
    </div>
</template>

<script>
import FileInput from '../components/FileInput.vue'
import UnityWebgl from '../components/UnityWebgl.vue'

export default {
    data: function(){
        return{
            BoolFileSub: true,
            BoolFileInput: false,
        }
    },
    components: {
        FileInput,
        UnityWebgl,
    },
    methods: {
        ShowUnity: function() {
            this.BoolFileSub = false;
            this.BoolFileInput = true;
        }
    }
}
</script>

<style>
body {
    text-align: center;
    background-color: #F6F6F6;
}

input {
    border-style: groove;
    width: 200px;
}

button {
    border-style: groove;
}

.shadow {
    box-shadow: 5px 10px 10px rgba(0, 0, 0, 0.03);
}
.ShowUnityContainer{
    display: none;
}
.ShowInputBox {
    display: none;
}
</style>