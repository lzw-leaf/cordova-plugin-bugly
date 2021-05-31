interface AppInfo {
  versionName: string
  versionCode: string
}

interface BuglySdk {
  checkUpdate(isManual = true, isSilence = false): Promise<string>
  getAppInfo(): Prmoise<AppInfo>
}

declare var BuglySdk: BuglySdk
