package com.e.dropbox_list_images;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.DbxUserUsersRequests;
import com.dropbox.core.v2.users.FullAccount;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String ACCESS_TOKEN = "jMQjMsTtg3IAAAAAAAAAAVObPlY8Yef9C13w75pqpBPGq0EO_sQoek6XX6BxOCdn";
    private ImageView imageView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //dont forget about thread raunnble this is just lazy test
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        recyclerView = findViewById(R.id.recyclerView);//display


        try {
            getThumbnailBatch();
        } catch (DbxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getThumbnailBatch() throws DbxException, IOException {
        //GET TEMPORARY LINK IS ONE FILE RETURN ONLY NOT FOR LIST DATA.
        DbxRequestConfig config;
        config = new DbxRequestConfig("dropbox/spring-boot-file-upload");
        DbxClientV2 client;
        client = new DbxClientV2(config, ACCESS_TOKEN);
        DbxUserUsersRequests r1 = client.users();
        FullAccount account = r1.getCurrentAccount();
        System.out.println(account.getName().getDisplayName());
        System.out.println("waiting...");

        ListFolderResult result = client.files().listFolder("/images");

        System.out.println("ubos na ang array ito size> : " + result.getEntries().size());
        layoutChanges(result.getEntries(), client, result);

    }

    public void layoutChanges(List<Metadata> link, DbxClientV2 client, ListFolderResult result) {

        layoutManager = new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        MyAdapter mMyAdapter = new MyAdapter(MainActivity.this, link, client);
        recyclerView.setAdapter(mMyAdapter);

    }


}