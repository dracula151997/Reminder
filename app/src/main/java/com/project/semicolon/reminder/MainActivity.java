package com.project.semicolon.reminder;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.project.semicolon.reminder.databinding.DrawerLayoutBind;
import com.project.semicolon.reminder.databinding.MainActivityBind;
import com.project.semicolon.reminder.utils.FormatterUtil;

public class MainActivity extends AppCompatActivity implements
        NavController.OnDestinationChangedListener {
    private static final int REQ_STORAGE_PERMISSION = 1000;
    private MainActivityBind mainActivityBind;
    private DrawerLayoutBind drawerLayoutBind;
    private int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        drawerLayoutBind = DrawerLayoutBind.inflate(getLayoutInflater());

        setSupportActionBar(mainActivityBind.toolbar);

        NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment);
        controller.addOnDestinationChangedListener(this);


        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (savedInstanceState == null) {
            Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.mainFragment);

        }

        checkStoragePermission();
        initDrawerLayout();


    }

    private void initDrawerLayout() {
        drawerLayoutBind.date.setText(FormatterUtil.formatDate());

        mainActivityBind.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityBind.drawerLayout.openDrawer(GravityCompat.START);
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (mainActivityBind.drawerLayout
                .isDrawerOpen(mainActivityBind.drawerLayout)) {
            mainActivityBind.drawerLayout.closeDrawers();
            return;
        } else {
            counter++;
            if (counter < 2) {
                Toast.makeText(this, getString(R.string.press_again), Toast.LENGTH_SHORT).show();
            } else if (counter == 2) {
                super.onBackPressed();
                finish();

            }

        }
    }

    private void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {

                new MaterialAlertDialogBuilder(this)
                        .setTitle(getString(R.string.permission))
                        .setMessage(getString(R.string.permission_msg))
                        .setPositiveButton(R.string.request, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //request permission
                                dialogInterface.dismiss();
                                requestPermission();
                            }
                        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //cancel
                        dialogInterface.dismiss();
                        displayPermissionError();
                    }
                }).show();


            }
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_STORAGE_PERMISSION);
        }
    }

    private void displayPermissionError() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.permission_error))
                .setMessage(getString(R.string.permission_error_msg))
                .setPositiveButton(getString(R.string.request), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        requestPermission();
                    }
                }).setNegativeButton(getString(R.string.cont), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination,
                                     @Nullable Bundle arguments) {
        switch (destination.getId()) {
            case R.id.noteFragment:
                mainActivityBind.menu.setImageResource(R.drawable.ic_arrow_back_black_24dp);
                break;
        }

    }
}
