package com.strangea.venuesearch;

import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.strangea.venuesearch.api.Api;
import com.strangea.venuesearch.api.ApiManager;
import com.strangea.venuesearch.api.DaggerApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @VisibleForTesting @BindView(R.id.text_view) TextView textView;
    @VisibleForTesting @BindView(R.id.edit_text) EditText editText;
    @VisibleForTesting @BindView(R.id.progress_layout) View progressView;

    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        api = DaggerApi.create();
    }

    @OnClick(R.id.button)
    public void search(){
        if(editText.getText().toString().isEmpty()) {
            textView.setText(R.string.search_location_required);
            return;
        }
        progressView.setVisibility(View.VISIBLE);

        api.apiManager().getVenueList(editText.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String searchResponse) {

                        JsonParser parser = new JsonParser();
                        JsonObject json = parser.parse(searchResponse).getAsJsonObject();

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String prettyJson = gson.toJson(json);

                        textView.setText(prettyJson);
                        progressView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.setText(e.getMessage());
                        progressView.setVisibility(View.GONE);
                    }
                });
    }
}
