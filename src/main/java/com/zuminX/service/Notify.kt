package com.zuminX.service

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.zuminX.service.Information.Companion.message
import org.jetbrains.annotations.PropertyKey

/**
 * 通知类
 */
class Notify(val project : Project) {

  /**
   * 以info级别通知信息
   *
   * @param key    该信息对应的键
   * @param params 参数
   */
  fun info(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) {
    notify(NotificationType.INFORMATION, key, params)
  }

  /**
   * 以warning级别通知信息
   *
   * @param key    该信息对应的键
   * @param params 参数
   */
  fun warning(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) {
    notify(NotificationType.WARNING, key, params)
  }

  /**
   * 以error级别通知信息
   *
   * @param key    该信息对应的键
   * @param params 参数
   */
  fun error(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) {
    notify(NotificationType.ERROR, key, params)
  }

  /**
   * 从配置文件中获取信息进行通知
   *
   * @param type   通知类型
   * @param key    该信息对应的键
   * @param params 参数
   */
  private fun notify(type: NotificationType, key: String, vararg params: Any) {
    NOTIFICATION_GROUP.createNotification(message(key, params), type).notify(project)
  }
}

fun Project.getNotify(): Notify = getService(Notify::class.java)

private val NOTIFICATION_GROUP = NotificationGroupManager.getInstance().getNotificationGroup("Easy Swagger Notification Group")
