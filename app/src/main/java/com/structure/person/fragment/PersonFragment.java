package com.structure.person.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.structure.R;
import com.structure.base.fragment.BaseFragment;

import butterknife.InjectView;

/**
 * Created by yuchao.
 */
public class PersonFragment extends BaseFragment<PersonFragmentPresenter>{

    @InjectView(R.id.content_recycler)
    RecyclerView mRecyclerView;

    public final static int ITEM_SPAN = 3;
    public final static String USER_ID      = "user_id";
    public final static String SHOW_HOLDER = "show_holder";


    private PersonAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_layout, container, false);

        return view;
    }

    protected void onViewCreated() {
        initRecyclerView();
    }


    @Override
    protected PersonFragmentPresenter createPresenter() {
        return new PersonFragmentPresenter(this);
    }


    public static PersonFragment newInstance(@NonNull String userID, boolean isShowHolder) {
        Bundle args = new Bundle();
        args.putString(USER_ID, userID);
        args.putBoolean(SHOW_HOLDER, isShowHolder);
        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initRecyclerView() {
        mLayoutManager = new GridLayoutManager(getContext(), ITEM_SPAN);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PersonAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

}