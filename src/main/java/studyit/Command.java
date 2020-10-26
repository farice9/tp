package studyit;

import academic.Grade;
import academic.GradeBook;
import academic.Person;
import academic.PersonBook;
import academic.AcademicCommandType;

import academic.AcademicStorage;
import academic.AcademicCommandParser;
import bookmark.BookmarkParser;
import bookmark.BookmarkUi;
import academic.AcademicUi;

import exceptions.InvalidCommandException;
import exceptions.InvalidGradeException;
import exceptions.InvalidMcException;
import flashcard.FlashcardRun;
import timetable.TimeTableRun;
import userinterface.ErrorMessage;
import userinterface.HelpMessage;
import userinterface.Ui;

import java.io.IOException;

import java.util.ArrayList;
import bookmark.BookmarkRun;
import academic.AcademicRun;


public class Command {

    public static void executeCommand(String command, CommandType commandType,
                                      BookmarkRun bookmarkRun, FlashcardRun flashcardRun,
                                      TimeTableRun timeTableRun, AcademicRun academicRun) {
        if (commandType == CommandType.EXIT_PROGRAM) {
            Ui.printExit();
        } else if (commandType == CommandType.EXIT_MODE) {
            Ui.exitMode();
        } else if (commandType == CommandType.LOCATION) {
            Ui.printLocation();
        } else if (commandType == CommandType.CHANGE_MODE) {
            Ui.changeModeCommand(command);
        } else if (commandType == CommandType.HELP) {
            HelpMessage.printHelpMessage();
        } else if (commandType == CommandType.HIGHLIGHT) {
            Ui.printHighlight(bookmarkRun, academicRun);
        } else if (StudyIt.getCurrentMode() != Mode.MENU) {
            // Run the mode specific commands if the input is none of the general command
            handleNonGeneralCommand(command, commandType, bookmarkRun, flashcardRun, timeTableRun,
                    academicRun);
        } else {
            assert commandType == CommandType.UNIDENTIFIABLE : "This command should be unidentifiable";
            ErrorMessage.printUnidentifiableCommand();
            StudyItLog.logger.info("Cannot understand command input.");
        }
    }

    public static void handleNonGeneralCommand(String command, CommandType commandType,
                                               BookmarkRun bookmarkRun,
                                               FlashcardRun flashcardRun, TimeTableRun timeTableRun,
                                               AcademicRun academicRun) {
        Mode currentMode = StudyIt.getCurrentMode();
        if (currentMode == Mode.BOOKMARK) {
            executeBookmarkModeCommand(command, bookmarkRun);
        } else if (currentMode == Mode.TIMETABLE) {
            executeTimetableModeCommand(command, timeTableRun);
        } else if (currentMode == Mode.ACADEMIC) {
            executeAcademicModeCommand(command, academicRun);
        } else if (currentMode == Mode.FLASHCARD) {
            executeFlashcardCommand(command, flashcardRun);
        } else {
            assert currentMode == Mode.MENU : "The current mode should be at menu";
            StudyItLog.logger.severe("Mode is not handled properly.");
        }
    }

    public static void executeBookmarkModeCommand(String command, BookmarkRun bookmarkRun) {
        StudyItLog.logger.info("Processing bookmark mode.");
        bookmarkRun.run(command);
    }

    public static void executeTimetableModeCommand(String command, TimeTableRun timeTableRun) {
        StudyItLog.logger.info("Processing timetable mode.");
        timeTableRun.run(command);
    }

    public static void executeAcademicModeCommand(String command, AcademicRun academicRun) {
        StudyItLog.logger.info("Processing academic mode.");
        academicRun.run(command);
    }

    public static void executeFlashcardCommand(String command, FlashcardRun flashcardRun) {
        StudyItLog.logger.info("Processing flashcard mode.");
        flashcardRun.run(command);
    }
}