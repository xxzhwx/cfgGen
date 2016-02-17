package com.xxzhwx.cfgGen;

public class CfgLine {
  private String name;
  private String value;

  public CfgLine(String name, String value) {
    this.name = name;
    this.value = value;
  }

  @Override
  public String toString() {
    return "    \"" + name + "\":\"" + value + "\"";
  }
}
