package storage;

import data.exception.DukeInvalidUserInputException;
import data.task.Task;
import data.task.TaskList;
import ui.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {

    private String filepath;

    public Storage(String filepath) {
        this.filepath = filepath;
    }

    public void loadTaskList(TaskList taskList) throws DukeInvalidUserInputException {
        File saveFile = new File(this.filepath);
        try {
            Scanner s = new Scanner(saveFile);
            while (s.hasNext()) {
                Task toAdd = Task.parse(s.nextLine());
                taskList.load(toAdd);
            }
        } catch (FileNotFoundException e) {
            //can be ignored as if file is not found it will just be created when saving data
        }
    }

    public void saveTask(Task task) {
        File saveFile = new File(this.filepath);
        try {
            saveFile.getParentFile().mkdirs(); //to create directory 'data'
            saveFile.createNewFile(); //to create duke.txt file
            //Check to see whether duke.txt file exists
            if (saveFile.length() > 0) {
                FileWriter toSave = new FileWriter(saveFile, true);
                toSave.write(System.lineSeparator() + task.toTxtFormat());
                toSave.close();
            } else {
                FileWriter toSave = new FileWriter(saveFile);
                toSave.write(task.toTxtFormat());
                toSave.close();
            }
        } catch (IOException e) {
            Ui.showUnknownError();;
        }
    }

    public void saveTaskList(TaskList taskList) {
        try {
            FileWriter overwriteFile = new FileWriter(this.filepath);
            if (taskList.getTotalTask() > 0) {
                overwriteFile.write(taskList.getTask(0).toTxtFormat());
                overwriteFile.close();
                for (int i = 1 ; i < taskList.getTaskList().size() ; i++) {
                    saveTask(taskList.getTask(i));
                }
            } else {
                overwriteFile.write("");
                overwriteFile.close();
            }
        } catch (IOException e) {
            Ui.showUnknownError();;
        }
    }
}