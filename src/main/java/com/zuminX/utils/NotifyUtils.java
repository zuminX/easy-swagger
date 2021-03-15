package com.zuminX.utils;

import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

/**
 * 通知工具类
 */
public class NotifyUtils {

  private static final NotificationGroup GROUP;
  private static Project PROJECT;

  static {
    GROUP = NotificationGroupManager.getInstance().getNotificationGroup("Easy Swagger Notification Group");
  }

  public static void notifyWarring(String content) {
    GROUP.createNotification(content, NotificationType.WARNING).notify(PROJECT);
  }

  public static void notifyInformation(String content) {
    GROUP.createNotification(content, NotificationType.INFORMATION).notify(PROJECT);
  }

  public static void notifyError(String content) {
    GROUP.createNotification(content, NotificationType.ERROR).notify(PROJECT);
  }

  public static void setProject(Project project) {
    NotifyUtils.PROJECT = project;
  }

}