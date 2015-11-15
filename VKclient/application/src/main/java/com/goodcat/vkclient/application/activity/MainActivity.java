package com.goodcat.vkclient.application.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.adapter.UserWallPostsAdapter;
import com.goodcat.vkclient.application.model.user.*;
import com.goodcat.vkclient.application.service.DownloadImageService;
import com.goodcat.vkclient.application.service.RequestService;
import com.goodcat.vkclient.application.service.ResponseHomeCallback;
import com.goodcat.vkclient.application.session.Session;
import com.goodcat.vkclient.application.session.SessionToken;
import net.danlew.android.joda.JodaTimeAndroid;

import java.util.List;


public class MainActivity extends Activity {

    private SessionToken st;

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("SERVICE", "Connected");
            RequestService.RequestWorker requestWorker = (RequestService.RequestWorker) service;
            requestWorker.getUserWithWallData(new ResponseHomeCallback<UserModel, UserWallPostsModel, UserWallProfilesModel, UserWallGroupsModel>() {
                @Override
                public void onResponse(List<UserModel> items, List<UserWallPostsModel> wItems, List<UserWallProfilesModel> wProfiles,List<UserWallGroupsModel> wGroups) {
                    if (!items.isEmpty() && !wItems.isEmpty()) {
                        setUserData(items, wItems, wProfiles, wGroups);
                    }
                }

            }, "14587316"/*"12455497"*/);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("SERVICE","Disconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this);
        setContentView(R.layout.activity_main);
        st = Session.getSession(this);

    }

    private void setUserData(List<UserModel> user, List<UserWallPostsModel> wall, List<UserWallProfilesModel> wProfiles,List<UserWallGroupsModel> wGroups) {
        //------------------------------------------------GETS -------------------------------------------
        ListView wallPosts = (ListView) findViewById(R.id.main_user_posts_wall);
        View WrapperHead = View.inflate(MainActivity.this, R.layout.header_part_of_main, null);
        TextView userName = (TextView) WrapperHead.findViewById(R.id.main_user_name);
        TextView userFrom = (TextView) WrapperHead.findViewById(R.id.main_user_from);
        TextView lastSeen = (TextView) WrapperHead.findViewById(R.id.main_online_status);
        TextView friendsCounter = (TextView) WrapperHead.findViewById(R.id.main_header_friends_quantity);
        TextView commonCounter = (TextView) WrapperHead.findViewById(R.id.main_header_common_friends_quantity);
        TextView followersCounter = (TextView) WrapperHead.findViewById(R.id.main_header_followers_quantity);
        TextView photosCounter = (TextView) WrapperHead.findViewById(R.id.main_header_photos_quantity);
        TextView videoCounter = (TextView) WrapperHead.findViewById(R.id.main_header_videos_quantity);
        TextView audioCounter = (TextView) WrapperHead.findViewById(R.id.main_header_audio_quantity);
        ImageView ownerAvatar = (ImageView) WrapperHead.findViewById(R.id.main_user_logo);

        UserLastSeenModel lastSeeModel = user.get(0).getLast_seen();
        UserCountersModel userCounters = user.get(0).getCounters();

        String firstName = user.get(0).getFirst_name();
        String lastName = user.get(0).getLast_name();
        String homeTown = user.get(0).getHome_town();


        DownloadImageService.fetchImage(user.get(0).getPhoto_200(),ownerAvatar);

        userName.setText(firstName+" "+lastName);
        if(homeTown.length()>0){
            userFrom.setText("From: "+homeTown);
        } else {
            // Needs to add adjustment getter for HOME\TOWN
            userFrom.setText("From: "+homeTown);
        }
        lastSeen.setText(lastSeeModel.getTime());
        //---------------------------------------------- SET COUNTERS -----------------------------------------------------
        friendsCounter.setText(userCounters.getFriends().toString()+"\n Friends");
        commonCounter.setText(userCounters.getFollowers() +"\n Followers");
        followersCounter.setText(userCounters.getGroups()+"\n Groups");
        photosCounter.setText(userCounters.getPhotos()+"\n Photos");
        videoCounter.setText(userCounters.getVideos()+"\n Videos");
        audioCounter.setText(userCounters.getAudios()+"\n Audios");
        //---------------------------------------------- SET WALL -----------------------------------------------------
        wallPosts.addHeaderView(WrapperHead);
        wallPosts.setHeaderDividersEnabled(false);
        wallPosts.setFooterDividersEnabled(false);
        wallPosts.setDividerHeight(0);

        if(wall != null) {
            UserWallPostsAdapter adapter = new UserWallPostsAdapter(getApplicationContext(), wall, wProfiles, wGroups, st.getToken());
            wallPosts.setAdapter(adapter);
        }

        TextView musicQuantity = (TextView) findViewById(R.id.main_header_audio_quantity);
        musicQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent musicIntent = new Intent(MainActivity.this,MusicActivity.class);
                Toast.makeText(MainActivity.this, "Music activity clicked", Toast.LENGTH_SHORT).show();

                startActivity(musicIntent);
            }
        });
        TextView albumsQuantity = (TextView) findViewById(R.id.main_header_photos_quantity);
        albumsQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoIntent = new Intent(MainActivity.this,PhotoAlbumsActivity.class);
                Toast.makeText(MainActivity.this, "Photos activity clicked", Toast.LENGTH_SHORT).show();

                startActivity(photoIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.exit) {
            finish();
            return true;
        }
        if (id == R.id.get_messages) {
            Intent messages = new Intent(MainActivity.this,MessagesActivity.class);
            startActivity(messages);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LOGGER","App is started!");
        bindService(new Intent(this,RequestService.class),serviceConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LOGGER","App is resumed!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LOGGER","App is paused!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LOGGER","App is stopped!");
        unbindService(serviceConnection);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LOGGER","App is destroyed!");
    }
}
