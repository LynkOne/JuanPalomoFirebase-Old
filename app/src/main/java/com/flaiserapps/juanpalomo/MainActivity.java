package com.flaiserapps.juanpalomo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.flaiserapps.juanpalomo.model.Ingredientes;
import com.flaiserapps.juanpalomo.model.Receta;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerRecetasFragment.recetasFragmentInteractionListener, LoginFragment.LoginFragmentInteractionListener, RegistroFragment.registroInteractionListener {
    private FrameLayout fl;
    private Fragment recyclerRecetasFragment;
    private Fragment acercaDeFragment;
    private Fragment detallesRecetaFragment;
    private Fragment loginFragment;
    private Fragment registroFragment;
    private FragmentTransaction transaction;
    private FragmentManager fm;
    private Toolbar toolbar;
    private boolean isShown = false;
    private LinearLayout menuLayout;
    private DrawerLayout drawer;
    private FirebaseAuth mAuth;
    private FirebaseUser fbUser;
    FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar) findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user_aux = FirebaseAuth.getInstance().getCurrentUser();
                if(user_aux!=null){
                    fbUser=user_aux;

                }
            }
        };

        //menuLayout=(LinearLayout) findViewById(R.id.);
        //final DrawerLayout
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fm=getSupportFragmentManager();
        transaction=fm.beginTransaction();
        fl=(FrameLayout) findViewById(R.id.fragment_main);
        recyclerRecetasFragment= new RecyclerRecetasFragment();
        acercaDeFragment=new AcercaDe();

        transaction.replace(fl.getId(), recyclerRecetasFragment);
        transaction.commit();

        navigationView.getMenu().getItem(0).setChecked(true);
    }


    private void burgerMenuIconClick(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShown) {
                    menuLayout.setVisibility(View.VISIBLE);
                    menuLayout.animate().translationY(menuLayout.getHeight());
                    isShown = true;
                } else {
                    menuLayout.animate().translationY(menuLayout.getHeight() * (-1));
                    isShown = false;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        transaction=fm.beginTransaction();
        switch (menuItem.getItemId()){

            case R.id.menu_explora:
                //Toast.makeText(this, "pulsado menu explorar recetas", Toast.LENGTH_SHORT).show();
                transaction.replace(fl.getId(), recyclerRecetasFragment);
                transaction.addToBackStack("");
                transaction.commit();
                vaciarSeleccionNavView();
                navigationView.getMenu().getItem(0).setChecked(true);
                break;
            case R.id.menu_recetas_ing:
                //Toast.makeText(this, "pulsado menu recetas por ingredientes", Toast.LENGTH_SHORT).show();
                transaction.replace(fl.getId(), recyclerRecetasFragment);
                transaction.addToBackStack("");
                transaction.commit();
                vaciarSeleccionNavView();
                navigationView.getMenu().getItem(1).setChecked(true);
                break;
            case R.id.menu_almacen:
                //Toast.makeText(this, "pulsado menu almacen", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_configuracion:
                break;
            case R.id.menu_acercade:
                transaction.replace(fl.getId(), acercaDeFragment);
                transaction.addToBackStack("");
                transaction.commit();
                vaciarSeleccionNavView();
                navigationView.getMenu().getItem(4).setChecked(true);

                break;
            default:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
    private void vaciarSeleccionNavView(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(false);
        navigationView.getMenu().getItem(1).setChecked(false);
        navigationView.getMenu().getItem(2).setChecked(false);
        navigationView.getMenu().getItem(3).setChecked(false);
        navigationView.getMenu().getItem(4).setChecked(false);

    }

    @Override
    public void expandirRecetaFragment(Receta receta, Ingredientes ingredientes) {
        detallesRecetaFragment=DetallesRecetaFragment.newInstance(receta, ingredientes);
        transaction=fm.beginTransaction();
        transaction.replace(fl.getId(), detallesRecetaFragment);
        transaction.addToBackStack("");
        transaction.commit();

        
    }

    @Override
    public void fab_iniciarLogin() {
        loginFragment=new LoginFragment();
        transaction=fm.beginTransaction();
        transaction.replace(fl.getId(), loginFragment);
        transaction.addToBackStack("");
        transaction.commit();
    }

    private void entrarFirebase(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("hectorr", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("hectorr", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    @Override
    public void registro() {
        registroFragment=new RegistroFragment();
        transaction=fm.beginTransaction();
        transaction.replace(fl.getId(), registroFragment);
        transaction.addToBackStack("");
        transaction.commit();
    }

    @Override
    public void login(String email, String password) {
        entrarFirebase(email,password);
    }
    @Override
    public void registrarUsuario(final String email, final String password, final String usuario){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("hectorr", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid=user.getUid();
                            Usuario usuario_auxiliar=new Usuario(usuario);
                            //Apuntamos al nodo usuarios
                            DatabaseReference dbr= FirebaseDatabase.getInstance().getReference("usuarios");
                            Log.d("hecotrr", "Database Reference: "+dbr.toString());
                            //Si no existe lo crea, si existe lo machaca
                            dbr.child(uid).setValue(usuario_auxiliar);
                            //Registro completo


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("hectorr", "createUserWithEmail:failure", task.getException());
                        }

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FirebaseAuthException feAux = (FirebaseAuthException) e;
                        String errcode=feAux.getErrorCode();

                    }
                });
    }
}
