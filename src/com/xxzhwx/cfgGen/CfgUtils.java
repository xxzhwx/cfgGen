package com.xxzhwx.cfgGen;

import java.util.ArrayList;
import java.util.List;

import com.xxzhwx.cfgGen.common.Constants;
import com.xxzhwx.cfgGen.xls.XlsCell;
import com.xxzhwx.cfgGen.xls.XlsRow;
import com.xxzhwx.cfgGen.xls.XlsSheet;

public class CfgUtils {
  public static String[] getColumnNames(XlsSheet sheet) {
    XlsRow metaRow = sheet.getRow(1, false);
    if (metaRow == null) {
      return Constants.EMPTY_STRING_ARRAY;
    }

    int lastCellNum = metaRow.getLastCellNum();

    List<String> columnNameList = new ArrayList<>();
    for (int i = 0; i <= lastCellNum; ++i) {
      XlsCell cell = metaRow.getCell(i, false);
      if (cell == null) {
        break;
      }
      columnNameList.add(cell.getValue());
    }

    return columnNameList.toArray(Constants.EMPTY_STRING_ARRAY);
  }
}
