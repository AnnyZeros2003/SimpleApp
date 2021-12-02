package com.example.simpleandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.simpleandroidapp.model.CardRepo;
import com.example.simpleandroidapp.view.adapter.CardListAdapter;
import com.example.simpleandroidapp.view.util.StackLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPageActivity extends AppCompatActivity implements CardListAdapter.Listener {

    @BindView(R.id.card_list_top)
    RecyclerView mTopList;

    @BindView(R.id.card_list_bottom)
    RecyclerView mBottomList;

    @BindView(R.id.shuffle_button)
    Button mShuffle;

    private CardRepo mCardRepo;
    private CardListAdapter mTopCardListAdapter;
    private CardListAdapter mBottomCardListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ButterKnife.bind(this);

        mCardRepo = new CardRepo();

        initTopList();
        initBottomList();

        mShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardRepo.shuffle(mCardRepo.getCardList());

                mTopList.setAdapter(null);
                initTopList();

                mBottomList.setAdapter(null);
                initBottomList();

            }
        });
    }

    private void initTopList() {

        mTopCardListAdapter = new CardListAdapter( this, mCardRepo.createEmptyList(), this);
        mTopList.setLayoutManager(new GridLayoutManager(this, 3));
        mTopList.setAdapter(mTopCardListAdapter);
        mTopList.setOnDragListener(mTopCardListAdapter.getDragListener());
    }

    private void initBottomList() {
        mBottomList.setLayoutManager(new StackLayoutManager(this));

        mBottomCardListAdapter = new CardListAdapter(this, mCardRepo.getCardList(), this);
        mBottomList.setAdapter(mBottomCardListAdapter);
        mBottomList.setOnDragListener(mBottomCardListAdapter.getDragListener());
    }

    @Override
    public void setEmptyListTop(boolean visibility) {
        mTopList.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setEmptyListBottom(boolean visibility) {
        mBottomList.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }
}