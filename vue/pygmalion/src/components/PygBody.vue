<template>
  <div id="BodyMain">
    <PygCarousel></PygCarousel>
    <b-row>
        <b-col>
            <div class="ServiceEx" id="ServiceExTitle">프로젝트 소개</div>
            <div class="ServiceEx" id="ServiceExMain">
                psd 파일 형식의 2D 컨셉 아트 캐릭터 원본 일러스트를 입력받아<br>
                AI를 통해 눈동자, 눈썹 등 얼굴의 특징점을 분석하고,<br>
                해당 내용을 바탕으로 3D 아바타를 생성합니다<br>
            </div>
        </b-col>
    <b-col>
        <InputImg v-bind:propsdata="imgName" class="bodyImage"></InputImg>
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
            imgName: '4.jpeg',
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
    margin-bottom: 40px;
    font-size: 30px;
    font-style: bold;
}
#ServiceExMain {
    text-align: left;
    margin-left: 10%;
}
.bodyImage {
    margin-top: 40px;
    margin-left: 5%;
    margin-right: 10%;
    margin-bottom: 40px;
}
</style>