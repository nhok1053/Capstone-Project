package com.example.huynhha.cookandshare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.huynhha.cookandshare.entity.YoutubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YoutubeActivity extends YouTubeBaseActivity {
    YouTubePlayerView youTubePlayerView;
    String ytb = "";
    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        youTubePlayerView = findViewById(R.id.youtubePlayer);
        ytb = getIntent().getStringExtra("youtube");
    }

    @Override
    protected void onStart() {
        super.onStart();
        playYoutube(ytb);

    }

    private void playYoutube(final String s) {
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(s);
                youTubePlayer.setFullscreen(true);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(YoutubeActivity.this, "Phát video bị lỗi", Toast.LENGTH_SHORT).show();
            }
        };
        youTubePlayerView.initialize(YoutubeConfig.getApiKey(), onInitializedListener);
    }
}
