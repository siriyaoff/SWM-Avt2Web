<template>
    <div>
        <PygBody></PygBody>
        <PygFooter></PygFooter>
    </div>
</template>

<script>
import PygBody from '../components/PygBody.vue'
import PygFooter from '../components/PygFooter.vue'

export default {
    components: {
        PygBody,
        PygFooter,
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
</style>