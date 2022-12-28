package com.samski.safenotes.colorsView;

import static com.samski.safenotes.EditorActivity.colorOption;
import static com.samski.safenotes.EditorActivity.fontOption;
import static com.samski.safenotes.EditorActivity.editorOptionsView;
import static com.samski.safenotes.EditorActivity.item;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.samski.safenotes.EditorActivity;
import com.samski.safenotes.R;

import java.util.ArrayList;
import java.util.Objects;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder>{

    Context context;
    ColorModel colors;

    public ColorAdapter(Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.color_items,
                parent,
                false
        );

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ArrayList<String> colorKeys = new ArrayList<>(colors.getColors().keySet());

        int color = context.getResources().
                getColor(Objects.requireNonNull(colors.getColors().get(colorKeys.get(position))),
                        context.getTheme());
        holder.colorItem.setCardBackgroundColor(color);

        holder.colorItem.setOnClickListener(view -> {

//            set user pref color to color
            EditorActivity.colorView.setVisibility(View.GONE);
            EditorActivity.editorLayout.setBackgroundColor(color);
            item.setPreferedThemeColor(colorKeys.get(position));
            editorOptionsView.setBackgroundColor(color);
            colorOption.setBackgroundColor(color);
            colorOption.setBackgroundTintList(ColorStateList.valueOf(color));
            fontOption.setBackgroundTintList(ColorStateList.valueOf(color));
        });
    }

    @Override
    public int getItemCount() {

        return colors.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //define my views
        CardView colorItem;
        RecyclerView colorView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            colorItem = itemView.findViewById(R.id.colorItem);
            colorView = itemView.findViewById(R.id.colorView);
        }
    }

    public void setColors(ColorModel colors) {
        this.colors = colors;
        notifyItemRangeInserted(0, colors.getCount());
    }

}
