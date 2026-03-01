# BMO User Guide

![](Ui.png)

Have you been 
* struggling to do your... todos? 
* missing deadlines left and right? 
* forgetting to show up for events?

If this is you, fret not. BMO is here to handle your endless list of tasks for you!

BMO is a desktop app for managing tasks, optimized for use via a Command Line Interface (CLI), while still having benefits of a Graphical User Interface (GUI). If you can type fast, BMO can save you time, effort and give you a peace of mind as you smash through your list of tasks!

* Quick start
* Features
  * Adding a todo task: `todo`
  * Adding a deadline task: `deadline`
  * Adding an event task: `event`
  * Listing all tasks: `list`
  * Finding a task by keyword: `find`
  * Mark a task as done: `mark`
  * Mark a task as not done: `unmark`
  * Delete a task: `delete`
  * Exiting the program: `bye`
  * List command summary: any other command word
  * Saving the file
  * Editing the data file
* FAQ
* Known issues
* Command summary

## Quick start

1. Ensure you have Java `17` or above installed in your laptop or PC.
**Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).


2. Download the latest `.jar` file from [here](https://github.com/ram-nush/ip/releases).


3. Copy the file to the folder you want to use as the home folder for your BMO chatbot.


4. On a file manager app, navigate to the folder you put the jar file in. Right-click and select an option which resembles *'Open in Terminal'*.


5. Use the `java -jar bmo.jar` command to run the application. A GUI similar to the one below should appear in a few seconds.


![](first_use.png)

Notice how the app mentions that there are issues loading from file. The reason is that there is no save file yet. This is expected during your first use of the app.

6. Type the command in the command box (bottom-left) and press Enter to execute it. e.g. typing `list` and pressing Enter will show the current list of tasks.

   Some example commands you can try:
  * `list`: Lists all tasks.
  * `todo read book`: Adds a todo task named `read book` to the list of tasks.
  * `mark 2`: marks the 2nd task in the list as done.
  * `bye`: saves the current list of tasks and exits the app.

7. Refer to the [Features](#features) below for details of each command.

---

## Features

> [!NOTE]
> Notes about the command format:
> * Words in `<>` are the parameters to be supplied by the user.
>
> e.g. in `todo <description>`, `description` is a parameter which can be used as in `todo read book`.
> 
> * Extraneous parameters for commands that do not take in parameters (such as `list`, `bye`) will be ignored.
> 
> e.g. if the command specifies `list 123`, it will be interpreted as `list`.

### Adding a todo task: `todo`

You can add tasks with no other information, using the `todo` command. You just need the task description.

Format: `todo <description>`

Example: `todo read book`

BMO will add the deadline task to its list, and inform you that it has added the task, along with the number of tasks you have now.

```
Another task? I'll make space for this task:
[T][ ] read book
Now you have 1 tasks.
```

### Adding a deadline task: `deadline`

You can also add tasks which have deadlines, using the `deadline` command. You would need the task description as well as the date and time the task is due.

The date and time need to be in a standard datetime format: `d-M-uuuu HHmm`. <br>
Here are some examples of datetimes which match the input format:
* `5-3-2026 1500`
* `06-03-2026 2300`
* `12-9-2026 0000`

Format: `deadline <description> /by <due>`

Example: `deadline clean house /by 5-3-2026 1800`

BMO will add the deadline task to its list, and inform you that it has added the task, along with the number of tasks you have now.

```
Another task? I'll make space for this task:
[D][ ] clean house (by: Mar 5 2026 1800)
Now you have 2 tasks.
```

Notice that the datetime is displayed in a different datetime format. This is for readability.

### Adding an event task: `event`

Additionally, you can add tasks which have a start and an end, using the `event` command. You would need the task description, the date and time the task starts and ends.

Similar to deadline tasks, the date and time need to be in the same datetime format: `d-M-uuuu HHmm`.

Format: `event <description> /from <start> /to <end>`

Example: `event meet friends /from 3-3-2026 1800 /to 4-3-2026 0400`

BMO will add the event task to its list, and inform you that it has added the task, along with the number of tasks you have now.

```
Another task? I'll make space for this task:
[E][ ] meet friends (from: Mar 3 2026 1800, to: Mar 4 2026 0400)
Now you have 3 tasks.
```

> [!TIP]
> Experienced users may omit the spaces between parameters.
>
> e.g. if the command specifies `deadline clean house/by5-3-2026 1800`,
> it is interpreted as `deadline clean house /by 5-3-2026 1800`

## Feature ABC

// Describe the action and its outcome.

<>

// Give examples of usage

Format : ``
Example: ``

// A description of the expected outcome goes here

<>

```
<>
```

## Feature XYZ

// Feature details
