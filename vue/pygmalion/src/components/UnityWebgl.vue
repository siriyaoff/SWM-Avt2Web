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

// const eyelashjson = "{\"Point\":[{\"id\":\"0\",\"x\":\"25\",\"y\":\"62\"},{\"id\":\"1\",\"x\":\"40\",\"y\":\"56\"},{\"id\":\"2\",\"x\":\"27\",\"y\":\"72\"},{\"id\":\"3\",\"x\":\"26\",\"y\":\"78\"},{\"id\":\"4\",\"x\":\"30\",\"y\":\"79\"},{\"id\":\"5\",\"x\":\"48\",\"y\":\"63\"},{\"id\":\"6\",\"x\":\"64\",\"y\":\"57\"},{\"id\":\"7\",\"x\":\"97\",\"y\":\"59\"},{\"id\":\"8\",\"x\":\"111\",\"y\":\"65\"},{\"id\":\"9\",\"x\":\"126\",\"y\":\"80\"},{\"id\":\"10\",\"x\":\"130\",\"y\":\"90\"},{\"id\":\"11\",\"x\":\"126\",\"y\":\"109\"},{\"id\":\"12\",\"x\":\"142\",\"y\":\"95\"},{\"id\":\"13\",\"x\":\"151\",\"y\":\"95\"},{\"id\":\"14\",\"x\":\"147\",\"y\":\"84\"},{\"id\":\"15\",\"x\":\"133\",\"y\":\"67\"},{\"id\":\"16\",\"x\":\"141\",\"y\":\"65\"},{\"id\":\"17\",\"x\":\"110\",\"y\":\"48\"},{\"id\":\"18\",\"x\":\"63\",\"y\":\"46\"},{\"id\":\"19\",\"x\":\"39\",\"y\":\"53\"}]}"
// const facejson = "{\"Point\":[{\"id\":\"0\",\"x\":\"118.48593\",\"y\":\"190.95702\"},{\"id\":\"1\",\"x\":\"142.89635\",\"y\":\"258.28796\"},{\"id\":\"2\",\"x\":\"216.27458\",\"y\":\"302.4483\"},{\"id\":\"3\",\"x\":\"290.5553\",\"y\":\"252.19106\"},{\"id\":\"4\",\"x\":\"313.39783\",\"y\":\"189.54433\"},{\"id\":\"5\",\"x\":\"134.25572\",\"y\":\"139.26167\"},{\"id\":\"6\",\"x\":\"157.14348\",\"y\":\"130.97289\"},{\"id\":\"7\",\"x\":\"182.10796\",\"y\":\"131.82689\"},{\"id\":\"8\",\"x\":\"236.72868\",\"y\":\"131.4446\"},{\"id\":\"9\",\"x\":\"261.97534\",\"y\":\"129.26927\"},{\"id\":\"10\",\"x\":\"289.53735\",\"y\":\"134.26164\"},{\"id\":\"11\",\"x\":\"136.28722\",\"y\":\"184.96913\"},{\"id\":\"12\",\"x\":\"155.46474\",\"y\":\"167.32848\"},{\"id\":\"13\",\"x\":\"184.88268\",\"y\":\"177.63304\"},{\"id\":\"14\",\"x\":\"147.00957\",\"y\":\"203.97829\"},{\"id\":\"15\",\"x\":\"163.3372\",\"y\":\"206.8141\"},{\"id\":\"16\",\"x\":\"178.8806\",\"y\":\"202.8509\"},{\"id\":\"17\",\"x\":\"245.78004\",\"y\":\"176.41853\"},{\"id\":\"18\",\"x\":\"273.4135\",\"y\":\"167.45662\"},{\"id\":\"19\",\"x\":\"294.74646\",\"y\":\"182.48863\"},{\"id\":\"20\",\"x\":\"251.39738\",\"y\":\"199.92595\"},{\"id\":\"21\",\"x\":\"267.20282\",\"y\":\"206.58635\"},{\"id\":\"22\",\"x\":\"284.91034\",\"y\":\"204.24956\"},{\"id\":\"23\",\"x\":\"212.51286\",\"y\":\"226.36882\"},{\"id\":\"24\",\"x\":\"193.29155\",\"y\":\"256.3261\"},{\"id\":\"25\",\"x\":\"214.40715\",\"y\":\"261.44287\"},{\"id\":\"26\",\"x\":\"235.27916\",\"y\":\"256.18353\"},{\"id\":\"27\",\"x\":\"214.41386\",\"y\":\"261.81555\"}]}"

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

script.onload = () => {
    createUnityInstance( document.getElementById('unity-canvas'), config, (progress) => {
        document.getElementById('unity-progress-bar-full').style.width = 100 * progress + "%";
    }).then((unityInstance) => {
        document.getElementById('unity-loading-bar').style.display = "none";
        document.getElementById('unity-fullscreen-button').onclick = () => {
            unityInstance.SendMessage('EyelashMakerMirror', 'MakeEyelash', localStorage.getItem('eyelash'))
            unityInstance.SendMessage('EyelashMakerMirror (1)', 'MakeEyelash', localStorage.getItem('eyelash'))
            unityInstance.SendMessage('DynamicAvatar1', 'SetFaceParameter', localStorage.getItem('face'))
            unityInstance.SendMessage('DynamicAvatar1', 'ChangeEye', localStorage.getItem('iris'))
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