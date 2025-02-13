package by.it.group351051.zaluzhny.lesson03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Lesson 3. C_Heap.
// Задача: построить max-кучу = пирамиду = бинарное сбалансированное дерево на массиве.
// ВАЖНО! НЕЛЬЗЯ ИСПОЛЬЗОВАТЬ НИКАКИЕ КОЛЛЕКЦИИ, КРОМЕ ARRAYLIST (его можно, но только для массива)

//      Проверка проводится по данным файла
//      Первая строка входа содержит число операций 1 ≤ n ≤ 100000.
//      Каждая из последующих nn строк задают операцию одного из следующих двух типов:

//      Insert x, где 0 ≤ x ≤ 1000000000 — целое число;
//      ExtractMax.

//      Первая операция добавляет число x в очередь с приоритетами,
//      вторая — извлекает максимальное число и выводит его.

//      Sample Input:
//      6
//      Insert 200
//      Insert 10
//      ExtractMax
//      Insert 5
//      Insert 500
//      ExtractMax
//
//      Sample Output:
//      200
//      500


public class C_HeapMax {

    private class MaxHeap {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //тут запишите ваше решение.
        //Будет мало? Ну тогда можете его собрать как Generic и/или использовать в варианте B
        private List<Long> heap = new ArrayList<>();

        int siftDown(int i) { //просеивание вверх
            int size = heap.size();
            while (i < size) {
                int leftChildIndex = 2 * i + 1;
                int rightChildIndex = 2 * i + 2;
                int largestIndex = i;

                if (leftChildIndex < size && heap.get(leftChildIndex) > heap.get(largestIndex)) {
                    largestIndex = leftChildIndex;
                }

                if (rightChildIndex < size && heap.get(rightChildIndex) > heap.get(largestIndex)) {
                    largestIndex = rightChildIndex;
                }

                if (largestIndex != i) {
                    // Меняем местами
                    Long temp = heap.get(i);
                    heap.set(i, heap.get(largestIndex));
                    heap.set(largestIndex, temp);
                    i = largestIndex;
                } else {
                    break;
                }
            }
            return i;
        }

        int siftUp(int i) { //просеивание вниз
            while (i > 0) {
                int parentIndex = (i - 1) / 2;
                if (heap.get(i) > heap.get(parentIndex)) {
                    // Меняем местами
                    Long temp = heap.get(i);
                    heap.set(i, heap.get(parentIndex));
                    heap.set(parentIndex, temp);
                    i = parentIndex;
                } else {
                    break;
                }
            }
            return i;
        }

        void insert(Long value) { //вставка
            heap.add(value);
            siftUp(heap.size() - 1);
        }

        Long extractMax() { //извлечение и удаление максимума
            if (heap.size() == 0) {
                return null;
            }
            Long max = heap.get(0);
            Long lastElement = heap.remove(heap.size() - 1);
            if (heap.size() > 0) {
                heap.set(0, lastElement);
                siftDown(0);
            }
            return max;
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! КОНЕЦ ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
    }

    //эта процедура читает данные из файла, ее можно не менять.
    Long findMaxValue(InputStream stream) {
        Long maxValue=0L;
        MaxHeap heap = new MaxHeap();
        //прочитаем строку для кодирования из тестового файла
        Scanner scanner = new Scanner(stream);
        Integer count = scanner.nextInt();
        for (int i = 0; i < count; ) {
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("extractMax")) {
                Long res=heap.extractMax();
                if (res!=null && res>maxValue) maxValue=res;
                System.out.println();
                i++;
            }
            if (s.contains(" ")) {
                String[] p = s.split(" ");
                if (p[0].equalsIgnoreCase("insert"))
                    heap.insert(Long.parseLong(p[1]));
                i++;
            //System.out.println(heap); //debug
            }
        }
        return maxValue;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson03/heapData.txt");
        C_HeapMax instance = new C_HeapMax();
        System.out.println("MAX="+instance.findMaxValue(stream));
    }

    // РЕМАРКА. Это задание исключительно учебное.
    // Свои собственные кучи нужны довольно редко.
    // "В реальном бою" все существенно иначе. Изучите и используйте коллекции
    // TreeSet, TreeMap, PriorityQueue и т.д. с нужным CompareTo() для объекта внутри.
}
