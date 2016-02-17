package com.xxzhwx.cfgGen.xls;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

public class XlsCell {
  private Cell cell;

  public XlsCell(Cell cell) {
    this.cell = cell;
  }

  public boolean isBlank() {
    return cell.getCellType() == Cell.CELL_TYPE_BLANK;
  }

  public String getValue() {
    DataFormatter df = new DataFormatter();
    String value;
    switch (cell.getCellType()) {
    case Cell.CELL_TYPE_FORMULA: {
      try {
        value = String.valueOf(cell.getNumericCellValue());
      } catch (IllegalStateException e) {
        value = "#ERROR";
      }
      break;
    }
    case Cell.CELL_TYPE_BLANK:
    case Cell.CELL_TYPE_BOOLEAN:
    case Cell.CELL_TYPE_NUMERIC:
    case Cell.CELL_TYPE_STRING: {
      value = df.formatCellValue(cell);
      break;
    }
    default: {
      value = null;
      break;
    }
    }

    return value;
  }

  public XlsCell setValue(boolean value) {
    cell.setCellValue(value);
    // cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
    return this;
  }

  public XlsCell setValue(int value) {
    cell.setCellValue(value);
    // cell.setCellType(Cell.CELL_TYPE_NUMERIC);
    return this;
  }

  public XlsCell setValue(double value) {
    cell.setCellValue(value);
    // cell.setCellType(Cell.CELL_TYPE_NUMERIC);
    return this;
  }

  public XlsCell setValue(String value) {
    cell.setCellValue(value);
    // cell.setCellType(Cell.CELL_TYPE_STRING);
    return this;
  }
}
