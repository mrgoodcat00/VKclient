package com.goodcat.vkclient.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.model.music.MusicModel;
import com.goodcat.vkclient.application.service.MusicService;

import java.util.List;

public class MusicAdapter extends ArrayAdapter<MusicModel>{

    private static String token;
    private List<MusicModel> mItems;
    private ServiceConnection musicConnection;
    private Intent musicIntent = new Intent(getContext(),MusicService.class);
    public MusicAdapter(Context context,List<MusicModel> objects, ServiceConnection musicConnection) {
        super(context, 0, objects);
        this.mItems    = objects;
        this.musicConnection = musicConnection;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewAudioItemsHolder holder;
        View currentView = null;
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

        final MusicModel musicItem = mItems.get(position);

        holder.music_singer.setText(musicItem.getArtist());
        holder.music_song_title.setText(musicItem.getTitle());
        long dur = musicItem.getDuration()/60;
        long durInMin = dur % 60;
        holder.music_duration.setText(dur+","+durInMin+" min");

        holder.music_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("!!!!!!!",musicItem.getUrl().substring(0,musicItem.getUrl().indexOf("?")));
                musicIntent.putExtra("START_COMMAND", "play");
                musicIntent.putExtra("START_POSITION",""+position);
                musicIntent.putExtra("FILE_URL",musicItem.getUrl().substring(0,musicItem.getUrl().indexOf("?")));
                //getContext().startService(musicIntent);
                getContext().bindService(musicIntent,musicConnection,getContext().BIND_AUTO_CREATE);
            }
        });

        holder.music_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicIntent.putExtra("START_COMMAND","stop");
                getContext().bindService(musicIntent,musicConnection,getContext().BIND_AUTO_CREATE);
            }
        });

        holder.music_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicIntent.putExtra("START_COMMAND","pause");
                getContext().bindService(musicIntent, musicConnection,getContext().BIND_AUTO_CREATE);
            }
        });

        /*holder.music_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( mediaPlayer == null) {
                    try {

                        mediaPlayer = new MediaPlayer();
                        Log.d("MEDIA_PLAYER", "start HTTP " + musicItem.getUrl());
                        mediaPlayer.setDataSource(musicItem.getUrl());
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        Log.d("MEDIA_PLAYER", "prepareAsync");
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                Log.d("MEDIA_PLAYER", "STARTING");
                                mp.start();
                            }
                        });
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                releaseMP();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    mediaPlayer.start();
                }


            }
        });*/

       /* holder.music_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                } else {return;}
            }
        });

        holder.music_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer == null){return;}
                mediaPlayer.stop();
                releaseMP();
            }
        });*/


        return currentView;
    }

   /* private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

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
