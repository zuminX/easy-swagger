package com.zuminX.service;

import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

/**
 * 通知类
 */
@AllArgsConstructor
public class Notify {

  private static final NotificationGroup NOTIFICATION_GROUP;

  static {
    NOTIFICATION_GROUP = NotificationGroupManager.getInstance().getNotificationGroup("Easy Swagger Notification Group");
  }

  private final Project project;

  /**
   * 获取实例对象
   *
   * @param project 项目对象
   * @return 通知类对象
   */
  public static Notify getInstance(@NotNull Project project) {
    return ServiceManager.getService(project, Notify.class);
  }

  /**
   * 以info级别通知信息
   *
   * @param key    该信息对应的键
   * @param params 参数
   */
  public void info(@PropertyKey(resourceBundle = Information.BUNDLE) String key, Object... params) {
    notify(NotificationType.INFORMATION, key, params);
  }

  /**
   * 以warning级别通知信息
   *
   * @param key    该信息对应的键
   * @param params 参数
   */
  public void warning(@PropertyKey(resourceBundle = Information.BUNDLE) String key, Object... params) {
    notify(NotificationType.WARNING, key, params);
  }

  /**
   * 以error级别通知信息
   *
   * @param key    该信息对应的键
   * @param params 参数
   */
  public void error(@PropertyKey(resourceBundle = Information.BUNDLE) String key, Object... params) {
    notify(NotificationType.ERROR, key, params);
  }

  /**
   * 从配置文件中获取信息进行通知
   *
   * @param type   通知类型
   * @param key    该信息对应的键
   * @param params 参数
   */
  private void notify(@NotNull NotificationType type, String key, Object... params) {
    NOTIFICATION_GROUP.createNotification(Information.message(key, params), type).notify(project);
  }
}