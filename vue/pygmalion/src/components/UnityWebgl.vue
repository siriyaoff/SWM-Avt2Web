<template>
    <div id="unity-container" class="unity-desktop">
        <canvas id="unity-canvas" width=960 height=600></canvas>
        <div id="unity-loading-bar">
            <div id="unity-logo"></div>
            <div id="unity-progress-bar-empty">
                <div id="unity-progress-bar-full"></div>
            </div>
        </div>
        <div id="unity-warning"> </div>
        <div id="unity-footer">
            <div id="unity-webgl-logo"></div>
            <div id="unity-fullscreen-button"></div>
            <div id="unity-build-title">uma_grond</div>
        </div>
    </div>
</template>

<script>
/* eslint-disable */
var buildUrl = "/Build";
var loaderUrl = buildUrl + "/Build.loader.js";
var config = {
    dataUrl: buildUrl + "/Build.data",
    frameworkUrl: buildUrl + "/Build.framework.js",
    codeUrl: buildUrl + "/Build.wasm",
    streamingAssetsUrl: "StreamingAssets",
    companyName: "DefaultCompany",
    productName: "uma_grond",
    productVersion: "0.1",
    showBanner: unityShowBanner,
};

var script = document.createElement("script");
script.src = loaderUrl;

function unityShowBanner(msg, type) {
    function updateBannerVisibility() {
        document.getElementById('unity-warning').style.display = document.getElementById('unity-warning').children.length ? 'block' : 'none';
    }
    var div = document.createElement('div');
    div.innerHTML = msg;
    document.getElementById('unity-warning').appendChild(div);
    if (type == 'error') div.style = 'background: red; padding: 10px;';
    else {
        if (type == 'warning') div.style = 'background: yellow; padding: 10px;';
        setTimeout(function () {
            document.getElementById('unity-warning').removeChild(div);
            updateBannerVisibility();
        }, 5000);
    }
    updateBannerVisibility();
}

// var img = new Image;
// var c = document.createElement("canvas");
// var ctx = c.getContext("2d");


// img.onload = function () {
//     c.width = this.naturalWidth;     // update canvas size to match image
//     c.height = this.naturalHeight;
//     ctx.drawImage(this, 0, 0);       // draw in image
//     c.toBlob(function (blob) {        // get content as JPEG blob
//         png = blob.text;
//         console.log(png);
//     }, "image/png", 0.75);
// };
// img.crossOrigin = "";              // if from different origin
// img.src = "https://i.imgur.com/ozvfB7F.png";
// function open(){
//     var input = document.createElement("input");
//     input.type = "file";
//     input.id = "test";
//     input.onchange = function process(input) {
//         let file = input.files[0];
//         let reader = new FileReader();
//         reader.onload = function (e) {
//             unityInstance.SendMessage('Canvas', 'receiveBytearr', e.target.result);
//         };
//         reader.readAsDataURL(file);
//     }
// }

script.onload = () => {
    createUnityInstance( document.getElementById('unity-canvas'), config, (progress) => {
        document.getElementById('unity-progress-bar-full').style.width = 100 * progress + "%";
    }).then((unityInstance) => {
        document.getElementById('unity-loading-bar').style.display = "none";
        

        document.getElementById('unity-fullscreen-button').onclick = () => {
            unityInstance.SendMessage('EyeLash', 'callStart');
            
            //unityInstance.SendMessage('Canvas', 'receiveTexture2D', png );
            //unityInstance.SendMessage('Canvas', 'receiveSprite', png );
            

            //unityInstance.SetFullscreen(1);
        };
    }).catch((message) => {
        alert(message);
    });
};

document.body.appendChild(script);

</script>

<style scoped>
body {
    padding: 0;
    margin: 0
}

#unity-container {
    position: absolute;
}

#unity-container.unity-desktop {
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%)
}

#unity-container.unity-mobile {
    width: 100%;
    height: 100%
}

#unity-canvas {
    background: #231F20;
    width: 960px;
    height: 600px;
}

.unity-mobile #unity-canvas {
    width: 100%;
    height: 100%
}

#unity-loading-bar {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    display: block;
}

#unity-logo {
    width: 154px;
    height: 130px;
    background: url('../../public/TemplateData/unity-logo-light.png') no-repeat center
}

#unity-progress-bar-empty {
    width: 141px;
    height: 18px;
    margin-top: 10px;
    margin-left: 6.5px;
    background: url('../../public/TemplateData/progress-bar-empty-dark.png') no-repeat center
}

#unity-progress-bar-full {
    width: 0%;
    height: 18px;
    margin-top: 10px;
    background: url('../../public/TemplateData/progress-bar-full-dark.png') no-repeat center
}

#unity-footer {
    position: relative
}

.unity-mobile #unity-footer {
    display: none
}

#unity-webgl-logo {
    float: left;
    width: 204px;
    height: 38px;
    background: url('../../public/TemplateData/webgl-logo.png') no-repeat center
}

#unity-build-title {
    float: right;
    margin-right: 10px;
    line-height: 38px;
    font-family: arial;
    font-size: 18px
}

#unity-fullscreen-button {
    float: right;
    width: 38px;
    height: 38px;
    background: url('../../public/TemplateData/fullscreen-button.png') no-repeat center
}

#unity-warning {
    position: absolute;
    left: 50%;
    top: 5%;
    transform: translate(-50%);
    background: white;
    padding: 10px;
    display: none
}
</style>