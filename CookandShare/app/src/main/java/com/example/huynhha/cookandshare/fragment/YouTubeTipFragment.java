package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.YoutubeConfig;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class YouTubeTipFragment extends Fragment {
    WebView webView;

    public YouTubeTipFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_you_tube_tip, container, false);
        webView = v.findViewById(R.id.webviewYoutube);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadData("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/bSMZknDI6bg\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>", "text/html", "utf-8");
        return v;
    }
}
