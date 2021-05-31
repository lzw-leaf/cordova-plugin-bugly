/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at
         http://www.apache.org/licenses/LICENSE-2.0
       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package wxt.cordova.plugins.bugly;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.ui.UILifecycleListener;

import java.lang.reflect.Method;

/**
 * Plugins must extend this class and override one of the execute methods.
 */
public class BuglyPlugin extends CordovaPlugin {


    /**
     * Called after plugin construction and fields have been initialized.
     */
    protected void pluginInitialize() {
        Log.d("buglyPlugin", "initializing buglyPlugin");
        ConfigXmlParser parser = new ConfigXmlParser();
        parser.parse(cordova.getActivity());//解析res/xml/config.xml文件
        Log.d("buglyPlugin", "try get appid");
        CordovaPreferences preferences = parser.getPreferences();
        String appId= preferences.getString("buglyAppId","");
        Log.d("buglyPlugin", "appid:" + appId);
        if( appId != "" ) initBugly(appId);
    }

    /**
     * 初始化 Bugly
     */
    public void  initBugly(String buglyAppId){
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.initDelay = 3 * 1000;
        Beta.showInterruptedStrategy = true;
        Beta.autoDownloadOnWifi = false;
        Beta.upgradeDialogLayoutId =cordova.getActivity().getResources().getIdentifier("update_dialog", "layout", cordova.getActivity().getPackageName()) ;
        Beta.upgradeDialogLifecycleListener = new UILifecycleListener<UpgradeInfo>() {
            @Override
            public void onCreate(Context context, View view, UpgradeInfo upgradeInfo) {
                TextView textFeature = (TextView) view.findViewWithTag("beta_upgrade_feature");
                textFeature.setMovementMethod(new ScrollingMovementMethod());
                TextView textConfirm = (TextView) view.findViewWithTag("beta_confirm_button");
                textConfirm.requestFocus();
            }
            @Override
            public void onStart(Context context, View view, UpgradeInfo upgradeInfo) {}

            @Override
            public void onResume(Context context, View view, UpgradeInfo upgradeInfo) {}

            @Override
            public void onPause(Context context, View view, UpgradeInfo upgradeInfo) {}

            @Override
            public void onStop(Context context, View view, UpgradeInfo upgradeInfo) {}

            @Override
            public void onDestroy(Context context, View view, UpgradeInfo upgradeInfo) {}
        };
        Bugly.init(cordova.getActivity().getApplicationContext(),buglyAppId,false);
        Log.d("buglySdk", "初始化完成");
    }

    /**
     * 手动检测更新
     */
    public void checkUpgrade(CallbackContext callbackContext)
    {
        try {
            Beta.checkUpgrade();
            callbackContext.success("success");
        }catch (Error e){
            callbackContext.error(e.getMessage());
        }

    }

    /**
     * 获取应用信息
     */
    private void getAppInfo(CallbackContext callbackContext) {
        try {
            PackageManager packageManager = cordova.getActivity().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    cordova.getActivity().getPackageName(), 0);
            JSONObject result = new JSONObject();
            result.put("versionName", packageInfo.versionName);
            result.put("versionCode", String.valueOf(packageInfo.versionCode));
            callbackContext.success(result);
        } catch (Exception e) {
            callbackContext.error(e.getMessage());
        }
    }



    /**
     * Detection of update
     * @param action          The action to execute.
     * @param args            The exec() arguments.
     * @param callbackContext The callback context used when calling back into JavaScript.
     * @return                Whether the action was valid.
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.d(action, "execute is triggered");
        if ("checkUpgrade".equals(action)) {
            checkUpgrade(callbackContext);
            return true;
        }else if("getAppInfo".equals(action)) {
            getAppInfo(callbackContext);
            return true;
        }
        return false;
    }

}