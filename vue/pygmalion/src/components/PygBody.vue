<template>
  <div id="BodyMain">
    <PygCarousel></PygCarousel>
    <b-row>
        <b-col>
            <div class="ServiceEx" id="ServiceExTitle">서비스 소개</div>
            <div class="ServiceEx">
                안녕하세요
            </div>
        </b-col>
    <b-col>
        <InputImg v-bind:propsdata="imgName"></InputImg>
    </b-col>
    </b-row>
  </div>
</template>

<script>
import InputImg from './InputImg.vue'
import PygCarousel from './PygCarousel.vue'
export default {
    data: function(){
        return {
            imgName: 'SakuraMiko.png',
        }
    },
    components: {
    InputImg: InputImg,
    PygCarousel: PygCarousel,
}
}
</script>

<style scoped>
.ServiceEx {
    text-align: center;
}
#ServiceExTitle {
    margin-top: 30px;
    font-size: 20px;
    font-style: bold;
}
</style>