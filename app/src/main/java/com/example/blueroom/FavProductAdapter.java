package com.example.blueroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class FavProductAdapter extends FirestoreRecyclerAdapter<products, FavProductAdapter.FavProductViewHolder> {

    private OnFavProductClickListener listener;
    private List<String> cartProductIds;

    public FavProductAdapter(@NonNull FirestoreRecyclerOptions<products> options, OnFavProductClickListener listener, List<String> cartProductIds) {
        super(options);
        this.listener = listener;
        this.cartProductIds = cartProductIds;
    }

    @Override
    protected void onBindViewHolder(@NonNull FavProductViewHolder holder, int position, @NonNull products model) {
        holder.bind(model, listener, cartProductIds);
    }

    @NonNull
    @Override
    public FavProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_recycler, parent, false);
        return new FavProductViewHolder(view);
    }

    static class FavProductViewHolder extends RecyclerView.ViewHolder {
        ImageButton imageurl;
        TextView nameTextView;
        TextView authorTextView;
        TextView priceTextView;
        ImageView checkImageView;

        public FavProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageurl = itemView.findViewById(R.id.imageurl);
            nameTextView = itemView.findViewById(R.id.name);
            authorTextView = itemView.findViewById(R.id.author);
            priceTextView = itemView.findViewById(R.id.price);
            checkImageView = itemView.findViewById(R.id.check);
        }

        public void bind(products product, OnFavProductClickListener listener, List<String> cartProductIds) {
            Glide.with(itemView.getContext()).load(product.getImageurl()).into(imageurl);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onFavProductClick(product);
                }
            });

            nameTextView.setText(product.getName());
            authorTextView.setText(product.getAuthor());
            priceTextView.setText(String.valueOf(product.getPrice()));

            if (cartProductIds.contains(product.getName())) {
                checkImageView.setVisibility(View.VISIBLE);
            } else {
                checkImageView.setVisibility(View.GONE);
            }
        }
    }

    public interface OnFavProductClickListener {
        void onFavProductClick(products product);
    }
}
