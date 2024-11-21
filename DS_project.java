import java.util.InputMismatchException;
import java.util.Scanner;

class EventNode {
    int time_stamp; // Event time
    String event_Detail;
    int h;
    EventNode l, r;

    public EventNode(int time_stamp, String event_Detail) {
        this.time_stamp = time_stamp;
        this.event_Detail = event_Detail;
        this.h = 1;
    }
}

class AVLTree {
    private EventNode root;

    // utility function
    private int h(EventNode N) {
        return (N == null) ? 0 : N.h;
    }

    private EventNode rRotate(EventNode y) {
        EventNode x = y.l;
        EventNode T2 = x.r;

        // Perform rotation
        x.r = y;
        y.l = T2;

        // Update hs
        y.h = Math.max(h(y.l), h(y.r)) + 1;
        x.h = Math.max(h(x.l), h(x.r)) + 1;

        return x;
    }

    private EventNode lRotate(EventNode x) {
        EventNode y = x.r;
        EventNode T2 = y.l;

        y.l = x;
        x.r = T2;

        x.h = Math.max(h(x.l), h(x.r)) + 1;
        y.h = Math.max(h(y.l), h(y.r)) + 1;

        return y;
    }

    private int get_bal(EventNode N) {
        return (N == null) ? 0 : h(N.l) - h(N.r);
    }

    public boolean insertEvent(int time_stamp, String event_Detail) {
        if (searchEvent(time_stamp) != null) {
            System.out.println("Error: An event with this time value already Exists.");
            return false;
        }
        root = insertEvent(root, time_stamp, event_Detail);
        return true;
    }

    private EventNode insertEvent(EventNode node, int time_stamp, String event_Detail) {
        if (node == null) {
            return new EventNode(time_stamp, event_Detail);
        }

        if (time_stamp < node.time_stamp)
            node.l = insertEvent(node.l, time_stamp, event_Detail);
        else if (time_stamp > node.time_stamp)
            node.r = insertEvent(node.r, time_stamp, event_Detail);

        node.h = 1 + Math.max(h(node.l), h(node.r));
        int bal = get_bal(node);

        if (bal > 1 && time_stamp < node.l.time_stamp)
            return rRotate(node);

        if (bal < -1 && time_stamp > node.r.time_stamp)
            return lRotate(node);

        if (bal > 1 && time_stamp > node.l.time_stamp) {
            node.l = lRotate(node.l);
            return rRotate(node);
        }

        if (bal < -1 && time_stamp < node.r.time_stamp) {
            node.r = rRotate(node.r);
            return lRotate(node);
        }

        return node;
    }

    public void deleteEvent(int time_stamp) {
        if (time_stamp <= 0) {
            System.out.println("Error: Time Value must be a positive Integer.");
            return;
        }
        if (searchEvent(root, time_stamp) == null) {
            System.out.println("Error: No event found with this time Value.");
            return;
        }
        root = deleteNode(root, time_stamp);
        System.out.println("Event deleted successfully.");
    }

    private EventNode deleteNode(EventNode root, int time_stamp) {
        if (root == null)
            return root;

        if (time_stamp < root.time_stamp)
            root.l = deleteNode(root.l, time_stamp);
        else if (time_stamp > root.time_stamp)
            root.r = deleteNode(root.r, time_stamp);
        else {
            if ((root.l == null) || (root.r == null)) {
                EventNode temp = (root.l != null) ? root.l : root.r;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else
                    root = temp;
            } else {
                EventNode temp = minValueNode(root.r);
                root.time_stamp = temp.time_stamp;
                root.event_Detail = temp.event_Detail;
                root.r = deleteNode(root.r, temp.time_stamp);
            }
        }

        if (root == null)
            return root;

        root.h = Math.max(h(root.l), h(root.r)) + 1;
        int bal = get_bal(root);

        if (bal > 1 && get_bal(root.l) >= 0)
            return rRotate(root);

        if (bal > 1 && get_bal(root.l) < 0) {
            root.l = lRotate(root.l);
            return rRotate(root);
        }

        if (bal < -1 && get_bal(root.r) <= 0)
            return lRotate(root);

        if (bal < -1 && get_bal(root.r) > 0) {
            root.r = rRotate(root.r);
            return lRotate(root);
        }

        return root;
    }

    public EventNode getNextEvent() {
        return minValueNode(root);
    }

    private EventNode minValueNode(EventNode node) {
        EventNode current = node;
        while (current != null && current.l != null)
            current = current.l;
        return current;
    }

    public EventNode searchEvent(int time_stamp) {
        return searchEvent(root, time_stamp);
    }

    private EventNode searchEvent(EventNode node, int time_stamp) {
        if (node == null || node.time_stamp == time_stamp)
            return node;

        if (time_stamp < node.time_stamp)
            return searchEvent(node.l, time_stamp);

        return searchEvent(node.r, time_stamp);
    }

    public void printEvents() {
        printEvents(root);
    }

    private void printEvents(EventNode node) { //INFO: inOrder
        if (node != null) {
            printEvents(node.l);
            System.out.println("Event at " + node.time_stamp + ": " + node.event_Detail);
            printEvents(node.r);
        }
    }
}

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
                sc.nextLine();
                continue;
            }

            sc.nextLine();

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter Event Time_stamp (Unique Positive Integer): ");
                        int time_stamp = sc.nextInt();

                        if (time_stamp <= 0) {
                            System.out.println("Error: Time_stamp must be a positive integer greater than zero.");
                            break;
                        }

                        sc.nextLine();
                        System.out.print("Enter event details: ");
                        String details = sc.nextLine();

                        if (!eventTree.insertEvent(time_stamp, details)) {
                            System.out.println("Event with the same time_stamp already exists. Event not added.");
                        } else {
                            System.out.println("Event added successfully.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid time_stamp. Please enter a valid positive integer.");
                        sc.nextLine();
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Enter the time_stamp of the event to be Delete: ");
                        int deleteTime_stamp = sc.nextInt();
                        eventTree.deleteEvent(deleteTime_stamp);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid time_stamp. Please enter a valid positive integer.");
                        sc.nextLine();
                    }
                    break;

                case 3:
                    EventNode nextEvent = eventTree.getNextEvent();
                    if (nextEvent != null) {
                        System.out.println("Next upcoming event:");
                        System.out.println("Event at " + nextEvent.time_stamp + ": " + nextEvent.event_Detail);
                    } else {
                        System.out.println("No events scheduled.");
                    }
                    break;

                case 4:
                    try {
                        System.out.print("Enter time_stamp of the Event to Search: ");
                        int searchTime_stamp = sc.nextInt();

                        if (searchTime_stamp <= 0) {
                            System.out.println("Error: Time Value must be a positive integer.");
                            break;
                        }

                        EventNode searchResult = eventTree.searchEvent(searchTime_stamp);
                        if (searchResult != null) {
                            System.out.println("Event found: " + searchResult.event_Detail);
                        } else {
                            System.out.println("No event found with the given time_stamp.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid time_stamp. Please enter a valid positive integer.");
                        sc.nextLine();
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
