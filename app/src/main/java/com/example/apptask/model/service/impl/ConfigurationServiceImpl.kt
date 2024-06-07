
package com.example.apptask.model.service.impl

import android.content.ContentValues.TAG
import android.util.Log
import com.example.apptask.BuildConfig
import com.example.apptask.R.xml as AppConfig
import com.example.apptask.model.service.ConfigurationService
import com.example.apptask.model.service.trace
//import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.get
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class ConfigurationServiceImpl @Inject constructor() : ConfigurationService {


  private val remoteConfig
    get() = Firebase.remoteConfig

  init {
    if (BuildConfig.DEBUG) {
      val configSettings = remoteConfigSettings { minimumFetchIntervalInSeconds = 0 }
      remoteConfig.setConfigSettingsAsync(configSettings)
    }

    remoteConfig.setDefaultsAsync(AppConfig.remote_config_defaults)
  }



//tìm nạp các giá trị từ máy chủ và được gọi ngay khi ứng dụng khởi động
  override suspend fun fetchConfiguration(): Boolean {
    return remoteConfig.fetchAndActivate().await()
  }
  //trả về giá trị boolean đã được push cho tham số  tạo trong Bảng điều khiển
  override val isShowTaskEditButtonConfig: Boolean
    get() = remoteConfig[SHOW_TASK_EDIT_BUTTON_KEY].asBoolean()
  companion object {
    private const val SHOW_TASK_EDIT_BUTTON_KEY = "show_task_edit_button"
    private const val FETCH_CONFIG_TRACE = "fetchConfig"
  }
}
