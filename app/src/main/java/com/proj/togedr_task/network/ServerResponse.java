package com.proj.togedr_task.network;

import com.proj.togedr_task.Contact;

import java.util.List;

/**
 * Created by karan on 23/4/17.
 */

public class ServerResponse {
    private List<Contact> contacts;

    public ServerResponse(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
