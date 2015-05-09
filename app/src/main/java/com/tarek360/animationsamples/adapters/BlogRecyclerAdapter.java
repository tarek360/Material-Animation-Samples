package com.tarek360.animationsamples.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tarek360.animationsamples.R;
import com.tarek360.animationsamples.models.Blog;

import java.util.List;

/**
 * Created by Tarek on 4/24/2015.
 */
public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.SimpleItemViewHolder> {

    private List<Blog> items;

    // Provide a reference to the views for each data item
    // Provide access to all the views for a data item in a view holder
    public final static class SimpleItemViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView subTitle;
        CardView cardView;

        public SimpleItemViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageThumb);
            title = (TextView) itemView.findViewById(R.id.title);
            subTitle = (TextView) itemView.findViewById(R.id.subTitle);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BlogRecyclerAdapter(List<Blog> items) {
        this.items = items;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    // Create new items (invoked by the layout manager)
    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public SimpleItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.blog_item, viewGroup, false);
        return new SimpleItemViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(SimpleItemViewHolder viewHolder, int position) {

        viewHolder.image.setImageResource(items.get(position).getImageRes());
        viewHolder.image.setTag(position);
        viewHolder.title.setText(items.get(position).getTitle());
        viewHolder.subTitle.setText(items.get(position).getSubTitle());
        viewHolder.cardView.setCardBackgroundColor(items.get(position).getBackGroundColor());

    }
}