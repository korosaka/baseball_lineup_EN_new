package com.websarva.wings.android.baseballstartinglineup;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class StartingPlayerListAdapter extends ArrayAdapter<StartingPlayerListItemData> {

    private List<StartingPlayerListItemData> playerItems;
    private int mResource;
    private LayoutInflater mInflater;
    private StartingPlayerListAdapterListener mListener;

    public StartingPlayerListAdapter(Context context, int resource, List<StartingPlayerListItemData> items, StartingPlayerListAdapterListener listener) {
        super(context, resource, items);

        playerItems = items;
        mResource = resource;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListener = listener;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView != null) {
            view = convertView;
        } else {
            view = mInflater.inflate(mResource, null);
        }

        Button orderButton = view.findViewById(R.id.order_button);
        TextView positionText = view.findViewById(R.id.position_text);
        TextView nameText = view.findViewById(R.id.name_text);

        preparePlayerItemView(playerItems.get(position), orderButton, positionText, nameText);

        return view;
    }

    private void preparePlayerItemView(
            StartingPlayerListItemData playerItem, Button orderButton, TextView positionText, TextView nameText) {
        int orderNum = playerItem.getItemOrderNumber();

        if (orderNum == FixedWords.DH_PITCHER_ORDER) {
            orderButton.setText(FixedWords.PITCHER_INITIAL);
            positionText.setTextColor(Color.parseColor(FixedWords.COLOR_PITCHER_TEXT));
        } else {
            orderButton.setText(MakingOrderActivity.makeOrdinalNumber(orderNum));
        }
        orderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onClickStartingOrderNum(orderNum, orderButton);
            }
        });

        positionText.setText(playerItem.getItemPosition());
        nameText.setText(playerItem.getItemName());
        changeTextSize(nameText);
    }

    private void changeTextSize(TextView textView) {
        int lengthOfText = textView.length();
        int textSize;
        switch (lengthOfText) {
            case 10:
                textSize = 25;
                break;
            case 11:
                textSize = 23;
                break;
            case 12:
                textSize = 21;
                break;
            case 13:
                textSize = 20;
                break;
            case 14:
                textSize = 19;
                break;
            case 15:
                textSize = 18;
                break;
            default:
                textSize = 28;
                break;
        }
        textView.setTextSize(textSize);
    }

}

interface StartingPlayerListAdapterListener {
    void onClickStartingOrderNum(int orderNum, Button numButton);
}