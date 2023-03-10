import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Item> items = Arrays.asList(
                new Item(2, 2),
                new Item(3, 3),
                new Item(4, 4),
                new Item(5, 5),
                new Item(6, 6),
                new Item(7, 7),
                new Item(8, 8),
                new Item(9, 9));

        System.out.println("\nОтвет: " + calculate(items, 20) + " денег");
    }

    public static int calculate(List<Item> items, int capacity) {
        Table<Integer> table = new Table<>(capacity + 1, items.size() + 1, -1);
        System.out.println("\nИзначальная таблица без значений:");
        System.out.println(table);

        for (int i = 0; i <= items.size(); ++i) {
            for (int backpack = 0; backpack <= capacity; ++backpack) {

                if ((i == 0) || (backpack == 0)) {
                    table.set(backpack, i, 0);
                } else if (items.get(i - 1).weight() > backpack) {
                    table.set(backpack, i, table.get(backpack, i - 1));
                } else {
                    table.set(backpack, i, Math.max(
                            table.get(backpack, i - 1),
                            items.get(i - 1).price() + table.get(backpack - items.get(i - 1).weight(), i - 1)
                    ));
                }

                System.out.println("\nОчередная таблица:");
                System.out.println(table);
            }
        }

        List<Integer> counts = new ArrayList<>();
        for (Item ignored : items) {
            counts.add(0);
        }
        int x = capacity;
        int y = items.size();
        System.out.println("Обратный оптимальный путь: ");
        while (table.get(x, y) > 0) {
            if (items.get(y - 1).weight() > x) {
                --y;
            } else if (table.get(x, y - 1) > items.get(y - 1).price() + table.get(x - items.get(y - 1).weight(), y - 1)) {
                --y;
            } else {
                x -= items.get(y - 1).weight();
                counts.set(y - 1, counts.get(y - 1) + 1);
                --y;
            }
        }
        System.out.println("\nИтого нужно элементов: ");
        System.out.println(counts);

        return table.get(capacity, items.size());
    }
}