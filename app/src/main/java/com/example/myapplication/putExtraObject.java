package com.example.myapplication;

import java.io.Serializable;

public class putExtraObject  implements Serializable {
    User activeUser;
    Contact currentContact;

    public putExtraObject(User activeUser, Contact currentContact) {
        this.activeUser = activeUser;
        this.currentContact = currentContact;
    }

/*    public User getActiveUser() {
        return activeUser;
    }

    public Contact getCurrentContact() {
        return currentContact;
    }*/
}
