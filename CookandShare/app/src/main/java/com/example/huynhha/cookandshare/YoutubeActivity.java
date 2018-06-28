package com.example.huynhha.cookandshare;

import android.os.Bundle;
import android.widget.Toast;

import com.example.huynhha.cookandshare.entity.YouTube;
import com.example.huynhha.cookandshare.entity.YoutubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity {
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        youTubePlayerView=findViewById(R.id.youtubePlayer);
//        final YouTube youTube= (YouTube) getIntent().getExtras().getBinder("youtube");
        final String s=getIntent().getStringExtra("youtube");
        onInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(s);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(YoutubeActivity.this, "Error while loading!!!", Toast.LENGTH_SHORT).show();
            }
        };
        youTubePlayerView.initialize(YoutubeConfig.getApiKey(), onInitializedListener);
    }
}
