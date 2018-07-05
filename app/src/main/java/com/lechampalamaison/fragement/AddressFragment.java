package com.lechampalamaison.fragement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lechampalamaison.R;
import com.lechampalamaison.api.model.User;
import com.lechampalamaison.api.model.apiResponse.FindAddressReponse;
import com.lechampalamaison.api.model.apiResponse.UpdateResponse;
import com.lechampalamaison.api.service.UserClient;
import com.lechampalamaison.api.utils.Configuration;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddressFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddressFragment extends Fragment {

    public static final String PREFS_NAME_USER = "USER";
    private static final String TAG = "addressFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    User userSave = new User();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProgressDialog progressDialog;
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);


    @BindView(R.id.input_lastName)
    EditText _lastName;
    @BindView(R.id.input_firstName)
    EditText _firstName;
    @BindView(R.id.input_address)
    EditText _address;
    @BindView(R.id.checkBoxSexH)
    CheckBox _checkBoxSexH;
    @BindView(R.id.checkBoxSexF)
    CheckBox _checkBoxSexF;
    @BindView(R.id.input_city)
    EditText _city;
    @BindView(R.id.input_cp)
    EditText _cp;
    @BindView(R.id.btn_save_address)
    Button _btnSaveAddress;
    @BindView(R.id.isSaved)
    TextView _isSavedView;


    public AddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddressFragment newInstance(String param1, String param2) {
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address, container, false);

        ButterKnife.bind(this, view);

        progressDialog = new ProgressDialog(getContext(),
                R.style.AppTheme_Dark_Dialog);

        _btnSaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAddress();
            }
        });

        _checkBoxSexH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _checkBoxSexF.setChecked(false);
            }
        });
        _checkBoxSexF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _checkBoxSexH.setChecked(false);
            }
        });


        loadAddress();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: User argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void loadAddress(){
        //On charge l'adresse mail
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Récupération de votre adresse...");
        progressDialog.show();

        SharedPreferences sharedPref = getContext().getSharedPreferences(PREFS_NAME_USER ,Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token_key), "");
        String loginUser = sharedPref.getString(getString(R.string.login_key), "");

        Call<FindAddressReponse> call = userClient.findAddress(loginUser, token);

        call.enqueue(new Callback<FindAddressReponse>() {
            @Override
            public void onResponse(Call<FindAddressReponse> call, Response<FindAddressReponse> response) {
                if(response.body().getCode() == 0){
                    if(response.body().getResult().getLastNameUser() == null ||response.body().getResult().getLastNameUser().equals("")){
                        _isSavedView.setText("Pas encore d'adresse de livraison enregistré. Remplissez le formulaire ci-dessous pour en enregistrer une.");
                    }else{

                        userSave.setLastNameUser(response.body().getResult().getLastNameUser());
                        userSave.setFirstNameUser(response.body().getResult().getFirstNameUser());
                        userSave.setAddressUser(response.body().getResult().getAddressUser());
                        userSave.setCityUser(response.body().getResult().getCityUser());
                        userSave.setCpUser(response.body().getResult().getCpUser());
                        userSave.setSexUser(response.body().getResult().getSexUser());

                        _lastName.setText(userSave.getLastNameUser());
                        _firstName.setText(userSave.getFirstNameUser());
                        _address.setText(userSave.getAddressUser());
                        _city.setText(userSave.getCityUser());
                        _cp.setText(userSave.getCpUser());


                        if(response.body().getResult().getSexUser().equals("H")){
                            _checkBoxSexH.setChecked(true);

                        }else{
                            _checkBoxSexF.setChecked(true);
                        }

                    }
                    progressDialog.dismiss();
                }else{
                    Toast.makeText(getContext(), "Erreur lors du chargment de l'adresse", Toast.LENGTH_SHORT).show();
                    onUpdateAddressFailed();
                }
            }
            @Override
            public void onFailure(Call<FindAddressReponse> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur lors du chargment de l'adresse", Toast.LENGTH_SHORT).show();
                onUpdateAddressFailed();
            }
        });

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    public void updateAddress(){
        Log.d(TAG, "updatePwd");

        if (!validate()) {
            onUpdateAddressFailed();
            return;
        }

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Modification de l'adresse de livraison ...");
        progressDialog.show();

        String lastName = _lastName.getText().toString();
        String firstName = _firstName.getText().toString();
        String address = _address.getText().toString();
        String city = _city.getText().toString();
        String cp = _cp.getText().toString();
        String sex ="H";
        if(_checkBoxSexF.isChecked()){
            sex ="F";
        }

        SharedPreferences sharedPref = getContext().getSharedPreferences(PREFS_NAME_USER ,Context.MODE_PRIVATE);

        String token = sharedPref.getString(getString(R.string.token_key), "");
        String loginUser = sharedPref.getString(getString(R.string.login_key), "");

        User user = new User(loginUser, token, null, null, lastName, firstName, sex, address, city, cp);
        Call<UpdateResponse> call = userClient.update(user);

        call.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                if(response.body().getCode() == 0){
                    onUpdateAddressSuccess();
                    _isSavedView.setText("");
                    userSave.setLastNameUser(lastName);
                    userSave.setFirstNameUser(firstName);
                    userSave.setAddressUser(address);
                    userSave.setCityUser(city);
                    userSave.setCpUser(cp);

                    if(_checkBoxSexF.isChecked()){
                        userSave.setSexUser("H");
                    }else{
                        userSave.setSexUser("F");
                    }

                }else{
                    Toast.makeText(getContext(), "Erreur lors de la modification de l'adresse de livraison. Veuillez recommencer", Toast.LENGTH_SHORT).show();
                    onUpdateAddressFailed();
                }
            }
            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur lors de la modification de l'adresse de livraison. Veuillez recommencer", Toast.LENGTH_SHORT).show();
                onUpdateAddressFailed();
            }
        });


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    public void onUpdateAddressSuccess() {
        progressDialog.dismiss();
        getActivity().setResult(getActivity().RESULT_OK, null);
        Toast.makeText(getContext(), "Adresse de livraison enregistrée avec succès.", Toast.LENGTH_LONG).show();
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onUpdateAddressFailed() {


    }

    public boolean validate() {
        boolean valid = true;

        String lastName = _lastName.getText().toString();
        String firstName = _firstName.getText().toString();
        String address = _address.getText().toString();
        String city = _city.getText().toString();
        String cp = _cp.getText().toString();
        int cpInt = Integer.parseInt(cp);
        if(userSave.getLastNameUser() != null){
            if(userSave.getLastNameUser().equals(lastName) && userSave.getFirstNameUser().equals(firstName) && userSave.getAddressUser().equals(address)
                    && userSave.getCityUser().equals(city) && userSave.getCpUser().equals(cp)){
                Toast.makeText(getContext(), "Aucune modification détectée.", Toast.LENGTH_SHORT).show();
                valid = false;
            }

        }

        if (lastName.isEmpty()) {
            _lastName.setError("Veuillez entrer un nom.");
            valid = false;
        } else {
            _lastName.setError(null);
        }
        if (firstName.isEmpty()) {
            _firstName.setError("Veuillez entrer un prénom.");
            valid = false;
        } else {
            _firstName.setError(null);
        }
        if (address.isEmpty()) {
            _address.setError("Veuillez entrer une adresse.");
            valid = false;
        } else {
            _address.setError(null);
        }
        if (city.isEmpty()) {
            _city.setError("Veuillez entrer une ville.");
            valid = false;
        } else {
            _city.setError(null);
        }
        if (cp.isEmpty() || cp.length()>5) {
            _cp.setError("Veuillez entrer un code postal valide.");
            valid = false;
        } else {
            _cp.setError(null);
        }

        if(_checkBoxSexF.isChecked() == false && _checkBoxSexH.isChecked() == false){
            _checkBoxSexF.setError("Veuillez renseigner votre sexe.");
            valid = false;
        }else{
            _checkBoxSexF.setError(null);
        }

        return valid;
    }
}
