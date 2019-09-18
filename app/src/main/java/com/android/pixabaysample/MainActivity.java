package com.android.pixabaysample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etSearch;
    ImageView ivSearch;
    ProgressBar topProgressBar;
    RecyclerView recyclerView;
    ImageRecyclerAdapter adapter;
    ArrayList<String> imageList = new ArrayList<>();
    int currentPage = 1;
    private boolean isLoading = false, isLastPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();

    }

    public void initializeView(){
        etSearch = findViewById(R.id.etSearchbar);
        ivSearch = findViewById(R.id.ivSearch);
        topProgressBar = findViewById(R.id.topProgressBar);
        recyclerView = findViewById(R.id.recyclerView);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new ImageRecyclerAdapter(this, imageList);
        recyclerView.setAdapter(adapter);

        RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition + 1) >= totalItemCount) {
                        getPhotos(etSearch.getText().toString());
                    }
                }
            }
        };

        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage=1;
                isLastPage=false;
                getPhotos(etSearch.getText().toString());
            }
        });

    }

    private void getPhotos(final String searchString) {
        if(!searchString.trim().equalsIgnoreCase("")) {

            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);

            @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, String> myTask = new AsyncTask<Void, Void, String>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    topProgressBar.setVisibility(View.VISIBLE);
                    isLoading = true;

                }

                @Override
                protected String doInBackground(Void... params) {

                    String response;

                    try {

                        String url = InternetOperations.SERVER_URL+"?key="+InternetOperations.API_KEY+"&q="+searchString+"&image_type=photo&page="+currentPage;

                        response = InternetOperations.get(url);

                        return response;

                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                    return null;
                }

                protected void onPostExecute(String s) {

                    topProgressBar.setVisibility(View.GONE);
                    isLoading = false;

                    if (s != null) {


                        try {


                            JSONObject object = new JSONObject(s);

                            JSONArray jsonArray = object.optJSONArray("hits");

                            if(currentPage==1)
                                imageList.clear();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                                    imageList.add(jsonObject.optString("webformatURL"));
                                }

                                adapter.updateList(imageList);

                                currentPage++;

                                if(jsonArray.length()==0)
                                isLastPage = true;


                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                    }


                }
            };
            myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

}
