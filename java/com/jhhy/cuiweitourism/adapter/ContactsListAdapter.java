package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.IContactsOperation;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.moudle.UserContacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiahe008 on 2016/9/20.
 */
public abstract class ContactsListAdapter extends MyBaseAdapter implements IContactsOperation {

    private List<Integer> listPosition = new ArrayList<>();
    private Drawable drawableRight;
    private Drawable drawableRightUncheck;

    public ContactsListAdapter(Context ct, List list) {
        super(ct, list);
        drawableRight = ContextCompat.getDrawable(context, R.drawable.check);
        drawableRightUncheck = ContextCompat.getDrawable(context, R.drawable.unchecked);
    }

    public void setData(List<UserContacts> listNew){
        list = listNew;
        notifyDataSetChanged();
    }

    public void addSelection(int selection){
//        if (listPosition.contains(selection)){
//            removeSelection(istPosition.childAt(selection));
//        } else {
            listPosition.add(selection);
            notifyDataSetChanged();
//        }
    }

    public void removeSelection(int selection){
        listPosition.remove(listPosition.indexOf(selection));
        notifyDataSetChanged();
    }

    public List<Integer> getSelection(){
        return listPosition;
    }

    public int getSelectionNumber(){
        return listPosition.size();
    }

    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        ContactsHolder holder = null;
        if (view == null){
            holder = new ContactsHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_custom_list, null);
            holder.tvName = (TextView) view.findViewById(R.id.tv_contacts_name);
            holder.ivLeft = (ImageView) view.findViewById(R.id.iv_edit_contacts);
            holder.ivRight = (ImageView) view.findViewById(R.id.iv_select_contacts);
            view.setTag(holder);
        }else{
            holder = (ContactsHolder) view.getTag();
        }

        holder.ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewContact(position);
            }
        });

        holder.ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshView(position);
            }
        });
        UserContacts contacts = (UserContacts) list.get(position);
        if (contacts != null){
            holder.tvName.setText(contacts.getContactsName());
        }
        if (listPosition.contains(position)){
//            holder.tvName.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableRight, null);
            holder.ivRight.setImageDrawable(drawableRight);
        }else{
            holder.ivRight.setImageDrawable(drawableRightUncheck);
//            holder.tvName.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableRightUncheck, null);
        }
        return view;
    }

    class ContactsHolder{
        private TextView tvName;
        private ImageView ivLeft;
        private ImageView ivRight;
    }

}
