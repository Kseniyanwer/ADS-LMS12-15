package by.it.group351051.samsonova.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {
    private Node<E>[] buckets;
    private int size;

    public MyHashSet() {
        buckets = new Node[16];
        size = 0;
    }

    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

    private int getIndex(Object o) {
        if (o == null) return 0;
        int hash = o.hashCode();
        return Math.abs(hash) % buckets.length;
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

        if (current == null) {
            buckets[index] = new Node<>(e, null);
            size++;
            return true;
        }

        Node<E> prev = null;
        while (current != null) {
            if (e == null ? current.item == null : e.equals(current.item)) {
                return false;
            }
            prev = current;
            current = current.next;
        }

        prev.next = new Node<>(e, null);
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean firstElement = true;

        for (int i = 0; i < buckets.length; i++) {
            Node<E> current = buckets[i];
            while (current != null) {
                if (!firstElement) {
                    sb.append(", ");
                }
                sb.append(current.item);
                firstElement = false;
                current = current.next;
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // Заглушки для остальных методов Set
    @Override public boolean addAll(Collection<? extends E> c) { return false; }
    @Override public boolean containsAll(Collection<?> c) { return false; }
    @Override public boolean removeAll(Collection<?> c) { return false; }
    @Override public boolean retainAll(Collection<?> c) { return false; }
    @Override public Iterator<E> iterator() { return null; }
    @Override public Object[] toArray() { return new Object[0]; }
    @Override public <T> T[] toArray(T[] a) { return null; }
}