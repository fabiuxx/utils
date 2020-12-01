/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.excel;

/**
 *
 * @author Fabio A. González Sosa
 */
public enum ExcelCellType {
    // Celda nula.
    NULL,
    // Celda vacia.
    EMPTY,
    // Celda con errores.
    ERROR,
    // Celda con valor numerico.
    NUMERIC,
    // Celda con valor de texto.
    STRING,
    // Celda con valor derivado de formula.
    FORMULA,
    // Celda con valor booleano.
    BOOLEAN
}
