package com.samski.safenotes.itemlist;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.samski.safenotes.EditorActivity;
import com.samski.safenotes.MainActivityAfterLogin;
import com.samski.safenotes.R;
import com.samski.safenotes.colorsView.ColorModel;
import com.samski.safenotes.data.DataHandler;
import com.samski.safenotes.font.FontModel;
import com.samski.safenotes.login.DataModel;
import com.samski.safenotes.itemlist.ItemsModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private DataHandler handler;
    private Context context;
    public static ArrayList<ItemsModel> items = new ArrayList<>();
    private int TEXTLENGTHLIMIT = 400;
    public static final String FLAG_SET_ONE = "set one";
    public static final String FLAG_SET_RANGE = "set range";
    public static int currentItemPosition;

    public ItemsAdapter(Context context) {

        this.context = context;
        handler = new DataHandler(this.context, DataHandler.USER_ITEMS_DATA_SHAREDPREF_KEY, DataHandler.ITEM_ARRAYLIST_TYPE);
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
        String userTxt = items.get(position).getUserText();

        holder.userTxt.setText(userTxt.length() <= TEXTLENGTHLIMIT ? userTxt :
                userTxt.substring(0, TEXTLENGTHLIMIT));

        holder.textTitle.setText(items.get(position).getUserTitle());

        holder.item.setOnLongClickListener(view -> {

            deleteItem(position);
            if (items.size() == 0) {
                MainActivityAfterLogin.addNewTxt.setVisibility(View.VISIBLE);
            }
            return true;
        });

        holder.item.setOnClickListener(view -> {

//            take user to editor
            currentItemPosition = holder.getAdapterPosition();
            context.startActivity(new Intent(context, EditorActivity.class));
        });

        Resources resources = context.getResources();
        int color = resources.getColor(ColorModel.getColor(items.get(position).getPreferedThemeColor()),
                context.getTheme());
        holder.item.setCardBackgroundColor(resources.getColor
                (ColorModel.getColor(items.get(position).getPreferedThemeColor()), context.getTheme()));

        int fontId = FontModel.getFont(items.get(position).getFont());
        if (fontId != 0) {

            holder.userTxt.setTypeface(context.getResources().getFont(fontId));
        }

        if (Objects.equals(color, resources.getColor(R.color.white, context.getTheme()))) {

            holder.userTxt.setTextColor(resources.getColor(R.color.themeDarkVariant1, context.getTheme()));
            holder.userTxt.setHintTextColor(resources.getColor(R.color.themeDarkVariant1, context.getTheme()));
            holder.textTitle.setTextColor(resources.getColor(R.color.themeDarkVariant1, context.getTheme()));
        } else if (Objects.equals(color, resources.getColor(R.color.themeDarkVariant1, context.getTheme()))) {

            holder.userTxt.setTextColor(resources.getColor(R.color.white, context.getTheme()));
            holder.userTxt.setHintTextColor(resources.getColor(R.color.white, context.getTheme()));
            holder.textTitle.setTextColor(resources.getColor(R.color.white, context.getTheme()));
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<ItemsModel> items, String flag) {

        this.items = items;
//        notifyDataSetChanged();
        if (flag.equals(FLAG_SET_ONE)) {

            notifyItemInserted(items.size() - 1);
        } else notifyItemRangeInserted(0, items.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userTxt, textTitle;
        private CardView item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item);
            userTxt = itemView.findViewById(R.id.userTxt);
            textTitle = itemView.findViewById(R.id.textTitle);
        }
    }

    public boolean InitializeItemPref() {

        try {
//            TODO: CHECK IF ANY DATA PRESENT, IF YES ADD TO, IF NO INITIALIZE
            handler.writeToPreferences(items);
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public void deleteItem(int position) {

        items.remove(position);
        handler.writeToPreferences(items);
        Toast.makeText(context, "Deleted at position " + position, Toast.LENGTH_SHORT).show();
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
//        notifyDataSetChanged();
    }

}
