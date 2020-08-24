package parser;

import commands.*;
import data.exception.DukeIllegalCommandException;
import data.task.TaskList;
import storage.Storage;
import ui.Ui;

public class Parser {

    private TaskList taskList;
    private Storage storage;
    private Ui ui;

    public Parser(TaskList taskList, Storage storage, Ui ui) {
        this.taskList = taskList;
        this.storage = storage;
        this.ui = ui;
    }

    public Command parseCommand(String user_input) throws DukeIllegalCommandException {
        String[] user_inputArr = user_input.split(" ");
        String keyword = user_inputArr[0];
        switch (keyword) {
            case "list":
                return new ListCommand(this.ui, this.taskList);
            case "done":
                return new DoneCommand(this.taskList, this.storage, user_input);
            case "bye":
                return new ByeCommand(this.ui);
            case "todo":
                return new ToDoCommand(this.taskList, this.storage, this.ui, user_input);
            case "event":
                return new EventCommand(this.taskList, this.storage, this.ui, user_input);
            case "deadline":
                return new DeadlineCommand(this.taskList, this.storage, this.ui, user_input);
            case "delete":
                return new DeleteCommand(this.taskList, this.storage, this.ui, user_input);
            default:
                throw new DukeIllegalCommandException(keyword);
        }
    }

    public static String parseFollowUpCommand(String user_input) {
        String[] user_inputArr = user_input.split(" ");
        return user_inputArr[0];
    }


}
