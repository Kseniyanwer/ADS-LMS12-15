package by.it.group351051.вырко.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: расстояние Левенштейна
    https://ru.wikipedia.org/wiki/Расстояние_Левенштейна
    http://planetcalc.ru/1721/

Дано:
    Две данных непустые строки длины не более 100, содержащие строчные буквы латинского алфавита.

Необходимо:
    Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ
    Итерационно вычислить алгоритм преобразования двух данных непустых строк
    Вывести через запятую редакционное предписание в формате:
     операция("+" вставка, "-" удаление, "~" замена, "#" копирование)
     символ замены или вставки

    Sample Input 1:
    ab
    ab
    Sample Output 1:
    #,#,

    Sample Input 2:
    short
    ports
    Sample Output 2:
    -s,~p,#,#,#,+s,

    Sample Input 3:
    distance
    editing
    Sample Output 2:
    +e,#,#,-s,#,~i,#,-c,~g,


    P.S. В литературе обычно действия редакционных предписаний обозначаются так:
    - D (англ. delete) — удалить,
    + I (англ. insert) — вставить,
    ~ R (replace) — заменить,
    # M (match) — совпадение.
*/


public class C_EditDist {

    String getDistanceEdinting(String one, String two) {
        int len1 = one.length();
        int len2 = two.length();


        int[][] dp = new int[len1 + 1][len2 + 1];


        String[][] ops = new String[len1 + 1][len2 + 1];

        // Инициализируем таблицу
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
            ops[i][0] = "-" + (i > 0 ? one.charAt(i - 1) : "") + ","; // Delete operations
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
            ops[0][j] = "+" + (j > 0 ? two.charAt(j - 1) : "") + ","; // Insert operations
        }

        // Заполняем таблицу
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                    ops[i][j] = "#,"; // Match operation
                } else {
                    // Вычисление
                    int insert = dp[i][j - 1] + 1;
                    int delete = dp[i - 1][j] + 1;
                    int replace = dp[i - 1][j - 1] + 1;

                    if (insert <= delete && insert <= replace) {
                        dp[i][j] = insert;
                        ops[i][j] = "+" + two.charAt(j - 1) + ","; // Вставка
                    } else if (delete <= insert && delete <= replace) {
                        dp[i][j] = delete;
                        ops[i][j] = "-" + one.charAt(i - 1) + ","; // Удаление
                    } else {
                        dp[i][j] = replace;
                        ops[i][j] = "~" + two.charAt(j - 1) + ","; // Замена
                    }
                }
            }
        }


        StringBuilder result = new StringBuilder();
        int i = len1;
        int j = len2;

        while (i > 0 || j > 0) {
            result.insert(0, ops[i][j]);
            if (i > 0 && j > 0 && dp[i][j] == dp[i - 1][j - 1] && one.charAt(i - 1) == two.charAt(j - 1)) {
                i--;
                j--;
            } else if (j > 0 && dp[i][j] == dp[i][j - 1] + 1) {
                j--;
            } else if (i > 0 && dp[i][j] == dp[i - 1][j] + 1) {
                i--;
            } else {
                i--;
                j--;
            }
        }

        return result.toString();
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson07/dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}