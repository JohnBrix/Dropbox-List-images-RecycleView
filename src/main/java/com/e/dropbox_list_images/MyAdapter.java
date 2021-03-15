package com.e.dropbox_list_images;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.GetTemporaryLinkResult;
import com.dropbox.core.v2.files.Metadata;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Metadata> link;
    private Context context;
    private DbxClientV2 client;
    /*private GetTemporaryLinkResult temporaryLinkLink;
     */

    public MyAdapter(Context context, List<Metadata> link, DbxClientV2 client) {

        this.link = link;
        this.context = context;
        this.client = client;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*   View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);*/
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Metadata list = link.get(position);

        List<Metadata> originalData = new ArrayList<>();
        originalData.add(list);

        Set<Metadata> setWithUniqueValues = new HashSet(originalData);

        setWithUniqueValues.stream().distinct().forEach(il -> {
            System.out.println("PARALLEL MO: " + il);
            try {
                GetTemporaryLinkResult temporaryLink = client.files().getTemporaryLink(il.getPathLower());
                Picasso.get().load(temporaryLink.getLink()).into(holder.image_view);
                System.out.println("hash pom: " + temporaryLink.getLink());

            } catch (DbxException e) {
                e.printStackTrace();
            }

        });


    }

    @Override
    public int getItemCount() {
        return link.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image_view;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);


            image_view = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }
    }

}