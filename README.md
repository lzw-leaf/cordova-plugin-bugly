# cordova-plugin-bugly

基于腾讯的[Bugly](https://bugly.qq.com/v2/index)集成 cordova 插件

目前只支持应用升级，做了自定义的dialog样式

## Support

仅支持Android (ios目前没需求)  
**要求 `AndroidSdkVersion` \>= 21。**    
 小于21的需要自行测试（理论可用）

## Installation

    cordova plugin add cordova-plugin-bugly

## Config

在cordova项目根目录的配置上Bugly的appid

``` xml
<!-- /config.xml -->
<preference name="buglyAppId" value="Your BuglyAppId" />
<!-- 例子：  -->
<preference name="buglyAppId" value="92894924" />

```

由于内置了andoridX的布局样式，需要config.xml上配置AndroidXEnabled开启支持

``` xml
<!-- /config.xml -->
  <preference name="AndroidXEnabled" value="true" />
```

为保障兼容需要安装 `cordova-plugin-androidx-adapter` 插件做处理

    cordova plugin add cordova-plugin-androidx-adapter

### Methods

> 挂载在全局window上的全局变量是 `BugSdk`

#### 检测更新

``` js
/**
 * 触发bugly更新检测
 * @param {boolean} isManual 是否用户手动触发
 * @param {boolean} isSilence 是否静默 （false则弹窗 ,true则不触发）
 * @return {Promise<string>} 返回提示信息
 */
BugSdk.checkUpgrade(isManual = true, isSilence = false)
    .then(msg => {
        // 成功触发更新
    }).catch(e => {
        //sdk.checkUpgrade错误捕获
    })
```

#### 获取应用信息

``` js
/**
 * 获取应用信息
 * 目前只返回 versionName,versionCode
 * @return {Promise<{versionName,versionCode}>} 返回应用信息
 */
BugSdk.getAppInfo().then(data => {
    const {
        versionName,
        versionCode
    } = data
    // versionName 是版本号
    // versionCode 是版本code
})
}
```

## 注意

1. 截止2021年5月31日 `cordova plugins add`指令 在npm7.X版本不支持添加本地插件需要自行降版本 建议通过`nvm`管理多版本node
2. 编写了d.ts文件可以再ts项目中使用
3. cordova项目开发中碰到奇怪的问题可以试试 `cordova platform rm android` 再 `cordova platform add android`

吐槽一下工作中的那些旧项目
本意是想升级一下工作中用到的cordova-android，来拥抱新版本，结果升级到9.0后才悲哀的发现原有的bugly相关内容是散在项目中的，剥了半天才总算一个一个拖拽出来拧到一起。什么鬼才想法会直接在platform中改代码，难怪项目体积这么大
