package com.android.gerajjjj.dailyselfie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gerajjjj on 1/24/2017.
 */

public class SelfieViewAdapter extends BaseAdapter {


    private ArrayList<SelfieRecord> list = new ArrayList<SelfieRecord>();
    private static LayoutInflater inflater = null;
    private Context mContext;

    public SelfieViewAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView = convertView;
        ViewHolder holder;

        SelfieRecord curr = list.get(position);

        if (null == convertView) {
            holder = new ViewHolder();
            newView = inflater
                    .inflate(R.layout.selfie_list_item, parent, false);
            holder.photo = (ImageView) newView.findViewById(R.id.photo);
            holder.extraInfo = (TextView) newView.findViewById(R.id.extra_info);
            newView.setTag(holder);

        } else {
            holder = (ViewHolder) newView.getTag();
        }

        holder.photo.setImageBitmap(curr.getPhotoBitMap());
        holder.extraInfo.setText("Info: " + curr.getExtraInfo());

        return newView;
    }

    static class ViewHolder {
        ImageView photo;
        TextView extraInfo;
    }
    public void add(SelfieRecord listItem) {
        list.add(listItem);
        notifyDataSetChanged();
    }

    public ArrayList<SelfieRecord> getList() {
        return list;
    }

    public void removeAllViews() {
        list.clear();
        this.notifyDataSetChanged();
    }

}
