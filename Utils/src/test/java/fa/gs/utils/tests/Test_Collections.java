/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests;

import fa.gs.utils.collections.Arrays;
import fa.gs.utils.collections.Lists;
import java.util.Collection;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Test_Collections {

    private static Collection<Point> points;

    @BeforeClass
    public static void init() {
        points = Lists.empty();
        points.add(new Point(1, 2f, 3f));
        points.add(new Point(2, 2f, 3f));
        points.add(new Point(1, 2f, 3f));
        points.add(new Point(3, 2f, 3f));
        points.add(new Point(3, 2f, 3f));
        points.add(new Point(2, 2f, 3f));
        points.add(new Point(1, 2f, 3f));
    }

    @Test
    public void test0() {
        Integer[] ids = Arrays.array(points, "id", Integer.class);
        Assert.assertEquals(3, ids.length);
    }

    @Test
    public void test1() {
        Integer[] ids = Arrays.array(points, "id", Integer.class);
        Assert.assertArrayEquals(ids, new Integer[]{1, 2, 3});
    }

    private static class Point {

        Integer id;
        Float x;
        Float y;

        public Point(Integer id, Float x, Float y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }
}
