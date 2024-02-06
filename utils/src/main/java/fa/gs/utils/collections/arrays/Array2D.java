/*
 * The MIT License
 *
 * Copyright 2023 Fabio A. González Sosa.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fa.gs.utils.collections.arrays;

import java.io.Serializable;
import java.util.ArrayList;
import lombok.Getter;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Array2D<T> implements Serializable {

    private final ArrayList<T> elements;

    @Getter
    private int rows;

    @Getter
    private final int columns;

    public Array2D(int rows, int columns) {
        int size = rows * columns;
        this.rows = rows;
        this.columns = columns;
        this.elements = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            this.elements.add(null);
        }
    }

    public void addRow() {
        rows++;
        for (int i = 0; i < columns; i++) {
            elements.add(null);
        }
    }

    public void set(int i, int j, T element) {
        int p = j + (i * columns);
        elements.set(p, element);
    }

    public T get(int i, int j) {
        int p = j + (i * columns);
        return elements.get(p);
    }

    public int getTotal() {
        return rows * columns;
    }

}
