package by.it.group351051.samsonova.lesson13;
import java.util.*;

public class GraphC {
    // Список смежности для исходного графа
    private List<List<Integer>> adjList;
    // Список смежности для транспонированного графа
    private List<List<Integer>> adjListTrans;
    // Отображение вершины (буквы) в индекс
    private Map<String, Integer> vertexToIndex;
    // Отображение индекса в вершину (букву)
    private Map<Integer, String> indexToVertex;
    // Стек для порядка завершения первого DFS
    private Stack<Integer> stack;
    // Множество посещённых вершин
    private Set<Integer> visited;

    // Конструктор
    public GraphC() {
        adjList = new ArrayList<>();
        adjListTrans = new ArrayList<>();
        vertexToIndex = new HashMap<>();
        indexToVertex = new HashMap<>();
        stack = new Stack<>();
        visited = new HashSet<>();
    }

    // Метод для добавления вершины
    private int addVertex(String v) {
        if (!vertexToIndex.containsKey(v)) {
            int index = vertexToIndex.size();
            vertexToIndex.put(v, index);
            indexToVertex.put(index, v);
            adjList.add(new ArrayList<>());
            adjListTrans.add(new ArrayList<>());
            return index;
        }
        return vertexToIndex.get(v);
    }

    // Метод для добавления ребра u -> v
    private void addEdge(String u, String v) {
        int uIndex = addVertex(u);
        int vIndex = addVertex(v);
        adjList.get(uIndex).add(vIndex);
        adjListTrans.get(vIndex).add(uIndex);
    }

    // Первый DFS для заполнения стека
    private void dfsFirst(int v) {
        visited.add(v);
        for (int neighbor : adjList.get(v)) {
            if (!visited.contains(neighbor)) {
                dfsFirst(neighbor);
            }
        }
        stack.push(v);
    }

    // Второй DFS для выделения SCC
    private void dfsSecond(int v, List<Integer> component) {
        visited.add(v);
        component.add(v);
        for (int neighbor : adjListTrans.get(v)) {
            if (!visited.contains(neighbor)) {
                dfsSecond(neighbor, component);
            }
        }
    }

    // Метод для нахождения компонент сильной связности
    private List<List<String>> findSCC() {
        // Первый DFS
        visited.clear();
        for (int v = 0; v < adjList.size(); v++) {
            if (!visited.contains(v)) {
                dfsFirst(v);
            }
        }

        // Второй DFS
        visited.clear();
        List<List<String>> sccs = new ArrayList<>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited.contains(v)) {
                List<Integer> component = new ArrayList<>();
                dfsSecond(v, component);
                // Преобразуем индексы в буквы и сортируем лексикографически
                List<String> scc = new ArrayList<>();
                for (int index : component) {
                    scc.add(indexToVertex.get(index));
                }
                Collections.sort(scc);
                sccs.add(scc);
            }
        }

        return sccs;
    }

    // Главный метод
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine(); // Считываем входную строку
        scanner.close();

        GraphC graph = new GraphC();

        // Парсим входную строку
        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] vertices = edge.split("->");
            String u = vertices[0];
            String v = vertices[1];
            graph.addEdge(u, v);
        }

        // Находим компоненты сильной связности
        List<List<String>> sccs = graph.findSCC();

        // Выводим компоненты
        for (List<String> scc : sccs) {
            for (int i = 0; i < scc.size(); i++) {
                System.out.print(scc.get(i));
            }
            System.out.println();
        }
    }
}