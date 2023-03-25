package com.blooddonationapp;

import android.util.Log;

import java.io.Serializable;
import java.util.LinkedList;

class Node {
    String userName,contactNo,bloodType,email,password;

    Node next;

    Node(String name,String contactNo,String type,String email,String password) {
        this.userName=name;
        this.contactNo=contactNo;
        this.bloodType = type;
        this.email=email;
        this.password=password;

    }
}

class CustomLinkedList  {
    Node head;

    public void insert(String name,String contactNo,String type,String email,String password) {
        Log.e("inside ","insert");
        Node newNode = new Node(name,contactNo,type,email,password);
        if(head == null) {
            head = newNode;
        }else {
            Node currentNode = head;
            while(currentNode.next != null) {
                currentNode= currentNode.next;
            }
            currentNode.next = newNode;
        }
    }

//    void update(Node head1,String name,String contactNo,String type,String email, String...newData)
//    {
//        Node current = head;
//
//        if (head == null)
//            return;
//
//        System.out.println("head in update: "+head.donorName);
//
//        while (current != null)
//        {
//            if(current.donorName == name){
//                System.out.println("In Update head "+current.donorName);
//                current.donorName=newName;
//                System.out.println("In Update head "+current.donorName);
//                return;
//            }
//            current=current.next;
//        }
//    }

    void deleteNode(Node head1, String username1) {

        Node current = head;
        if (head == null)
            return;

        if(head1.userName == username1){
            System.out.println("In delete head "+head.userName);
            head=head1.next;
            return;
        }

        while (current != null)
        {

            if(current.next==null)
            {
                Node temp = head;
                while(temp.next.next != null) {
                    temp = temp.next;
                    temp.next=null;
                    return;
                }
            }
            else if(current.next.userName == username1)
            {
                Node next = current.next.next;
                current.next=next;
                return;
            }
            current = current.next;
        }

    }
    public String show() {
        Node currentNode = head;
        if(currentNode == null){
            System.out.println("Linked list is empty");
        }
        else {
            while(currentNode != null) {
                Log.e("Data",currentNode.userName + " "+currentNode.contactNo+" "
                        +currentNode.bloodType+" "+currentNode.email+" "+currentNode.password);

//                Log.e(currentNode.userName+" 1 ",currentNode.next.userName+" 2");
                currentNode = currentNode.next;
            }
        }
        return null;
    }
}
