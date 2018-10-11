package com.example.android.popularmoviemvvmproject.ui.detail.review;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmoviemvvmproject.R;
import com.example.android.popularmoviemvvmproject.data.models.Review;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anamika Tripathi on 10/10/18.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    private Context context;
    private List<Review> reviewList;

    ReviewAdapter(Context context) {
        this.context = context;
        reviewList = new ArrayList<>();
    }

    public void setList(List<Review> reviews){
        reviewList = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review, viewGroup, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        holder.reviewAuthor.setText(reviewList.get(position).getAuthor());
        holder.reviewImage.setText(""+reviewList.get(position).getAuthor().charAt(0));
        holder.reviewContent.setText(reviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        if (reviewList.size()>0)
            return reviewList.size();
        else return 0;
    }

    class ReviewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.review_author)
        TextView reviewAuthor;
        @BindView(R.id.review_image)
        TextView reviewImage;
        @BindView(R.id.review_content)
        TextView reviewContent;
        ReviewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
