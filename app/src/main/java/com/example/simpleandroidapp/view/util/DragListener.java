package com.example.simpleandroidapp.view.util;

import android.view.DragEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleandroidapp.R;
import com.example.simpleandroidapp.model.Card;
import com.example.simpleandroidapp.view.adapter.CardListAdapter;
import com.example.simpleandroidapp.view.adapter.CardListAdapter.*;

import java.util.List;

public class DragListener implements View.OnDragListener {
    private Listener mListener;
    private boolean mIsDropped = false;

    public DragListener(Listener listener) {
        this.mListener = listener;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {

        switch(event.getAction()) {
            case DragEvent.ACTION_DROP:
                mIsDropped = true;
                int positionTarget = -1;

                View view = (View)event.getLocalState();
                int viewId = view.getId();

                final int frameLayout = R.id.card_frame;
                final int topList = R.id.card_list_top;
                final int bottomList = R.id.card_list_bottom;
                switch(viewId) {
                    case frameLayout:
                        View target;
                        switch (viewId) {
                            case topList:
                                target = (RecyclerView) v.getRootView().findViewById(topList);
                                break;
                            case bottomList:
                                target = (RecyclerView) v.getRootView().findViewById(bottomList);
                                break;
                            default:
                                target = (View) v.getParent();
                                positionTarget = (int) v.getTag();
                        }

                        if (view != null) {
                            RecyclerView source = (RecyclerView) view.getParent();

                            // Remove card from deck
                            CardListAdapter adapterSource = (CardListAdapter) source.getAdapter();
                            int posSource = (int) view.getTag();
                            int sourceId = source.getId();

                            Card c = adapterSource.getList().get(posSource);
                            List<Card> listSource = adapterSource.getList();

                            adapterSource.remove(posSource);
//                            adapterSource.notifyDataSetChanged();

                            // Update card for card list
                            RecyclerView cardListView = (RecyclerView) target;
                            CardListAdapter targetAdapter = (CardListAdapter) cardListView.getAdapter();

                            if(positionTarget >= 0) {
                                targetAdapter.replace(c, positionTarget);
                                targetAdapter.notifyItemChanged(positionTarget);
                            }

                            if(sourceId == topList && adapterSource.getItemCount() < 1) {
                                mListener.setEmptyListTop(true);
                            }
                        }
                        break;
                }
                break;
        }

        if(!mIsDropped && event.getLocalState() != null) {
            ((View) event.getLocalState()).setVisibility(View.VISIBLE);
        }

        return true;
    }
}
