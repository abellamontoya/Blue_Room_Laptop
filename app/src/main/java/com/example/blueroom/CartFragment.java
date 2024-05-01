package com.example.blueroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private ArrayList<products> cartProducts;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(ArrayList<products> cartProducts) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putSerializable("cartProducts", cartProducts);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cartProducts = (ArrayList<products>) getArguments().getSerializable("cartProducts");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.cartFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (cartProducts != null) {
            adapter = new CartAdapter(cartProducts);
            recyclerView.setAdapter(adapter);
        }
    }
}
