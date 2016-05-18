package com.walter.sc.myjgapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by huangxl on 2016/3/30.
 */
public class MyButterKnifeAdapter extends BaseAdapter {
    private static final String[] CONTENTS = ("OkHttp GreenDao soundPool EventBus " +
            "NavigationView MyTabActivity HandHeldActivity " +
            "CustomControl lazy dog").split(" ");

    private final LayoutInflater inflater;

    public MyButterKnifeAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return CONTENTS.length;
    }

    @Override
    public Object getItem(int position) {
        return CONTENTS[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.simple_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        String word = getItem(position).toString();
        viewHolder.word.setText("Word: " + word);
        viewHolder.length.setText("Length: " + word.length());
        viewHolder.position.setText("Position: " + position);


        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.word)
        TextView word;

        @Bind(R.id.length)
        TextView length;

        @Bind(R.id.position)
        TextView position;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


    }
}
