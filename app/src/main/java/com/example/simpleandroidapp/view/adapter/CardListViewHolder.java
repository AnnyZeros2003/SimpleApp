package com.example.simpleandroidapp.view.adapter;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleandroidapp.R;
import com.example.simpleandroidapp.model.Card;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardListViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.card_frame)
    FrameLayout mFrameLayout;

    @BindView(R.id.card_image)
    ImageView mCardImage;

    Card mCard;

    public CardListViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void setCardData(Card card) {
        mCard = card;
    }

    public void setImageId(int imageId) {
        mCardImage.setImageResource(imageId);
    }
}
