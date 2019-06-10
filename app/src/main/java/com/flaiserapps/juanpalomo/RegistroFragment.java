package com.flaiserapps.juanpalomo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.flaiserapps.juanpalomo.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;


public class RegistroFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private registroInteractionListener interfaz;
    private EditText usuario, email, password;
    private Button b_registro;
    private FirebaseAuth mAuth;
    public RegistroFragment() {
        // Required empty public constructor
    }


    public static RegistroFragment newInstance(String param1, String param2) {
        RegistroFragment fragment = new RegistroFragment();
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
        View v =inflater.inflate(R.layout.fragment_registro, container, false);
        usuario=(EditText)v.findViewById(R.id.editText_registro_usuario);
        email=(EditText)v.findViewById(R.id.editText_registro_email);
        password=(EditText)v.findViewById(R.id.editText_registro_password);
        b_registro=(Button)v.findViewById(R.id.button_registro_registrarse);
        mAuth = FirebaseAuth.getInstance();
        b_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().isEmpty()){
                    email.setError("El email no puede estar en blanco");
                    email.requestFocus();
                }
                else{
                    if (!isValidEmail(email.getText().toString())){
                        email.setError("Direccón de correo no válida");
                        email.requestFocus();
                    }else{
                        if(password.getText().toString().isEmpty()){
                            password.setError("La contraseña no puede estar en blanco");
                            password.requestFocus();
                        }else{
                            if(password.getText().toString().length()<6){
                                password.setError("Longitud mínima de 6 caracteres");
                                password.requestFocus();
                            }else{

                                registrarUsuario(email.getText().toString(), password.getText().toString());
                            }

                        }

                    }

                }

            }
        });
        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof registroInteractionListener) {
            interfaz = (registroInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement recetasFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public void registrarUsuario(final String email, final String password){
        interfaz.registrarUsuario(email, password, usuario.getText().toString());
    }
    public interface registroInteractionListener{
        void registrarUsuario(String email, String password, String usuario);
    }


}
