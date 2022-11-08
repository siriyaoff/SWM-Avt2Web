<template>
    <div>
        <UnityWebgl></UnityWebgl>
    </div>
</template>

<script>
import UnityWebgl from '../components/UnityWebgl.vue'

export default {
    components: {
        UnityWebgl,
    },
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

.ShowUnityContainer {
    display: none;
}

.ShowInputBox {
    display: none;
}
</style>