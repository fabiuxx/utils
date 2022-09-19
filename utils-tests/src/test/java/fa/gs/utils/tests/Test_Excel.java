/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests;

import fa.gs.utils.misc.excel.Excel;
import fa.gs.utils.misc.excel.ExcelCellType;
import java.io.File;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Test_Excel {

    @Test
    public void test0() throws Throwable {
        File file = new File("C:\\Users\\Fabio\\Desktop\\test.xlsx");
        XSSFWorkbook wb = Excel.load(file);
        XSSFSheet ws = Excel.sheet(wb, 0);
        XSSFCell c0 = Excel.cell(ws, 0, 0);
        Assertions.assertNotNull(c0);
        Assertions.assertEquals(ExcelCellType.STRING, Excel.type(c0));
        String t0 = Excel.text(c0, "--");
        Assertions.assertEquals("A", t0);

        XSSFCell c1 = Excel.cell(ws, 1, 0);
        Assertions.assertNotNull(c1);
        Assertions.assertEquals(ExcelCellType.STRING, Excel.type(c1));
        String t1 = Excel.text(c1, "--");
        Assertions.assertEquals("1", t1);
    }

}
