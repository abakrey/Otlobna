package com.example.otlobna.Adapter;

import android.view.View;
import android.widget.TextView;

import com.example.otlobna.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class DetailsHolder extends ChildViewHolder {
    private TextView mTextView;

    public DetailsHolder(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.textView);
    }

    public void bind(Details product) {
        mTextView.setText(product.name);
    }
}
