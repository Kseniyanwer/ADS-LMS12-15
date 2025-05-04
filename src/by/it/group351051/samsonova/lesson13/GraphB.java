package by.it.group351051.samsonova.lesson13;

import java.util.*;

public class GraphB {
    // Список смежности для представления графа
    private List<List<Integer>> adjList;

    // Конструктор
    public GraphB() {
        adjList = new ArrayList<>();
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

    // DFS для обнаружения цикла
    private boolean hasCycle(int v, Set<Integer> visited, Set<Integer> recStack) {
        // Добавляем вершину в посещённые и в текущий путь
        visited.add(v);
        recStack.add(v);

        // Обрабатываем всех соседей
        for (int neighbor : adjList.get(v)) {
            // Если сосед ещё не посещён, рекурсивно проверяем его
            if (!visited.contains(neighbor)) {
                if (hasCycle(neighbor, visited, recStack)) {
                    return true;
                }
            }
            // Если сосед уже в текущем пути, найден цикл
            else if (recStack.contains(neighbor)) {
                return true;
            }
        }

        // Убираем вершину из текущего пути после обработки
        recStack.remove(v);
        return false;
    }

    // Метод для проверки наличия цикла в графе
    private boolean detectCycle() {
        Set<Integer> visited = new HashSet<>();
        Set<Integer> recStack = new HashSet<>();

        // Проверяем каждую вершину (на случай несвязного графа)
        for (int v = 0; v < adjList.size(); v++) {
            if (!visited.contains(v)) {
                if (hasCycle(v, visited, recStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Главный метод
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine(); // Считываем входную строку
        scanner.close();

        GraphB graph = new GraphB();

        // Парсим входную строку
        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] vertices = edge.split(" -> ");
            int u = Integer.parseInt(vertices[0]);
            int v = Integer.parseInt(vertices[1]);
            graph.addEdge(u, v);
        }

        // Проверяем наличие цикла и выводим результат
        System.out.println(graph.detectCycle() ? "yes" : "no");
    }
}