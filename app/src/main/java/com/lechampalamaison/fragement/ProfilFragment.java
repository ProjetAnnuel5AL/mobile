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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lechampalamaison.R;
import com.lechampalamaison.api.model.Update;
import com.lechampalamaison.api.model.apiResponse.FindEmailResponse;
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
 * {@link ProfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {
    public static final String PREFS_NAME_USER = "USER";
    private static final String TAG = "profilFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProgressDialog progressDialog;
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);


    @BindView(R.id.input_mail_new) EditText _mailNewText;
    @BindView(R.id.input_reEnterMail) EditText _reEnterMailText;
    @BindView(R.id.btn_update_mail)Button _btn_update_mail;

    @BindView(R.id.loginLoadTextView)
    TextView _loginLoadTextView;
    @BindView(R.id.mailLoadTextView) TextView _mailLoadTextView;


    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
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
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        ButterKnife.bind(this, view);

        progressDialog = new ProgressDialog(getContext(),
                R.style.AppTheme_Dark_Dialog);

        _btn_update_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMail();
            }
        });

        loadProfil();

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void loadProfil(){
        //On charge l'adresse mail
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Chargement du profil...");
        progressDialog.show();

        SharedPreferences sharedPref = getContext().getSharedPreferences(PREFS_NAME_USER ,Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token_key), "");
        String loginUser = sharedPref.getString(getString(R.string.login_key), "");

        Call<FindEmailResponse> call = userClient.findEmail(loginUser, token);

        call.enqueue(new Callback<FindEmailResponse>() {
            @Override
            public void onResponse(Call<FindEmailResponse> call, Response<FindEmailResponse> response) {
                if(response.body().getCode() == 0){
                    _loginLoadTextView.setText(loginUser);
                    _mailLoadTextView.setText(response.body().getResult().getEmailUser());
                    progressDialog.dismiss();
                }else{
                    Toast.makeText(getContext(), "Erreur lors du chargment du profil", Toast.LENGTH_SHORT).show();
                    onUpdateMailFailed();
                }
            }
            @Override
            public void onFailure(Call<FindEmailResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur lors du chargment du profil", Toast.LENGTH_SHORT).show();
                onUpdateMailFailed();
            }
        });

        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    progressDialog.dismiss();
                }
                }, 2000);
    }

    public void updateMail(){
        Log.d(TAG, "updatePwd");

        if (!validate()) {
            onUpdateMailFailed();
            return;
        }

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Modification du mot de passe ...");
        progressDialog.show();

        String mail = _mailNewText.getText().toString();

        SharedPreferences sharedPref = getContext().getSharedPreferences(PREFS_NAME_USER ,Context.MODE_PRIVATE);

        String token = sharedPref.getString(getString(R.string.token_key), "");
        String loginUser = sharedPref.getString(getString(R.string.login_key), "");

        Update update = new Update(loginUser, token, null, mail);
        Call<UpdateResponse> call = userClient.update(update);

        call.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                if(response.body().getCode() == 0){
                    onUpdateMailSuccess();
                    _mailLoadTextView.setText(_mailNewText.getText());
                    _mailNewText.setText("");
                    _reEnterMailText.setText("");
                }else{
                    Toast.makeText(getContext(), "Erreur lors de la modification de l'adresse mail. Veuillez recommencer", Toast.LENGTH_SHORT).show();
                    onUpdateMailFailed();
                }
            }
            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur lors de la modification de l'adresse mail. Veuillez recommencer", Toast.LENGTH_SHORT).show();
                onUpdateMailFailed();
            }
        });


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    public void onUpdateMailSuccess() {
        progressDialog.dismiss();
        getActivity().setResult(getActivity().RESULT_OK, null);
        Toast.makeText(getContext(), "Adresse mail modifié avec succès.", Toast.LENGTH_LONG).show();
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onUpdateMailFailed() {


    }

    public boolean validate() {
        boolean valid = true;

        String mail = _mailNewText.getText().toString();
        String reEnterMail = _reEnterMailText.getText().toString();

        if (mail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            _mailNewText.setError("Veuillez entrer une adresse mail valide.");
            valid = false;
        } else {
            _mailNewText.setError(null);
        }

        if (reEnterMail.isEmpty() || !(reEnterMail.equals(mail))) {
            _reEnterMailText.setError("Les adresses mail saisient ne sont pas identiques.");
            valid = false;
        } else {
            _reEnterMailText.setError(null);
        }

        return valid;
    }
}
