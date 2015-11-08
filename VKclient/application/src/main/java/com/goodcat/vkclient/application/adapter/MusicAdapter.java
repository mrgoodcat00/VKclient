package com.goodcat.vkclient.application.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.model.music.MusicModel;

import java.util.List;

public class MusicAdapter extends ArrayAdapter<MusicModel>{
    public MusicAdapter(Context context,List<MusicModel> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View currentView = View.inflate(getContext(), R.layout.music_list_item,null);

        return currentView;
    }
}
