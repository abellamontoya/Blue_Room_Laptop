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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements ProductAdapter.OnProductClickListener {
    private RecyclerView recyclerView;
    private NavController navController;
    private AppViewModel appViewModel;
    private ImageView photoImageView;
    private ProductAdapter adapter;
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        CheckBox checkVinyl = view.findViewById(R.id.checkVinyl);
        CheckBox checkCd = view.findViewById(R.id.checkCd);

        SearchView searchView = view.findViewById(R.id.searchbar);
        // Your searchView listener code

        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.sort_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                handleSorting(selectedOption);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        Query query = FirebaseFirestore.getInstance().collection("products").orderBy("name");

        FirestoreRecyclerOptions<products> options = new FirestoreRecyclerOptions.Builder<products>()
                .setQuery(query, products.class)
                .setLifecycleOwner(this)
                .build();

        adapter = new ProductAdapter(options, this);
        recyclerView.setAdapter(adapter);

        photoImageView = view.findViewById(R.id.image);
        Uri photoUri = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
        if (photoUri != null && photoImageView != null) {
            String url = photoUri.toString();
            Glide.with(requireContext()).load(url).circleCrop().into(photoImageView);
        } else {
            if (photoImageView != null) {
                photoImageView.setImageResource(R.drawable.user);
            }
        }
    }

    private void handleSorting(String selectedOption) {
        Query query = FirebaseFirestore.getInstance().collection("products");

        switch (selectedOption) {
            case "Price Up":
                query = query.orderBy("price", Query.Direction.ASCENDING);
                break;
            case "Price Down":
                query = query.orderBy("price", Query.Direction.DESCENDING);
                break;
            case "Name":
                query = query.orderBy("name");
                break;
            case "Author":
                query = query.orderBy("author");
                break;
            default:
                break;
        }

        FirestoreRecyclerOptions<products> options = new FirestoreRecyclerOptions.Builder<products>()
                .setQuery(query, products.class)
                .setLifecycleOwner(this)
                .build();

        adapter.updateOptions(options);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    private void searchFirestore(String searchText) {
        Query baseQuery = FirebaseFirestore.getInstance().collection("products");

        if (searchText != null && !searchText.trim().isEmpty()) {
            String queryText = searchText.trim().toLowerCase();

            // Create a query to search documents where the name_lowercase field contains the search string
            baseQuery = baseQuery.whereArrayContains("name_lowercase", queryText);
        }

        FirestoreRecyclerOptions<products> options = new FirestoreRecyclerOptions.Builder<products>()
                .setQuery(baseQuery, products.class)
                .setLifecycleOwner(this)
                .build();

        adapter.updateOptions(options);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    public void onProductClick(products product) {
        Bundle bundle = new Bundle();
        bundle.putString("author", product.getAuthor());
        bundle.putString("imageurl", product.getImageurl());
        bundle.putString("name", product.getName());
        bundle.putInt("date", product.getDate());
        bundle.putFloat("price", product.getPrice());
        bundle.putString("type", product.getType()); // Get the product type
        bundle.putStringArrayList("tag", new ArrayList<>(product.getTag())); // Get the product tags list

        navController.navigate(R.id.showProduct, bundle);
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageurl;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageurl = itemView.findViewById(R.id.imageurl);
        }

        public void bind(products product, ProductAdapter.OnProductClickListener listener) {
            Glide.with(itemView.getContext()).load(product.getImageurl()).into(imageurl);
            imageurl.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onProductClick(product);
                }
            });
        }
    }
}
