package com.xxzhwx.cfgGen.common;

import java.io.File;
import java.io.IOException;

public final class FileUtils {
  /**
   * 创建文件，若文件已经存在则返回 true
   */
  public static boolean createFile(String fileName) {
    if (fileName.endsWith(File.separator)) {
      return false;
    }

    File file = new File(fileName);
    if (file.isFile()) {
      return true;
    }

    File parent = file.getParentFile();
    if (!parent.exists()) {
      if (!parent.mkdirs()) {
        return false;
      }
    }

    try {
      if (!file.createNewFile()) {
        return false;
      }
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 创建目录，若目录存在则返回 true
   */
  public static boolean createDir(String dirName) {
    File dir = new File(dirName);
    if (dir.isDirectory()) {
      return true;
    }

    if (!dir.mkdirs()) {
      return false;
    }

    return true;
  }

  /**
   * 删除文件或文件夹
   */
  public static void deletePath(File file) {
    if (!file.exists()) {
      return;
    }

    if (file.isFile()) {
      file.delete();
      return;
    }

    File[] files = file.listFiles();
    for (File f : files) {
      deletePath(f);
    }
  }
}
