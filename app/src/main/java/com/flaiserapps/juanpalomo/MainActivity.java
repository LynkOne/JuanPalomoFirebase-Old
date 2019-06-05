package com.flaiserapps.juanpalomo;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerRecetasFragment.recetasFragmentInteractionListener {
    private FrameLayout fl;
    private Fragment recyclerRecetasFragment;
    private Fragment acercaDeFragment;
    private Fragment detallesRecetaFragment;
    private FragmentTransaction transaction;
    private FragmentManager fm;
    private Toolbar toolbar;
    private boolean isShown = false;
    private LinearLayout menuLayout;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar) findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);

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
    public void expandirReceta() {
        detallesRecetaFragment=new DetallesRecetaFragment();
        transaction=fm.beginTransaction();
        transaction.replace(fl.getId(), detallesRecetaFragment);
        transaction.addToBackStack("");
        transaction.commit();
    }
}
