package by.it.group351051.samsonova.lesson13;
import java.util.*;

public class GraphA {
    // Список смежности для представления графа
    private List<List<Integer>> adjList;
    // Множество посещённых вершин
    private Set<Integer> visited;
    // Стек для хранения результата топологической сортировки
    private Stack<Integer> result;

    // Конструктор
    public GraphA() {
        adjList = new ArrayList<>();
        visited = new HashSet<>();
        result = new Stack<>();
    }

    // Метод для добавления вершины (если она ещё не существует)
    private void addVertex(int v) {
        while (adjList.size() <= v) {
            adjList.add(new ArrayList<>());
        }
    }

    // Метод для добавления ребра u -> v
    private void addEdge(int u, int v) {
        addVertex(u);
        addVertex(v);
        adjList.get(u).add(v);
    }

    // DFS для топологической сортировки
    private void dfs(int v) {
        visited.add(v); // Отмечаем вершину как посещённую
        // Обрабатываем всех соседей
        for (int neighbor : adjList.get(v)) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor);
            }
        }
        // Добавляем вершину в стек после обработки всех соседей
        result.push(v);
    }

    // Метод для выполнения топологической сортировки
    private List<Integer> topologicalSort(int maxVertex) {
        visited.clear();
        result.clear();

        // Создаём приоритетную очередь для лексикографического порядка
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        // Добавляем все вершины от 0 до maxVertex
        for (int i = 0; i <= maxVertex; i++) {
            if (adjList.size() > i && !visited.contains(i)) {
                pq.offer(i);
            }
        }

        // Обрабатываем вершины в порядке возрастания
        while (!pq.isEmpty()) {
            int v = pq.poll();
            if (!visited.contains(v)) {
                dfs(v);
            }
        }

        // Формируем результат из стека
        List<Integer> sorted = new ArrayList<>();
        while (!result.isEmpty()) {
            sorted.add(result.pop());
        }
        return sorted;
    }

    // Главный метод
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine(); // Считываем входную строку
        scanner.close();

        GraphA graph = new GraphA();

        // Парсим входную строку
        String[] edges = input.split(", ");
        int maxVertex = 0; // Для определения максимальной вершины
        for (String edge : edges) {
            String[] vertices = edge.split(" -> ");
            int u = Integer.parseInt(vertices[0]);
            int v = Integer.parseInt(vertices[1]);
            graph.addEdge(u, v);
            maxVertex = Math.max(maxVertex, Math.max(u, v));
        }

        // Сортируем списки смежности для лексикографического порядка
        for (List<Integer> neighbors : graph.adjList) {
            Collections.sort(neighbors);
        }

        // Выполняем топологическую сортировку
        List<Integer> sorted = graph.topologicalSort(maxVertex);

        // Выводим результат
        for (int i = 0; i < sorted.size(); i++) {
            System.out.print(sorted.get(i));
            if (i < sorted.size() - 1) {
                System.out.print(" ");
            }
        }
    }
}
