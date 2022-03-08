package commands;

import exception.CommandNotAcceptArgumentsException;
import invokers.CommandManager;

/**
 * Команда, показывающая 10 последних команд
 */
public class HistoryCommand extends AbstractCommand{
    public HistoryCommand(){
        super("history","вывести последние 10 команд (без их аргументов)");
    }
//TODO
    @Override
    public void execute(String argument) {
        try {
            if(!argument.isEmpty()) throw new CommandNotAcceptArgumentsException();
            CommandManager.printLast10Commands();
        }
        catch(CommandNotAcceptArgumentsException e){
            e.printStackTrace();
        }
    }
}
