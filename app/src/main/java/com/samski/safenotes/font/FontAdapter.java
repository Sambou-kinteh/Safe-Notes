package com.samski.safenotes.font;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.samski.safenotes.EditorActivity;
import com.samski.safenotes.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FontAdapter extends RecyclerView.Adapter<FontAdapter.ViewHolder> {

    Context context;
    FontModel font;

    public FontAdapter(Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(

                R.layout.font_items,
                parent,
                false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ArrayList<String> fontValues = new ArrayList<>(font.getFonts().keySet());
        Typeface typeface = context.getResources().getFont(
                Objects.requireNonNull(font.getFonts().get(fontValues.get(position))));
        holder.fontItem.setTypeface(typeface);
        holder.fontItem.setOnClickListener(view -> {

            EditorActivity.fontView.setVisibility(View.GONE);
            EditorActivity.editorForUserText.setTypeface(typeface);
            EditorActivity.item.setFont(fontValues.get(position));

        });
    }

    @Override
    public int getItemCount() {

        return font.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView fontCard;
        private TextView fontItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fontCard = itemView.findViewById(R.id.fontCard);
            fontItem = itemView.findViewById(R.id.fontItem);
        }
    }

    public void setFont(FontModel font) {

        this.font = font;
        notifyItemRangeInserted(0, this.font.getCount());
    }
}
