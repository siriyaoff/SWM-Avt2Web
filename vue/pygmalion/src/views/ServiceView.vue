<template>
    <div>
        <FileInput></FileInput>
    </div>
</template>

<script>
import FileInput from '../components/FileInput.vue'

export default {
    components: {
        FileInput,
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
</style>