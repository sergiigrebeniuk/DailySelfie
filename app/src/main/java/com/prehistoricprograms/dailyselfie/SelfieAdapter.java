package com.prehistoricprograms.dailyselfie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SelfieAdapter extends BaseAdapter {

    private ArrayList<SelfieRecord> list = new ArrayList<>();
    private Context mContext;
    private static LayoutInflater mInflater = null;

    public SelfieAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View newView = convertView;
        ViewHolder holder;

        SelfieRecord curr = list.get(position);

        if (null == convertView) {
            holder = new ViewHolder();
            newView = mInflater.inflate(R.layout.selfie_view, parent, false);
            holder.thumb = (ImageView) newView.findViewById(R.id.thumb);
            holder.name = (TextView) newView.findViewById(R.id.name);
            newView.setTag(holder);
        } else {
            holder = (ViewHolder) newView.getTag();
        }

        holder.thumb.setImageBitmap(curr.getScaledSelfie((int)mContext.getResources().getDimension(R.dimen.thumb_size), (int)mContext.getResources().getDimension(R.dimen.thumb_size)));
        holder.name.setText(curr.getName());

        return newView;
    }

    static class ViewHolder {
        ImageView thumb;
        TextView name;
    }

    public void add(SelfieRecord listItem) {
        list.add(listItem);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
    }
}
