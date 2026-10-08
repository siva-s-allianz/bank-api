package com.siva.bank_api.model;

import java.util.ArrayList;
import java.util.List;

public class TransactionLinkedList {
    static class Node{
        BankTransaction data;
        Node next;

        Node(BankTransaction data){
            this.data = data;
            this.next = null;
        }
    }

    private Node head;

    public void add(BankTransaction transaction){
        Node newNode = new Node(transaction);


        if (head ==null){
            head =newNode;
            return;
        }

        Node current = head;

        while(current.next!=null){
            current =current.next;
        }
        current.next = newNode;
    }

    public void display(){
        Node current = head;

        while(current!=null){
            System.out.println(current.data);
            current=current.next;
        }
    }
    // Search for a transaction type
    public boolean search(String type) {

        Node current = head;

        while (current != null) {

            if (current.data.getType().equalsIgnoreCase(type)) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    public boolean delete(String type){
        if(head ==null){
            return false;
        }

        if(head.data.getType().equalsIgnoreCase(type)){
            head =head.next;
            return true;
        }

        Node current = head;

        while(current.next!=null && !current.next.data.getType().equalsIgnoreCase(type)){
            current =current.next;
        }
        if(current.next!=null){
            current.next =current.next.next;
            return true;
        }
        return false;
    }

    public List<BankTransaction> getTransactions() {

        List<BankTransaction> transactionList = new ArrayList<>();

        Node current = head;

        while (current != null) {
            transactionList.add(current.data);
            current = current.next;
        }

        return transactionList;
    }
    
}

