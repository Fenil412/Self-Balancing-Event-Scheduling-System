# Event Scheduler with AVL Tree

This project is an **Event Scheduling System** that leverages an **AVL Tree** data structure for managing and scheduling events. The AVL Tree maintains events in a balanced structure, allowing efficient operations for inserting, deleting, and searching events by timestamp.

## Features

- **Add Event**: Schedule an event with a unique timestamp.
- **Delete Event**: Remove an event by timestamp.
- **Search Event**: Find an event by timestamp.
- **View Next Event**: Retrieve the next upcoming event (smallest timestamp).
- **View All Events**: Display all scheduled events in sorted order.

## AVL Tree Functionality

The AVL Tree ensures:
- **Self-balancing** after every insertion and deletion.
- **Efficient Searching** by timestamp.
- **O(log n)** complexity for insertion, deletion, and search operations.

## Error Handling

- **Invalid Timestamps**: Only positive integer timestamps are accepted.
- **Duplicate Timestamps**: Prevents adding events with the same timestamp.
- **Non-Existent Events**: Handles attempts to delete or search for non-existent events.

## Usage

1. Clone the repository and compile the Java files.
2. Run the `DS_project` class as the main program.

### Example Commands

After running the program, use the following options to interact with the scheduler:
- **1**: Add an Event (Enter a unique positive timestamp and event details).
- **2**: Delete an Event by timestamp.
- **3**: View the next upcoming event.
- **4**: Search for a specific event by timestamp.
- **5**: View all scheduled events.
- **6**: Exit the application.

## Code Structure

### Main Classes

- **EventNode**: Represents each event with properties `timestamp`, `eventDetails`, `height`, `left`, and `right`.
- **AVLTree**: Manages AVL tree operations such as insertion, deletion, rotation, and balancing.
- **DS_project**: Main program that includes the menu interface and interaction logic.

### Methods Overview

- **insertEvent(int timestamp, String eventDetails)**: Inserts an event with the specified timestamp and details.
- **deleteEvent(int timestamp)**: Deletes an event with the given timestamp.
- **getNextEvent()**: Returns the next event by timestamp.
- **searchEvent(int timestamp)**: Searches and returns an event by timestamp.
- **printEvents()**: Displays all events in sorted order.

## Requirements

- **Java JDK 8 or above**

## Setup Instructions

1. Clone the repository:

    ```bash
    git clone https://github.com/yourusername/AVLTree-Event-Scheduler.git
    cd AVLTree-Event-Scheduler
    ```

2. Compile and run:

    ```bash
    javac DS_project.java
    java DS_project
    ```

## Example Usage

1. Start the program and choose **1** to add an event:
    - Enter `timestamp: 15`
    - Enter `eventDetails: Meeting with team`
    
2. Choose **4** to view all events, showing the scheduled event with timestamp `15`.

3. Choose **3** to view the next event, which returns the event at `timestamp 15`.

4. Choose **2** to delete an event by timestamp. Enter `15` to delete the event.

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests to improve the code, add features, or fix bugs.

## License

This project is open-source and available under the [MIT License](LICENSE).
