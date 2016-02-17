package com.xxzhwx.cfgGen;

import java.io.File;
import java.util.function.BiConsumer;

import com.xxzhwx.cfgGen.common.FileUtils;
import com.xxzhwx.cfgGen.xls.XlsCell;
import com.xxzhwx.cfgGen.xls.XlsRow;
import com.xxzhwx.cfgGen.xls.XlsSheet;
import com.xxzhwx.cfgGen.xls.XlsWorkbook;

public class CfgGen {
  public static void main(String[] args) {
    String inDir = "F:\\work\\product\\数值表格";
    String outDir = "D:\\config";
    String name = "*";

    if (args.length == 3) {
      inDir = args[0];
      outDir = args[1];
      name = args[2];
    }

    System.out.println("inDir:" + inDir);
    System.out.println("outDir:" + outDir);
    System.out.println("name:" + name);

    if (!name.equalsIgnoreCase("*")) {
      name = name.replace(".xlsx", "");
      gen(inDir, outDir, name);
      return;
    }

    // '*' - 生成全部
    File inFolder = new File(inDir);
    if (!inFolder.isDirectory()) {
      throw new RuntimeException("Input directory not found!");
    }

    // 为防止误操作限制输出目录的路径必须以 'config' 结尾
    if (!outDir.endsWith("config")) {
      throw new RuntimeException(
          "Output directory is invalid, should ends with 'config'!");
    }

    File outFolder = new File(outDir);
    FileUtils.deletePath(outFolder);

    File[] files = inFolder.listFiles();
    for (File f : files) {
      if (!f.isFile()) {
        continue;
      }

      String fileName = f.getName();
      if (!fileName.endsWith(".xlsx")) {
        continue;
      }

      if (fileName.startsWith("~")) {
        continue;
      }

      fileName = fileName.replace(".xlsx", "");
      gen(inDir, outDir, fileName);
    }
  }

  private static void gen(String inDir, String outDir, String xlsFileName) {
    String inFileName = xlsFileName + ".xlsx";
    if (!inDir.isEmpty()) {
      inFileName = inDir + "\\" + inFileName;
    }

    XlsWorkbook wb = new XlsWorkbook(inFileName);
    wb.forEachSheet(new BiConsumer<String, XlsSheet>() {
      @Override
      public void accept(String sheetName, XlsSheet sheet) {
        System.out.println("工作表：" + xlsFileName + "->" + sheetName);

        String outFileName = xlsFileName + sheetName + ".json";
        if (!outDir.isEmpty()) {
          outFileName = outDir + "\\" + outFileName;
        }

        CfgDocument doc = new CfgDocument(outFileName);

        String[] columnNames = CfgUtils.getColumnNames(sheet);
        int columnSize = columnNames.length;
        sheet.forEachRow(new BiConsumer<Integer, XlsRow>() {
          @Override
          public void accept(Integer rowNum, XlsRow row) {
            if (rowNum < 2) { // 跳过前2行
              return;
            }

            CfgSection sec = new CfgSection();
            for (int i = 0; i < columnSize; ++i) {
              XlsCell cell = row.getCell(i, false);
              if (cell == null && i == 0) { // 第一列值为空则跳过该行
                return;
              }

              String value = ""; // 空格子的值默认为空字符串
              if (cell != null) {
                value = cell.getValue();
              }

              sec.addLine(new CfgLine(columnNames[i], value));
            }
            doc.addSection(sec);
          }
        });

        doc.saveToFile();
      }
    });

    // wb.createSheet("新工作表");
    // wb.save();
    wb.close();
  }
}
