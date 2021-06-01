interface AppInfo {
  versionName: string
  versionCode: string
}

interface Bugly {
  /**
   * 触发bugly更新检测
   * @param {boolean} isManual 是否用户手动触发 默认 true
   * @param {boolean} isSilence 是否静默 （false则弹窗 ,true则不触发）默认 false
   * @return {Promise<string>} 返回提示信息
   */
  checkUpgrade(isManual?: boolean, isSilence?: boolean): Promise<string>

  /**
   * 获取应用信息
   * 目前只返回 versionName,versionCode
   * @return {Promise<{versionName,versionCode}>} 返回应用信息
   */
  getAppInfo(): Promise<AppInfo>
}

declare var BuglySdk: Bugly
