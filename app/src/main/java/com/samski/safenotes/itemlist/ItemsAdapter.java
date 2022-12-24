package com.samski.safenotes.itemlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.samski.safenotes.R;
import com.samski.safenotes.data.DataHandler;
import com.samski.safenotes.login.DataModel;
import com.samski.safenotes.itemlist.ItemsModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private DataHandler handler;
    private Context context;
    private ArrayList<ItemsModel> items = new ArrayList<>();

    public ItemsAdapter(Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.items_list,
                parent,
                false
        );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        InitializeItemPref();
        holder.userTxt.setText(items.get(position).getUserText());
//        holder.item.setCardBackgroundColor(items.get(position).getPreferedThemeColor());

//        TODO: CREATE ONCLICKLISTENER WHEN USER CLICKS ON A CARD, TO TEXTEDITOR
    }

    @Override
    public int getItemCount() {
//        TODO: count of items should be done through the sharedprefrence file userItemPref with key i = N
        return items.size();
    }

    public void setItems(ArrayList<ItemsModel> items) {

        this.items = items;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        private CardView item;
        private TextView userTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item);
            userTxt = itemView.findViewById(R.id.userTxt);
        }
    }

    public boolean InitializeItemPref() {

        try {
            handler = new DataHandler(this.context, DataHandler.USER_ITEMS_DATA_SHAREDPREF_KEY, DataHandler.ITEM_ARRAYLIST_TYPE);
//            TODO: CHECK IF ANY DATA PRESENT, IF YES ADD TO, IF NO INITIALIZE
            handler.writeToPreferences(items);
            return true;
        } catch (Exception e) {

            return false;
        }
    }
}
