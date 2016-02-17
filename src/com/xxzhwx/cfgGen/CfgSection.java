package com.xxzhwx.cfgGen;

import java.util.ArrayList;
import java.util.List;

public class CfgSection {
  private List<CfgLine> lineList;

  public CfgSection() {
    this.lineList = new ArrayList<>();
  }

  public CfgSection addLine(CfgLine line) {
    lineList.add(line);
    return this;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(64);
    sb.append("  {\n");
    for (int i = 0, n = lineList.size(); i < n; ++i) {
      if (i != 0) {
        sb.append(",\n");
      }
      sb.append(lineList.get(i));
    }
    sb.append("\n  }");
    return sb.toString();
  }
}
