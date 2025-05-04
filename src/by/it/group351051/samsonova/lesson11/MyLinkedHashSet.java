package by.it.group351051.samsonova.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {
    private Node<E>[] buckets;
    private int size;
    private Node<E> head;
    private Node<E> tail;

    public MyLinkedHashSet() {
        buckets = new Node[16];
        size = 0;
        head = null;
        tail = null;
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> orderNext;

        Node(E item, Node<E> next, Node<E> orderNext) {
            this.item = item;
            this.next = next;
            this.orderNext = orderNext;
        }
    }

    private int getIndex(Object o) {
        if (o == null) return 0;
        int hash = o.hashCode();
        return Math.abs(hash) % buckets.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<E> current = head;
        while (current != null) {
            sb.append(current.item);
            if (current.orderNext != null) {
                sb.append(", ");
            }
            current = current.orderNext;
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E e) {
        int index = getIndex(e);
        Node<E> current = buckets[index];

        while (current != null) {
            if (e == null ? current.item == null : e.equals(current.item)) {
                return false;
            }
            current = current.next;
        }

        Node<E> newNode = new Node<>(e, buckets[index], null);
        buckets[index] = newNode;

        if (head == null) {
            head = newNode;
        } else {
            tail.orderNext = newNode;
        }
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = getIndex(o);
        Node<E> current = buckets[index];
        Node<E> prev = null;

        while (current != null) {
            if (o == null ? current.item == null : o.equals(current.item)) {
                if (prev == null) {
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }

                Node<E> orderPrev = null;
                Node<E> orderCurrent = head;
                while (orderCurrent != current) {
                    orderPrev = orderCurrent;
                    orderCurrent = orderCurrent.orderNext;
                }
                if (orderPrev == null) {
                    head = current.orderNext;
                } else {
                    orderPrev.orderNext = current.orderNext;
                }
                if (current == tail) {
                    tail = orderPrev;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        int index = getIndex(o);
        Node<E> current = buckets[index];
        while (current != null) {
            if (o == null ? current.item == null : o.equals(current.item)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        Node<E> current = head;
        while (current != null) {
            Node<E> next = current.orderNext;
            if (c.contains(current.item)) {
                remove(current.item);
                modified = true;
            }
            current = next;
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Node<E> current = head;
        while (current != null) {
            Node<E> next = current.orderNext;
            if (!c.contains(current.item)) {
                remove(current.item);
                modified = true;
            }
            current = next;
        }
        return modified;
    }

    @Override
    public Iterator<E> iterator() {
        return null; // Заглушка
    }

    @Override
    public Object[] toArray() {
        return new Object[0]; // Заглушка
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null; // Заглушка
    }
}