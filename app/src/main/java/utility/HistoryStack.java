package utility;

import commands.Command;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class HistoryStack extends Stack {
    private static HistoryStack HISTORY = new HistoryStack();

    public static HistoryStack getInstance() {
        return HISTORY;
    }

    /**
     * Метод возвращает последние 10 команд
     */
    public synchronized ArrayList<Command> last10() {
        int len = size();
        if (len == 0)
            throw new EmptyStackException();
        ArrayList<Command> arrayList = new ArrayList<>();
        for (int i = 10; i > 0; i--) {
            if (len - i < 0) {
                continue;
            }
            arrayList.add((Command) elementAt(len - i));
        }
        return arrayList;
    }
}
