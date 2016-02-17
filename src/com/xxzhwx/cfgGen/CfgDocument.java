package com.xxzhwx.cfgGen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.xxzhwx.cfgGen.common.FileUtils;

public class CfgDocument {
  private String fileName;
  private List<CfgSection> sectionList;

  public CfgDocument(String fileName) {
    this.fileName = fileName;
    this.sectionList = new ArrayList<>();
  }

  public CfgDocument addSection(CfgSection section) {
    sectionList.add(section);
    return this;
  }

  public void saveToFile() {
    File file = new File(fileName);
    if (!file.exists()) {
      FileUtils.createFile(fileName);
    }

    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(file);

      String content = this.toString();
      byte[] bytes = content.getBytes("utf-8");
      fos.write(bytes, 0, bytes.length);
      fos.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (fos != null) {
        try {
          fos.close();
          fos = null;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(64);
    sb.append("[\n");
    for (int i = 0, n = sectionList.size(); i < n; ++i) {
      if (i != 0) {
        sb.append(",\n");
      }
      sb.append(sectionList.get(i));
    }
    sb.append("\n]");
    return sb.toString();
  }
}
