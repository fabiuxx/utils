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
package fa.gs.utils.misc.colors;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author Fabio A. González Sosa
 */
@Data
public class RGBAColor implements Serializable {

    public static final int MAX_CHANNEL_VALUE = 0xFF;

    private int r;
    private int g;
    private int b;
    private int a;

    public RGBAColor() {
        this(0, 0, 0);
    }

    public RGBAColor(int r, int g, int b) {
        this(r, g, b, MAX_CHANNEL_VALUE);
    }

    public RGBAColor(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public String toRGBHex() {
        return String.format("#%02X%02X%02X", r, g, b);
    }

    public String toRGBAHex() {
        return String.format("#%02X%02X%02X%02X", r, g, b, a);
    }

}
