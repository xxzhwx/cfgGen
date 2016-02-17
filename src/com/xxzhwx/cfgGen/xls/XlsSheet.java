package com.xxzhwx.cfgGen.xls;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class XlsSheet {
  private Sheet sheet;
  private Map<Integer, XlsRow> num2RowMap;

  public XlsSheet(Sheet sheet) {
    this.sheet = sheet;
    initRows();
  }

  private void initRows() {
    num2RowMap = new HashMap<>();

    int firstRowNum = sheet.getFirstRowNum();
    int lastRowNum = sheet.getLastRowNum();
    for (int rowNum = firstRowNum; rowNum <= lastRowNum; ++rowNum) {
      Row row = sheet.getRow(rowNum);
      if (row == null) {
        // System.out.println(String.format("row[%d] is null.", rowNum));
        continue;
      }
      addRow(rowNum, row);
    }
  }

  private XlsRow addRow(int rowNum, Row row) {
    XlsRow xlsRow = new XlsRow(row);
    num2RowMap.put(rowNum, xlsRow);
    return xlsRow;
  }

  public String getName() {
    return sheet.getSheetName();
  }

  public void forEachRow(BiConsumer<Integer, XlsRow> action) {
    num2RowMap.forEach(action);
  }

  /**
   * Gets the index of the last row contained in this sheet, 0-based.
   */
  public int getLastRowNum() {
    return sheet.getLastRowNum();
  }

  public XlsRow getRow(int rowNum, boolean createIfNotExist) {
    if (rowNum < 0) {
      throw new IllegalArgumentException("rowNum: " + rowNum);
    }

    XlsRow xlsRow = num2RowMap.get(rowNum);
    if (xlsRow == null && createIfNotExist) {
      xlsRow = createRow(rowNum);
    }
    return xlsRow;
  }

  private XlsRow createRow(int rowNum) {
    Row row = sheet.createRow(rowNum);
    return addRow(rowNum, row);
  }
}
