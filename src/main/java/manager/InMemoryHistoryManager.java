package manager;
import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        customLinkedList.removeNode(customLinkedList.nodesMap.get(task.getId()));
        customLinkedList.linkLast(task);
         customLinkedList.nodesMap.put(task.getId(), customLinkedList.tail);
    }

    @Override
    public void remove(int taskId) {
        customLinkedList.removeNode(customLinkedList.nodesMap.get(taskId));
    }

    @Override
    public List<Task> getHistory() {
        return customLinkedList.getTasks();
    }

    public static class CustomLinkedList<T> {
        final private Map<Integer, Node<T>> nodesMap = new HashMap<>();
        private Node<T> head;
        private Node<T> tail;

        private void linkLast(T task) {
            final Node<T> oldTail = tail;
            final Node<T> newNode = new Node<>(task, oldTail, null);
            tail = newNode;
            if (oldTail == null) {
                head = newNode;
            } else {
                oldTail.next = newNode;
            }
        }

        private void removeNode(Node<T> node) {
            if (node == null) {
                return;
            } else if (head == tail) {
                head = null;
                tail = null;
            } else if (node.prev == null) {
                head = node.next;
                node.next = null;
            } else if (node.next == null) {
                tail = node.prev;
                node.prev = null;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            if (nodesMap.containsValue(node.task)) {
                nodesMap.values().remove(node.task);
            }
        }

        private List<T> getTasks() {
            List<T> list = new ArrayList<>();

            for (Node<T> i = head; i != null; i = i.next) {
                list.add(i.task);
            }
            return list;
        }
    }
}
