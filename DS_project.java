import java.util.InputMismatchException;
import java.util.Scanner;

// Node class to represent each event in the AVL Tree
class EventNode {
    int timestamp; // Event time (acts as a unique priority)
    String eventDetails;
    int height;
    EventNode left, right;

    public EventNode(int timestamp, String eventDetails) {
        this.timestamp = timestamp;
        this.eventDetails = eventDetails;
        this.height = 1;
    }
}

// AVL Tree class to manage event scheduling
class AVLTree {
    private EventNode root;

    // Utility function to get the height of the node
    private int height(EventNode N) {
        return (N == null) ? 0 : N.height;
    }

    // Right rotation for balancing the AVL tree
    private EventNode rightRotate(EventNode y) {
        EventNode x = y.left;
        EventNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x; // new root
    }

    // Left rotation for balancing the AVL tree
    private EventNode leftRotate(EventNode x) {
        EventNode y = x.right;
        EventNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y; // new root
    }

    // Get the balance factor of node N
    private int getBalance(EventNode N) {
        return (N == null) ? 0 : height(N.left) - height(N.right);
    }

    // Insert a new event by timestamp, with duplicate check
    public boolean insertEvent(int timestamp, String eventDetails) {
        if (searchEvent(timestamp) != null) {
            System.out.println("Error: An event with this timestamp already exists.");
            return false; // Duplicate timestamp found
        }
        root = insertEvent(root, timestamp, eventDetails);
        return true;
    }

    private EventNode insertEvent(EventNode node, int timestamp, String eventDetails) {
        if (node == null) {
            return new EventNode(timestamp, eventDetails);
        }

        if (timestamp < node.timestamp)
            node.left = insertEvent(node.left, timestamp, eventDetails);
        else if (timestamp > node.timestamp)
            node.right = insertEvent(node.right, timestamp, eventDetails);

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        if (balance > 1 && timestamp < node.left.timestamp)
            return rightRotate(node);

        if (balance < -1 && timestamp > node.right.timestamp)
            return leftRotate(node);

        if (balance > 1 && timestamp > node.left.timestamp) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && timestamp < node.right.timestamp) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Delete an event by timestamp
    public void deleteEvent(int timestamp) {
        if (timestamp <= 0) {
            System.out.println("Error: Timestamp must be a positive integer.");
            return;
        }
        if (searchEvent(root, timestamp) == null) {
            System.out.println("Error: No event found with this timestamp.");
            return;
        }
        root = deleteNode(root, timestamp);
        System.out.println("Event deleted successfully.");
    }

    private EventNode deleteNode(EventNode root, int timestamp) {
        if (root == null)
            return root;

        if (timestamp < root.timestamp)
            root.left = deleteNode(root.left, timestamp);
        else if (timestamp > root.timestamp)
            root.right = deleteNode(root.right, timestamp);
        else {
            if ((root.left == null) || (root.right == null)) {
                EventNode temp = (root.left != null) ? root.left : root.right;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else
                    root = temp;
            } else {
                EventNode temp = minValueNode(root.right);
                root.timestamp = temp.timestamp;
                root.eventDetails = temp.eventDetails;
                root.right = deleteNode(root.right, temp.timestamp);
            }
        }

        if (root == null)
            return root;

        root.height = Math.max(height(root.left), height(root.right)) + 1;
        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // Get the smallest timestamp event
    public EventNode getNextEvent() {
        return minValueNode(root);
    }

    private EventNode minValueNode(EventNode node) {
        EventNode current = node;
        while (current != null && current.left != null)
            current = current.left;
        return current;
    }

    // Search for an event by timestamp
    public EventNode searchEvent(int timestamp) {
        return searchEvent(root, timestamp);
    }

    private EventNode searchEvent(EventNode node, int timestamp) {
        if (node == null || node.timestamp == timestamp)
            return node;

        if (timestamp < node.timestamp)
            return searchEvent(node.left, timestamp);

        return searchEvent(node.right, timestamp);
    }

    // Print all events (in-order traversal)
    public void printEvents() {
        printEvents(root);
    }

    private void printEvents(EventNode node) {
        if (node != null) {
            printEvents(node.left);
            System.out.println("Event at " + node.timestamp + ": " + node.eventDetails);
            printEvents(node.right);
        }
    }
}

// Main class to test the AVL Tree based Event Scheduler
public class DS_project {
    public static void main(String[] args) {
        AVLTree eventTree = new AVLTree();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Event Scheduling System ---");
            System.out.println("1. Add an Event");
            System.out.println("2. Delete an Event");
            System.out.println("3. View Next Upcoming Event");
            System.out.println("4. Search for an Event");
            System.out.println("5. View All Events");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = -1;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine(); // Consume invalid input
                continue;
            }

            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter event timestamp (unique positive integer): ");
                        int timestamp = sc.nextInt();

                        if (timestamp <= 0) {
                            System.out.println("Error: Timestamp must be a positive integer greater than zero.");
                            break;
                        }

                        sc.nextLine(); // Consume newline
                        System.out.print("Enter event details: ");
                        String details = sc.nextLine();

                        if (!eventTree.insertEvent(timestamp, details)) {
                            System.out.println("Event with the same timestamp already exists. Event not added.");
                        } else {
                            System.out.println("Event added successfully.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid timestamp. Please enter a valid positive integer.");
                        sc.nextLine(); // Consume invalid input
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Enter the timestamp of the event to delete: ");
                        int deleteTimestamp = sc.nextInt();
                        eventTree.deleteEvent(deleteTimestamp);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid timestamp. Please enter a valid positive integer.");
                        sc.nextLine(); // Consume invalid input
                    }
                    break;

                case 3:
                    EventNode nextEvent = eventTree.getNextEvent();
                    if (nextEvent != null) {
                        System.out.println("Next upcoming event:");
                        System.out.println("Event at " + nextEvent.timestamp + ": " + nextEvent.eventDetails);
                    } else {
                        System.out.println("No events scheduled.");
                    }
                    break;

                case 4:
                    try {
                        System.out.print("Enter timestamp of the event to search: ");
                        int searchTimestamp = sc.nextInt();

                        if (searchTimestamp <= 0) {
                            System.out.println("Error: Timestamp must be a positive integer.");
                            break;
                        }

                        EventNode searchResult = eventTree.searchEvent(searchTimestamp);
                        if (searchResult != null) {
                            System.out.println("Event found: " + searchResult.eventDetails);
                        } else {
                            System.out.println("No event found with the given timestamp.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid timestamp. Please enter a valid positive integer.");
                        sc.nextLine(); // Consume invalid input
                    }
                    break;

                case 5:
                    System.out.println("All scheduled events:");
                    eventTree.printEvents();
                    break;

                case 6:
                    System.out.println("Exiting Event Scheduler. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option. Please choose a number between 1 and 6.");
            }
        }
    }
}
