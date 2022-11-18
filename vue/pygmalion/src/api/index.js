import axios from "axios";

const AxiosInst = axios.create({
  baseURL: "http://ec2-13-209-174-9.ap-northeast-2.compute.amazonaws.com:8080",
});

AxiosInst.interceptors.request.use((config) => {
  config.headers.authorization = "token";
  config.headers["Access-Control-Allow-Origin"] = "*"; // CORS 설정(모든 리소스 허용)
  return config;
});

export default AxiosInst;

function postPsdFile(data) {
  return AxiosInst.post("/create", data);
}

export { postPsdFile };