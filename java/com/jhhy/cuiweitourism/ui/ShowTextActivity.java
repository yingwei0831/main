package com.jhhy.cuiweitourism.ui;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

public class ShowTextActivity extends BaseActionBarActivity {

    String text;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_show_text);
        getData();
        super.onCreate(savedInstanceState);
        tvTitle.setText(title);
        TextView tvShowText = (TextView) findViewById(R.id.tv_show_reserve);
        tvShowText.setText(Html.fromHtml(text));
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        text = bundle.getString("text");
        title = bundle.getString("title");
    }
}
