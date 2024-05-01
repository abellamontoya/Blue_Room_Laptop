package com.example.blueroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

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
        products product = cartProducts.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, authorTextView, priceTextView, quantityTextView;
        Button deleteButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.Name);
            authorTextView = itemView.findViewById(R.id.Author);
            priceTextView = itemView.findViewById(R.id.Price);
            quantityTextView = itemView.findViewById(R.id.Quantity);
        }

        public void bind(products product) {
            nameTextView.setText(product.getName());
            authorTextView.setText(product.getAuthor());
            priceTextView.setText(String.valueOf(product.getPrice()));
            quantityTextView.setText(String.valueOf(product.getQuantity()));

            // Aquí puedes implementar la lógica para eliminar el producto del carrito
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Implementa la lógica para eliminar el producto del carrito aquí
                }
            });
        }
    }
}
