package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactsAdapterVh> {
    private List<Contact> contacts;
    private Context context;
    private SelectedContact selectedContact;

    public ContactAdapter(List<Contact> contacts, SelectedContact selectedContact) {
        this.contacts = contacts;
        this.selectedContact = selectedContact;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactsAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ContactsAdapterVh(LayoutInflater.from(context).inflate(R.layout.contact_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactsAdapterVh holder, int position) {
        Contact contact = contacts.get(position);
        String tv_name = contact.getName();
        String tv_last = contact.getLast();
        String tv_date = contact.getLastDate();
        holder.tv_name.setText(tv_name);
        holder.tv_last.setText(tv_last);
        holder.tv_date.setText(tv_date);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public interface SelectedContact{
        void selectedContact(Contact contact);
    }

    public class ContactsAdapterVh extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_last;
        TextView tv_date;
        public ContactsAdapterVh(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_last = itemView.findViewById(R.id.tv_last);
            tv_date = itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    selectedContact.selectedContact(contacts.get(getAdapterPosition()));
                }
            });
        }
    }
}
