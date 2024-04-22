package com.example.blueroom;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private NavController navController;
    private AppViewModel appViewModel;
    private ImageView photoImageView;
    private FirestoreRecyclerAdapter<products, ProductViewHolder> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        FloatingActionButton miau = view.findViewById(R.id.miau);

        miau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.addProductFragment);
            }
        });



        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        // Initialize RecyclerView with GridLayoutManager
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // 2 columns for grid

        // Set up Firestore query
        Query query = FirebaseFirestore.getInstance().collection("products").orderBy("Name");

        FirestoreRecyclerOptions<products> options = new FirestoreRecyclerOptions.Builder<products>()
                .setQuery(query, products.class)
                .setLifecycleOwner(this)
                .build();

        Log.d("QUERY_DEBUG", query.get().toString());
        adapter = new FirestoreRecyclerAdapter<products, ProductViewHolder>(options) {
            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_recyclerview, parent, false);
                return new ProductViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull products model) {
                Log.d("BIND_DEBUG", "Binding item: " + model.getName()); // Registro para depuración
                holder.bind(model);
            }
        };

        recyclerView.setAdapter(adapter);

        // Load user photo into photoImageView
        photoImageView = view.findViewById(R.id.image); // Assuming you have an ImageView with this ID
        Uri photoUri = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
        if (photoUri != null && photoImageView != null) { // Check if photoImageView is not null
            String url = photoUri.toString();
            Glide.with(requireContext()).load(url).circleCrop().into(photoImageView);
        } else {
            // Load default placeholder image
            if (photoImageView != null) {
                photoImageView.setImageResource(R.drawable.user);
            }
        }
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

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageurl;
        TextView name;
        TextView author;
        TextView price;
        TextView quantity;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageurl = itemView.findViewById(R.id.imageurl);
            name = itemView.findViewById(R.id.name);
            author = itemView.findViewById(R.id.author);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
        }

        public void bind(products product) {
            Glide.with(itemView.getContext()).load(product.getImageURL()).into(imageurl);
            name.setText(product.getName());
            author.setText(product.getAuthor());
            price.setText(String.valueOf(product.getPrice()));
            quantity.setText(String.valueOf(product.getQuantity()));
        }
    }
}