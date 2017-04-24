package com.proj.togedr_task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by stpl on 4/24/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<Contact> contacts;
    DataAdapter(List<Contact> contacts){
        this.contacts=contacts;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact=contacts.get(position);
        holder.email.setText(contact.getEmail());
        holder.gender.setText(contact.getGender());
        holder.email.setText(contact.getEmail());
        holder.home.setText(contact.getPhone().getHome());
        holder.office.setText(contact.getPhone().getOffice());
        holder.mobile.setText(contact.getPhone().getMobile());
        holder.id.setText(contact.getId());
        holder.address.setText(contact.getAddress());
        holder.name.setText(contact.getName());

    }

    @Override
    public int getItemCount() {
        if(contacts!=null){
            return contacts.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,address,home,mobile,office,id,gender,email;
        ViewHolder(View itemView) {
            super(itemView);
            name= $(itemView,R.id.name_value);
            address = $(itemView,R.id.address_value);
            home= $(itemView,R.id.home_value);
            mobile=$(itemView,R.id.mobile_value);
            office=$(itemView,R.id.office_value);
            id =$(itemView,R.id.id_value);
            gender=$(itemView,R.id.gender_value);
            email=$(itemView,R.id.email_value);
        }
    }
    private <T extends View> T $(View item,int id) {
        return (T) item.findViewById(id);
    }
}
