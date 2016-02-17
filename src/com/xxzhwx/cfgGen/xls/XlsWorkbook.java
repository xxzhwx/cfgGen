package com.xxzhwx.cfgGen.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class XlsWorkbook {
  private String fileName;
  private Workbook workbook;
  private Map<String, XlsSheet> name2SheetMap;

  public XlsWorkbook(String fileName) {
    this.fileName = fileName;
    initWorkbook();
    initSheets();
  }

  private void initWorkbook() {
    File file = new File(fileName);
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(file);
      workbook = WorkbookFactory.create(fis);
    } catch (Exception e) {
      throw new RuntimeException("Fail to open file: " + fileName);
    } finally {
      if (fis != null) {
        try {
          fis.close();
          fis = null;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void initSheets() {
    name2SheetMap = new HashMap<>();

    int numOfSheets = workbook.getNumberOfSheets();
    for (int i = 0; i < numOfSheets; ++i) {
      String sheetName = workbook.getSheetName(i);
      Sheet sheet = workbook.getSheet(sheetName);
      addSheet(sheetName, sheet);
    }
  }

  private XlsSheet addSheet(String sheetName, Sheet sheet) {
    XlsSheet xlsSheet = new XlsSheet(sheet);
    name2SheetMap.put(sheetName, xlsSheet);
    return xlsSheet;
  }

  public XlsSheet getSheet(String name) {
    return name2SheetMap.get(name);
  }

  public void forEachSheet(BiConsumer<String, XlsSheet> action) {
    name2SheetMap.forEach(action);
  }

  public XlsSheet createSheet(String sheetName) {
    Sheet sheet = workbook.createSheet(sheetName);
    return addSheet(sheetName, sheet);
  }

  public void save() {
    File file = new File(fileName);
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(file);
      workbook.write(fos);
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

  public void close() {
    if (workbook != null) {
      try {
        workbook.close();
        workbook = null;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
