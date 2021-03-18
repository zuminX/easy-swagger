package com.zuminX.service;

import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * 通知类
 */
public class Notify {

  private static final NotificationGroup NOTIFICATION_GROUP;

  static {
    NOTIFICATION_GROUP = NotificationGroupManager.getInstance().getNotificationGroup("Easy Swagger Notification Group");
  }

  private final Project project;

  private Notify(@NotNull Project project) {
    this.project = project;
  }

  public static Notify getInstance(@NotNull Project project) {
    return ServiceManager.getService(project, Notify.class);
  }

  public void info(@NotNull String content) {
    notify(content, NotificationType.INFORMATION);
  }

  public void warning(@NotNull String content) {
    notify(content, NotificationType.WARNING);
  }

  public void error(@NotNull String content) {
    notify(content, NotificationType.ERROR);
  }

  private void notify(@NotNull String content, @NotNull NotificationType type) {
    NOTIFICATION_GROUP.createNotification(content, type).notify(project);
  }
}