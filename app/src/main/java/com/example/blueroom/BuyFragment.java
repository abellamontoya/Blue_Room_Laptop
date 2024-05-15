package com.example.blueroom;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BuyFragment extends Fragment {

    private TextView priceOfProducts;
    private EditText countryEditText, addressEditText, postalCodeEditText,
            cardNumberEditText, expirationDateEditText, securityCodeEditText;
    private TextView finalPriceTextView;

    private Button confirmButton;

    private double totalPriceFromCart;

    public BuyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy, container, false);

        countryEditText = view.findViewById(R.id.countryet);
        addressEditText = view.findViewById(R.id.addresset);
        postalCodeEditText = view.findViewById(R.id.postalcodeet);
        cardNumberEditText = view.findViewById(R.id.cardnumber_edit);
        expirationDateEditText = view.findViewById(R.id.expirationdate_edit);
        securityCodeEditText = view.findViewById(R.id.securitycode_edit);
        confirmButton = view.findViewById(R.id.confirmbutton);
        priceOfProducts = view.findViewById(R.id.priceofprods);
        finalPriceTextView = view.findViewById(R.id.finalprice);

        loadProfileData();

        setUpTextChangeListener();

        confirmButton.setEnabled(false);

        return view;
    }

    private void loadProfileData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userEmail = user.getEmail();

            DocumentReference userDocRef = db.collection("users").document(userEmail);

            userDocRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String country = documentSnapshot.getString("country");
                    String address = documentSnapshot.getString("address");
                    String postalCode = documentSnapshot.getString("postal_code");

                    countryEditText.setText(country);
                    addressEditText.setText(address);
                    postalCodeEditText.setText(postalCode);

                    totalPriceFromCart = calculateTotalPriceFromCart();

                    priceOfProducts.setText(String.format(Locale.getDefault(), "%.2f€", totalPriceFromCart));

                    double finalPrice = calculateFinalPrice(totalPriceFromCart);

                    finalPriceTextView.setText(String.format(Locale.getDefault(), "%.2f€", finalPrice));
                }
            }).addOnFailureListener(e -> {
            });
        }
    }

    private double calculateTotalPriceFromCart() {
        MyApp myApp = (MyApp) requireActivity().getApplication();
        ArrayList<products> cartProducts = myApp.getCartProducts();
        double totalPrice = 0;
        for (products product : cartProducts) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    private double calculateFinalPrice(double totalPrice) {
        return totalPrice + 3.99;
    }

    private void setUpTextChangeListener() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Check if all EditText fields are filled
                boolean allFieldsFilled = !countryEditText.getText().toString().isEmpty() &&
                        !addressEditText.getText().toString().isEmpty() &&
                        !postalCodeEditText.getText().toString().isEmpty() &&
                        !cardNumberEditText.getText().toString().isEmpty() &&
                        !expirationDateEditText.getText().toString().isEmpty() &&
                        !securityCodeEditText.getText().toString().isEmpty();

                confirmButton.setEnabled(allFieldsFilled);
            }
        };

        countryEditText.addTextChangedListener(textWatcher);
        addressEditText.addTextChangedListener(textWatcher);
        postalCodeEditText.addTextChangedListener(textWatcher);
        cardNumberEditText.addTextChangedListener(textWatcher);
        expirationDateEditText.addTextChangedListener(textWatcher);
        securityCodeEditText.addTextChangedListener(textWatcher);
    }
}
