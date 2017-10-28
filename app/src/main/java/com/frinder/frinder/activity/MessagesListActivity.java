package com.frinder.frinder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.frinder.frinder.R;
import com.frinder.frinder.dataaccess.MessageFirebaseDas;
import com.frinder.frinder.model.MessageThread;
import com.frinder.frinder.utils.Constants;
import com.frinder.frinder.views.ThreadDialogViewHolder;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesListActivity extends BaseActivity {

    private MessageFirebaseDas mMessageFirebaseDas;

    @BindView(R.id.dlThreads)
    DialogsList dlThreads;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Messages");

        final DialogsListAdapter dialogsListAdapter =
                new DialogsListAdapter<>(R.layout.item_dialog, ThreadDialogViewHolder.class, null);
        dialogsListAdapter.setOnDialogClickListener(new DialogsListAdapter.OnDialogClickListener<MessageThread>() {
            @Override
            public void onDialogClick(MessageThread thread) {
                Intent i = new Intent(MessagesListActivity.this, MessageDetailActivity.class);
                i.putExtra(Constants.INTENT_EXTRA_THREAD, Parcels.wrap(thread));
                MessagesListActivity.this.startActivity(i);
            }
        });
        
        dlThreads.setAdapter(dialogsListAdapter);
        mMessageFirebaseDas = new MessageFirebaseDas(this);
        mMessageFirebaseDas.getThreads(new MessageFirebaseDas.OnCompletionListener() {
            public void onThreadsReceived(ArrayList<MessageThread> threads) {
                dialogsListAdapter.setItems(threads);
                dialogsListAdapter.notifyDataSetChanged();
            }
        });
    }
}
