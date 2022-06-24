package com.example.veganplace.ui.noticias;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.veganplace.R;
import com.example.veganplace.data.modelnoticias.Article;

public class NoticiaenlaWeb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_noticiaenla_web);

        Toolbar toolbar = findViewById(R.id.toolbarnews);
        setSupportActionBar(toolbar);
        this.setTitle(R.string.title_detalle_noticia);

        Article noticia = (Article) getIntent().getSerializableExtra("noticiasweb");
        WebView webView = (WebView)findViewById(R.id.webview_new);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(noticia.getUrl().toString());
    }
}