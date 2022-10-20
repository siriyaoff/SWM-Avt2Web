<template>
    <div id="app">
        <b-form-file id="psdfile" v-model="file1" :state="Boolean(file1)" placeholder="Choose a file or drop it here..."
            drop-placeholder="Drop file here..." accept=".png" v-on:input="FileSubmit"></b-form-file>
        <div class="mt-3">Selected file: {{ file1 ? file1.name : '' }}</div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            file1: null,
        }
    },
    methods: {
        FileSubmit: function() {
            // this.$emit("IsFileSub");
            document.getElementById("#app").style = "display: none";
        }
    }
}
</script>
<style scoped>

</style>