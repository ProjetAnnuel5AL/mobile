package com.lechampalamaison.fragement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.lechampalamaison.R;
import com.lechampalamaison.activity.HomeActivity;
import com.lechampalamaison.api.model.Login;
import com.lechampalamaison.api.model.Update;
import com.lechampalamaison.api.model.apiResponse.AuthResponse;
import com.lechampalamaison.api.model.apiResponse.UpdateResponse;
import com.lechampalamaison.api.service.UserClient;
import com.lechampalamaison.api.utils.Configuration;

import java.util.regex.Pattern;

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
 * {@link UpdatePwdFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdatePwdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatePwdFragment extends Fragment {

    private static final String TAG = "UpdatePwdFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String PREFS_NAME_USER = "USER";

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


    @BindView(R.id.input_password_old) EditText _oldPwdText;
    @BindView(R.id.input_password_new) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword_new) EditText _reEnterPasswordText;
    @BindView(R.id.btn_update_pwd) Button _btn_update_pwd;


    public UpdatePwdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdatePwdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdatePwdFragment newInstance(String param1, String param2) {
        UpdatePwdFragment fragment = new UpdatePwdFragment();
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
        View view = inflater.inflate(R.layout.fragment_update_pwd, container, false);
        ButterKnife.bind(this, view);

        progressDialog = new ProgressDialog(getContext(),
                R.style.AppTheme_Dark_Dialog);

        _btn_update_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePwd();
            }
        });

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void updatePwd(){
        Log.d(TAG, "updatePwd");

        if (!validate()) {
            onUpdatePwdFailed();
            return;
        }


        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Modification du mot de passe ...");
        progressDialog.show();

        String odlPwd = _oldPwdText.getText().toString();
        String password = _passwordText.getText().toString();

        SharedPreferences sharedPref = getContext().getSharedPreferences(PREFS_NAME_USER ,Context.MODE_PRIVATE);

        String token = sharedPref.getString(getString(R.string.token_key), "");
        String loginUser = sharedPref.getString(getString(R.string.login_key), "");

        Login login = new Login(loginUser, odlPwd);
        Call<AuthResponse> call = userClient.login(login);

        Update update = new Update(loginUser, token, password, null);
        Call<UpdateResponse> call2 = userClient.update(update);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if(response.body().getCode() == 0){

                    call2.enqueue(new Callback<UpdateResponse>() {
                        @Override
                        public void onResponse(Call<UpdateResponse> call2, Response<UpdateResponse> response) {
                            if(response.body().getCode() == 0) {
                                onUpdatePwdSuccess();
                                _oldPwdText.setText("");
                                _passwordText.setText("");
                                _reEnterPasswordText.setText("");
                            }else{
                                Toast.makeText(getContext(), "Erreur lors de la modification de votre mot de passe. Veuillez recommencer", Toast.LENGTH_SHORT).show();
                                onUpdatePwdFailed();
                            }

                        }

                        @Override
                        public void onFailure(Call<UpdateResponse> call2, Throwable t) {
                            Toast.makeText(getContext(), "Erreur lors de la modification de votre mot de passe. Veuillez recommencer", Toast.LENGTH_SHORT).show();
                            onUpdatePwdFailed();
                        }

                    });

                }else{
                    Toast.makeText(getContext(), "L'ancien mot de passe est incorrect.", Toast.LENGTH_SHORT).show();
                    onUpdatePwdFailed();
                }
            }
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(getContext(), "L'ancien mot de passe est incorrect.", Toast.LENGTH_SHORT).show();
                onUpdatePwdFailed();
            }
        });


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    public void onUpdatePwdSuccess() {

        progressDialog.dismiss();
        getActivity().setResult(getActivity().RESULT_OK, null);
        Toast.makeText(getContext(), "Mot de passe modifié avec succès.", Toast.LENGTH_LONG).show();
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onUpdatePwdFailed() {


    }

    public boolean validate() {
        boolean valid = true;

        String oldPwd = _oldPwdText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (oldPwd.isEmpty()) {
            _oldPwdText.setError("Saisisez votre mot de passe actuel");
            valid = false;
        } else {
            _oldPwdText.setError(null);
        }

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[&\"'(\\-_)=+?\\.^$])(?=\\S+$).{8,}$";


        if (password.isEmpty() || password.length() < 8 || Pattern.matches(regex, password) == false ) {
            _passwordText.setError("Mot de passe invalide : 8 caractères minimum, au moins une majuscule et une minuscule, un chiffre et un caractère spéciale ( parmis les suivants : &\\\"'(-_)=+?.^$ ) sont requis ! ");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Les mots de passe saisient ne sont pas identiques.");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

}
