package com.goodcat.vkclient.application.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.model.music.MusicModel;
import com.goodcat.vkclient.application.service.MusicService;

import java.util.List;

public class MusicAdapter extends ArrayAdapter<MusicModel>{

    private MusicService.MusicWorker musicBinder;

    public MusicAdapter(Context context,List<MusicModel> objects, MusicService.MusicWorker musicBinder) {
        super(context, 0, objects);
        this.musicBinder = musicBinder;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        final int position = pos;
        float sec = getItem(position).getDuration() % 60;
        int min = getItem(position).getDuration() / 60;
        View currentView = null;
        final ViewAudioItemsHolder holder;
        final SeekBar progressBar;

        if (convertView == null) {
            holder = new ViewAudioItemsHolder();
            currentView = View.inflate(getContext(),R.layout.music_list_item, null);
            holder.music_singer = (TextView) currentView.findViewById(R.id.music_singer);
            holder.music_song_title = (TextView) currentView.findViewById(R.id.music_song_title);
            holder.music_duration = (TextView) currentView.findViewById(R.id.music_duration);

            holder.music_play = (ImageButton) currentView.findViewById(R.id.music_play);
            holder.music_pause = (ImageButton) currentView.findViewById(R.id.music_pause);
            holder.music_stop = (ImageButton) currentView.findViewById(R.id.music_stop);

            currentView.setTag(holder);
        } else {
            currentView = convertView;
            holder = (ViewAudioItemsHolder) currentView.getTag();
        }
            progressBar = (SeekBar) currentView.findViewById(R.id.music_progress);

        if(getItem(position).isPlaying()){
            Log.d("M_ADAPTER", "is current item - " + position);
            progressBar.setVisibility(View.VISIBLE);
            musicBinder.setProgressBar(progressBar);
        } else {progressBar.setVisibility(View.INVISIBLE);Log.d("M_ADAPTER","is not current item - "+position);}

        holder.music_singer.setText(getItem(position).getArtist());
        holder.music_song_title.setText(getItem(position).getTitle());
        holder.music_duration.setText(min+"."+Math.round(sec));

        holder.music_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(musicBinder.getLastPosition() > 0 && musicBinder.getLastPosition() != position){
                    musicBinder.stopAudioTrack(musicBinder.getLastPosition());
                }
                musicBinder.playAudioTrack(getItem(position).getUrl().substring(0,getItem(position).getUrl().indexOf("?")), position);
                musicBinder.setCurrentPlayingTrack(getItem(position));
                musicBinder.setProgressBar(progressBar);
            }
        });

        holder.music_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicBinder.stopAudioTrack(position);
                getItem(position).setIsPlaying(false);
                progressBar.setVisibility(View.INVISIBLE);
                musicBinder.dropTrack();
            }
        });

        holder.music_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicBinder.pauseAudioTrack(position);
            }
        });
        return currentView;
    }

    public static class ViewAudioItemsHolder {
        TextView music_song_title;
        TextView music_singer;
        ImageButton music_play;
        ImageButton music_pause;
        ImageButton music_stop;
        TextView music_duration;
        TextView music_circled;

    }
}
