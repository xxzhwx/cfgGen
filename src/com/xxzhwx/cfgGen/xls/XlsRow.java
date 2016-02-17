package com.xxzhwx.cfgGen.xls;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class XlsRow {
  private Row row;
  private Map<Integer, XlsCell> num2CellMap;

  public XlsRow(Row row) {
    this.row = row;
    initCells();
  }

  private void initCells() {
    num2CellMap = new HashMap<>();

    short firstCellNum = row.getFirstCellNum();
    short lastCellNum = row.getLastCellNum();
    for (int cellNum = firstCellNum; cellNum < lastCellNum; ++cellNum) {
      Cell cell = row.getCell(cellNum);
      if (cell == null) {
        // System.out.println(String.format("cell[%d,%d] is null.",
        // row.getRowNum(), cellNum));
        continue;
      }
      addCell(cellNum, cell);
    }
  }

  private XlsCell addCell(int cellNum, Cell cell) {
    XlsCell xlsCell = new XlsCell(cell);
    num2CellMap.put(cellNum, xlsCell);
    return xlsCell;
  }

  public void forEachCell(BiConsumer<Integer, XlsCell> action) {
    num2CellMap.forEach(action);
  }

  /**
   * Gets the index of the last cell contained in this row, 0-based.<br/>
   * Return -1 if the row does not contain any cells.
   */
  public int getLastCellNum() {
    int num = row.getLastCellNum();
    if (num != -1) {
      num -= 1;
    }
    return num;
  }

  public XlsCell getCell(int cellNum, boolean createIfNotExist) {
    if (cellNum < 0) {
      throw new IllegalArgumentException("cellNum: " + cellNum);
    }

    XlsCell xlsCell = num2CellMap.get(cellNum);
    if (xlsCell == null && createIfNotExist) {
      xlsCell = createCell(cellNum);
    }
    return xlsCell;
  }

  private XlsCell createCell(int cellNum) {
    Cell cell = row.createCell(cellNum);
    return addCell(cellNum, cell);
  }
}
