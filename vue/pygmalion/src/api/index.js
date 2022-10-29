import axios from 'axios'

const AxiosInst = axios.create({
    baseURL : 'https://avatar2-front-test.herokuapp.com/ec2-13-124-191-61.ap-northeast-2.compute.amazonaws.com:8080'
})

AxiosInst.interceptors.request.use(
    (config) => {
        config.headers.authorization = 'token';
        config.headers['Access-Control-Allow-Origin'] = '*';  // CORS 설정(모든 리소스 허용)
        return config;
    }
)

export default AxiosInst;

function get3DModel() {
    return axios.get('/get/test');
}

function postPsdFile(data) {
    return AxiosInst.post('/stringtest', data);
}

function getAiStatus() {
    return axios.get('');
}

// get3DModel()
// .then(res => {
//     console.log(res.data)
// })
// .catch((ex) => {
//     console.log('Error:', ex);
// })

// postPsdFile()
// .then((res) => {
//     this.result = res.data;
// })
// .catch((ex) => {
//     console.log('Error:', ex);
// })

// getAiStatus()
// .then()
// .catch((ex) => {
//     console.log('Error:', ex);
// })


export {
    get3DModel,
    postPsdFile,
    getAiStatus,
}