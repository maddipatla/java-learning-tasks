package com.learningbydoing.lru;

/**
 * @author Maddipatla Chandra Babu
 * @date 07-Feb-2018
 */
public class LRUCache {
    private Integer noOfPages;
    private Node front;
    private Node rear;
    private Integer noOfHits = 0;
    private Integer listSize = 0;

    /**
     * @param noOfPages
     */
    public LRUCache(Integer noOfPages) {
        this.noOfPages = noOfPages;
    }

    /**
     * @param key
     * @return Employee
     */
    public Employee getEmployee(Integer key) {
        Employee employee;
        if (front != null && listSize < noOfPages) {
            Node existingNode = getNodeExists(key);
            if (existingNode == null) {
                employee = getEmployeeFromDB(key);
                Node node = new Node(null, front, employee);
                front.setPrev(node);
                front = node;
            } else {
                processExistingNode(existingNode);
                employee = existingNode.getEmployee();
                noOfHits++;
            }
        } else if (front == null) {
            employee = getEmployeeFromDB(key);
            Node node = new Node(null, null, employee);
            front = rear = node;
        } else {
            Node existingNode = getNodeExists(key);
            if (existingNode == null)
                employee = processNonExistNode(key);
            else {
                processExistingNode(existingNode);
                employee = existingNode.getEmployee();
                noOfHits++;
            }
        }
        listSize++;
        return employee;
    }

    /**
     * @param key
     * @return Node
     */
    private Node getNodeExists(Integer key) {
        Node node = front;
        while (node != null) {
            if (node.getEmployee().getId() == key)
                return node;
            node = node.getNext();
        }
        return null;
    }

    /**
     * @param existingNode
     */
    private void processExistingNode(Node existingNode) {
        if (existingNode == rear && existingNode != front) {
            rear = rear.getPrev();
            rear.setNext(null);
            existingNode.setPrev(null);
            existingNode.setNext(front);
            front.setPrev(existingNode);
            front = existingNode;
        } else if (existingNode != front) {
            existingNode.getPrev().setNext(existingNode.getNext());
            existingNode.getNext().setPrev(existingNode.getPrev());
            existingNode.setPrev(null);
            existingNode.setNext(front);
            front.setPrev(existingNode);
            front = existingNode;
        }
    }

    /**
     * @param key
     * @return Employee
     */
    private Employee processNonExistNode(Integer key) {
        Employee employee = getEmployeeFromDB(key);
        Node node = new Node(null, front, employee);
        front.setPrev(node);
        front = node;
        rear = rear.getPrev();
        rear.setNext(null);
        return employee;
    }

    /**
     * @param key
     * @return Employee
     */
    private Employee getEmployeeFromDB(Integer key) {
        return new Employee(key, "Employee-" + key, Double.valueOf(key * 5d));
    }
}
