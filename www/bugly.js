const exec = require('cordova/exec')

/**
 * 触发bugly更新检测
 * @param {boolean} isManual 是否用户手动触发
 * @param {boolean} isSilence 是否静默 （false则弹窗 ,true则不触发）
 * @return {Promise<string>} 返回提示信息
 */
exports.checkUpgrade = function (isManual = true, isSilence = false) {
  return new Promise((resolve, reject) => {
    exec(resolve, reject, 'BuglySdk', 'checkUpgrade', [isManual, isSilence])
  })
}

/**
 * 获取应用信息
 * 目前只返回 versionName,versionCode
 * @return {Promise<{versionName,versionCode}>} 返回应用信息
 */
exports.getAppInfo = function () {
  return new Promise((resolve, reject) => {
    exec(resolve, reject, 'BuglySdk', 'getAppInfo', [])
  })
}
