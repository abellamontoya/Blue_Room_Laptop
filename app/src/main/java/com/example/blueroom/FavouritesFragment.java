package com.example.blueroom;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class FavouritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private SharedPreferences favoritesPreferences;
    private ProductAdapter adapter;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoritesPreferences = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.fav_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // Cambiar a GridLayoutManager con 2 columnas

        loadFavorites();
    }


    private void loadFavorites() {
        // Get the list of favorite product IDs from SharedPreferences
        ArrayList<String> favoriteIds = new ArrayList<>();
        for (String key : favoritesPreferences.getAll().keySet()) {
            if (favoritesPreferences.getBoolean(key, false)) {
                favoriteIds.add(key);
            }
        }

        // Construct a Firestore query to fetch favorite products
        Query query = FirebaseFirestore.getInstance().collection("products")
                .whereIn("name", favoriteIds);

        // Set up FirestoreRecyclerOptions
        FirestoreRecyclerOptions<products> options = new FirestoreRecyclerOptions.Builder<products>()
                .setQuery(query, products.class)
                .setLifecycleOwner(this)
                .build();

        // Initialize the adapter with the options and set it to the RecyclerView
        adapter = new ProductAdapter(options, new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(products product) {
                // Handle click event if needed
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}
