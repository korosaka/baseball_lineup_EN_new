package com.websarva.wings.android.baseballstartinglineup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SubMembersFragment extends Fragment {


    protected ListView playerList;
    protected SubPlayerListAdapter listAdapter;
    protected int orderType;


    public static SubMembersFragment newInstance(int orderType) {
        SubMembersFragment fragment = new SubMembersFragment();
        Bundle args = new Bundle();
        args.putInt(FixedWords.ORDER_TYPE, orderType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) orderType = getArguments().getInt(FixedWords.ORDER_TYPE);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sub_members, container, false);
        playerList = view.findViewById(R.id.sub_player_list);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listAdapter =
                new SubPlayerListAdapter(
                        getContext(),
                        R.layout.sub_player_list_item,
                        CachedPlayersInfo.instance.getSubMembers(orderType),
                        (MakingOrderActivity) getActivity()
                );

        playerList.setAdapter(listAdapter);
        setListViewHeightBasedOnChildren(playerList);
    }

    public void updatePlayerListView() {
        listAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(playerList);
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}