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
package fa.gs.utils.collections.lists;

import lombok.Data;

/**
 *
 * @author Fabio A. González Sosa
 */
@Data
public class LinkedList<T> {

    private LinkedList.Node<T> head;
    private LinkedList.Node<T> tail;
    private int size;

    private LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public static <T> LinkedList<T> instance() {
        return new LinkedList<>();
    }

    public LinkedList.Node<T> getNode(int pos) {
        if (pos >= 0 && pos <= (size - 1)) {
            LinkedList.Node<T> node = head;
            while (pos > 0 && node != null) {
                pos--;
                node = node.next;
            }
            return node;
        } else {
            return null;
        }
    }

    public T get(int pos) {
        LinkedList.Node<T> node = getNode(pos);
        return (node != null) ? node.data : null;
    }

    public void add(T data) {
        LinkedList.Node<T> node = new LinkedList.Node<>(data);
        if (head == null) {
            head = node;
            tail = node;
            size = 1;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
            size = size + 1;
        }
    }

    public void push(T data) {
        add(data);
    }

    public T pop() {
        if (tail != null) {
            T data = tail.data;
            tail = tail.prev;
            size = size - 1;
            return data;
        } else {
            return null;
        }
    }

    @Data
    public static final class Node<T> {

        private T data;
        private Node<T> next;
        private Node<T> prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }
}
