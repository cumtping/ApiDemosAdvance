/*
 * Copyright (c) 2018. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.android.apis.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowId;
import android.view.WindowId.FocusObserver;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.apis.R;

public class WindowFocusObserver extends Activity implements SearchView.OnQueryTextListener {
    TextView mState;

    final FocusObserver mObserver = new FocusObserver() {

        @Override
        public void onFocusGained(WindowId token) {
            mState.setText("Gained focus");
        }

        @Override
        public void onFocusLost(WindowId token) {
            mState.setText("Lost focus");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.window_focus_observer);
        mState = (TextView)findViewById(R.id.focus_state);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.content_actions, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);

        // Set file with share history to the provider and set the share intent.
        MenuItem actionItem = menu.findItem(R.id.menu_item_share_action_provider_action_bar);
        ShareActionProvider actionProvider = (ShareActionProvider) actionItem.getActionProvider();
        actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        // Note that you can set/change the intent any time,
        // say when the user has selected an image.
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        Uri uri = Uri.fromFile(getFileStreamPath("shared.png"));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        actionProvider.setShareIntent(shareIntent);
        return true;
    }

    public void onSort(MenuItem item) {
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        WindowId token = getWindow().getDecorView().getWindowId();
        token.registerFocusObserver(mObserver);
        mState.setText(token.isFocused() ? "Focused" : "Not focused");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getWindow().getDecorView().getWindowId().unregisterFocusObserver(mObserver);
    }
}
