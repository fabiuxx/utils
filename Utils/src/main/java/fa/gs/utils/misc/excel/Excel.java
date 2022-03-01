/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.excel;

import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.errors.Errors;
import fa.gs.utils.misc.fechas.Fechas;
import fa.gs.utils.misc.fechas.Formats;
import fa.gs.utils.misc.numeric.Currency;
import fa.gs.utils.misc.numeric.Numeric;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java8.util.Objects;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Excel {

    /**
     * Carga un archivo fisico a la memoria.
     *
     * @param file Archivo Excel.
     * @return Representacion en memoria.
     * @throws Throwable Si no es posible cargar el archivo.
     */
    public static XSSFWorkbook load(File file) throws Throwable {
        OPCPackage fs = OPCPackage.open(file);
        XSSFWorkbook wb = new XSSFWorkbook(fs);
        return wb;
    }

    /**
     * Carga un archivo fisico a la memoria.
     *
     * @param stream Archivo Excel.
     * @return Representacion en memoria.
     * @throws Throwable Si no es posible cargar el archivo.
     */
    public static XSSFWorkbook load(InputStream stream) throws Throwable {
        OPCPackage fs = OPCPackage.open(stream);
        XSSFWorkbook wb = new XSSFWorkbook(fs);
        return wb;
    }

    /**
     * Obtiene una hoja especifica de un libro excel.
     *
     * @param workbook Libro excel.
     * @param name Nombre de la hoja.
     * @return Hoja, si hubiere. Caso contrario {@code null}.
     */
    public static XSSFSheet sheet(XSSFWorkbook workbook, String name) {
        return workbook.getSheet(name);
    }

    /**
     * Obtiene una hoja especifica de un libro excel.
     *
     * @param workbook Libro excel.
     * @param index Posicion de la hoja.
     * @return Hoja, si hubiere. Caso contrario {@code null}.
     */
    public static XSSFSheet sheet(XSSFWorkbook workbook, int index) {
        return workbook.getSheetAt(index);
    }

    /**
     * Obtiene una fila especifica de una hoja excel.
     *
     * @param sheet Hoja excel.
     * @param row Numero de fila.
     * @return Fila, si hubiere. Caso contrario {@code null}.
     */
    public static XSSFRow row(XSSFSheet sheet, int row) {
        return row(sheet, row, true);
    }

    /**
     * Obtiene (o crea) una fila especifica de una hoja excel.
     *
     * @param sheet Hoja excel.
     * @param row Numero de fila.
     * @param createIfMissing Si se debe crear la fila en caso que no exista.
     * @return Fila, si hubiere. Caso contrario {@code null}.
     */
    public static XSSFRow row(XSSFSheet sheet, int row, boolean createIfMissing) {
        if (Assertions.isNull(sheet)) {
            return null;
        }

        XSSFRow row_ = sheet.getRow(row);
        if (row_ == null) {
            row_ = sheet.createRow(row);
        }

        return row_;
    }

    /**
     * Obtiene la cantidad de filas con datos en una hoja excel.
     *
     * @param sheet Hoja excel.
     * @return Cantidad de filas con datos (no vacias).
     */
    public static int rowCount(XSSFSheet sheet) {
        int rowCount = 0;
        Iterator<Row> iter = sheet.rowIterator();
        while (iter.hasNext()) {
            Row row = iter.next();
            if (!isEmpty(row)) {
                rowCount = row.getRowNum();
            }
        }
        return rowCount;
    }

    /**
     * Obtiene una celda especifica de una hoja excel.
     *
     * @param sheet Hoja excel.
     * @param row Numero de fila.
     * @param col Numero de columna (celda).
     * @return Celda, si hubiere. Caso contrario {@code null}.
     */
    public static XSSFCell cell(XSSFSheet sheet, int row, int col) {
        if (Assertions.isNull(sheet)) {
            return null;
        }

        XSSFRow row_ = row(sheet, row);
        return cell(row_, col);
    }

    /**
     * Obtiene una celda especifica de una fila excel.
     *
     * @param row Fila excel.
     * @param col Numero de columna (celda).
     * @return Celda, si hubiere. Caso contrario {@code null}.
     */
    public static XSSFCell cell(XSSFRow row, int col) {
        if (Assertions.isNull(row)) {
            return null;
        }

        return row.getCell(col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
    }

    /**
     * Determina el tipo de dato de una celda.
     *
     * @param cell Celda.
     * @return Tipo de dato.
     */
    public static ExcelCellType type(Cell cell) {
        if (Assertions.isNull(cell)) {
            return ExcelCellType.NULL;
        }

        if (isEmpty(cell)) {
            return ExcelCellType.EMPTY;
        }

        if (cell.getCellType() == CellType.ERROR) {
            return ExcelCellType.ERROR;
        }

        if (cell.getCellType() == CellType.NUMERIC) {
            return ExcelCellType.NUMERIC;
        }

        if (cell.getCellType() == CellType.STRING) {
            return ExcelCellType.STRING;
        }

        if (cell.getCellType() == CellType.FORMULA) {
            return ExcelCellType.FORMULA;
        }

        if (cell.getCellType() == CellType.BOOLEAN) {
            return ExcelCellType.BOOLEAN;
        }

        throw Errors.illegalArgument("Tipo de celda desconocido '%s'.", cell.getCellType());
    }

    /**
     * Obtiene el valor de fecha en una celda. La celda debe contene un valor en
     * formato {@code dd/MMM/yyyy}.
     *
     * @param cell Celda excel.
     * @param fallback Valor alternativo en caso de que la celda no contenga un
     * valor correcto.
     * @return Fecha.
     */
    public static Date date(XSSFCell cell, Date fallback) {
        try {
            if (Assertions.isNull(cell)) {
                return fallback;
            }

            if (type(cell) == ExcelCellType.NUMERIC) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    if (date != null) {
                        return date;
                    }
                }
            }

            String text = text(cell, null);
            if (text != null) {
                Date tmp = Fechas.parse(text, Formats.DD_MMM_YYYY);
                if (tmp != null) {
                    return tmp;
                }
            }

            return fallback;
        } catch (Throwable thr) {
            return fallback;
        }
    }

    /**
     * Obtiene el valor numerico en una celda.
     *
     * @param cell Celda excel.
     * @param fallback Valor alternativo en caso de que la celda no contenga un
     * valor correcto.
     * @return Valor numerico.
     */
    public static BigDecimal number(XSSFCell cell, BigDecimal fallback) {
        try {
            if (Assertions.isNull(cell)) {
                return fallback;
            }

            if (type(cell) == ExcelCellType.NUMERIC) {
                double value = cell.getNumericCellValue();
                return Numeric.wrap(value);
            }

            String text = text(cell, null);
            if (text != null) {
                BigDecimal tmp = Currency.parseCurrency(text);
                if (tmp != null) {
                    return tmp;
                }
            }

            return fallback;
        } catch (Throwable thr) {
            return fallback;
        }
    }

    /**
     * Obtiene el valor de texto en una celda.
     *
     * @param cell Celda excel.
     * @param fallback Valor alternativo en caso de que la celda no contenga un
     * valor correcto.
     * @return Texto.
     */
    public static String text(Cell cell, String fallback) {
        try {
            if (Assertions.isNull(cell)) {
                return fallback;
            }

            if (type(cell) == ExcelCellType.STRING) {
                return cell.getStringCellValue();
            }

            return fallback;
        } catch (Throwable thr) {
            return fallback;
        }
    }

    /**
     * Obtiene el valor de bandera en una celda. S=si, N=no.
     *
     * @param cell Celda excel.
     * @param fallback Valor alternativo en caso de que la celda no contenga un
     * valor correcto.
     * @return Booleano.
     */
    public static Boolean bool(Cell cell, Boolean fallback) {
        String text = text(cell, "N");
        if (Objects.equals(text, "S")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Verifica si una fila esta completamente vacia.
     *
     * @param row Fila excel.
     * @return {@code true} si la fila esta vacia, caso contrario {@code false}.
     */
    public static boolean isEmpty(Row row) {
        if (Assertions.isNull(row)) {
            return true;
        }

        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (!isEmpty(cell)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isEmpty(Cell cell) {
        if (cell == null) {
            return true;
        }

        boolean a = cell.getCellType() == CellType.BLANK;
        boolean b = cell.getCellType() == CellType.STRING && Assertions.stringNullOrEmpty(cell.getStringCellValue());
        return a || b;
    }

}
