package com.example.blueroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private ArrayList<products> cartProducts;
    private RecyclerView recyclerView;
    private CartAdapter adapter;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApp myApp = (MyApp) requireActivity().getApplication();
        cartProducts = myApp.getCartProducts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.cartFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CartAdapter(cartProducts);
        recyclerView.setAdapter(adapter);

        return view;
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, authorTextView, priceTextView, quantityTextView;
        ImageView imageView;
        Button deleteButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.albumname);
            authorTextView = itemView.findViewById(R.id.authorname);
            priceTextView = itemView.findViewById(R.id.priceoftheitem);
            imageView = itemView.findViewById(R.id.albumcover);
            quantityTextView = itemView.findViewById(R.id.quantity);
            deleteButton = itemView.findViewById(R.id.deleteitem);
        }

        public void bind(products product) {
            nameTextView.setText(product.getName());
            authorTextView.setText(product.getAuthor());
            priceTextView.setText(String.valueOf(product.getPrice()));

            // Load image from URL into ImageView using Glide
            Glide.with(itemView.getContext()).load(product.getImageurl()).into(imageView);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Implement delete logic here
                }
            });
        }


    }

    class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

        private ArrayList<products> cartProducts;

        public CartAdapter(ArrayList<products> cartProducts) {
            this.cartProducts = cartProducts;
        }

        @NonNull
        @Override
        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recyclerview, parent, false);
            return new CartViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
            holder.bind(cartProducts.get(position));
        }

        @Override
        public int getItemCount() {
            return cartProducts.size();
        }
    }
}
