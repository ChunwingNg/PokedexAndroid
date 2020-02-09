package com.example.pokedexandroid.Common;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
    private int offset;

    public ItemOffsetDecoration(int offset) {
        this.offset = offset;
    }

    public ItemOffsetDecoration(@NonNull Context context, @DimenRes int dimens){
        this(context.getResources().getDimensionPixelSize(dimens));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(offset,offset,offset,offset);
    }
}
