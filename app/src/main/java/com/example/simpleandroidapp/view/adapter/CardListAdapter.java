package com.example.simpleandroidapp.view.adapter;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleandroidapp.R;
import com.example.simpleandroidapp.model.Card;
import com.example.simpleandroidapp.view.util.DragListener;

import java.util.List;


public class CardListAdapter extends RecyclerView.Adapter<CardListViewHolder> {
    private Context mContext;
    private List<Card> mList;
    private Listener mListener;

    public interface Listener {
        void setEmptyListTop(boolean visibility);
        void setEmptyListBottom(boolean visibility);
    }

    public CardListAdapter(Context context, List<Card> list, Listener listener) {
        this.mContext = context;
        this.mList = list;
        this.mListener = listener;
    }


    @NonNull
    @Override
    public CardListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item, parent, false);
        return new CardListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CardListViewHolder holder, int position) {
        if(mList.get(position).getImageId() > 0) {
            if(!mList.get(position).isTurned()) {
                holder.mCardImage.setImageResource(R.drawable.bb);
            } else {
                holder.mCardImage.setImageResource(mList.get(position).getImageId());
            }
            holder.setCardData(mList.get(position));
        }

        holder.mFrameLayout.setTag(position);
        holder.mCardImage.setTag(position);
        holder.mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mList.get(position).isTurned()) {
                    holder.mCardImage.setImageResource(mList.get(position).getImageId());
                    mList.get(position).setTurned(true);
                }
            }
        });
        holder.mFrameLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, shadowBuilder, v, 0);
                } else {
                    v.startDrag(data, shadowBuilder, v, 0);
                }
                return true;
            }
        });
        holder.mFrameLayout.setOnDragListener(new DragListener(mListener));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<Card> getList() {
        return this.mList;
    }

    public void replace(Card c, int pos) {
        this.mList.add(pos, c);
    }

    public void remove(int pos) {
        this.mList.remove(pos);
    }

    public DragListener getDragListener() {
        if(mListener != null) {
            return new DragListener(mListener);
        } else {
            return null;
        }
    }
}
