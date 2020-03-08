package com.project.semicolon.reminder.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.project.semicolon.reminder.NoteActivity;
import com.project.semicolon.reminder.R;
import com.project.semicolon.reminder.adapters.GenericAdapter;
import com.project.semicolon.reminder.adapters.SwipeController;
import com.project.semicolon.reminder.database.DatabaseHelper;
import com.project.semicolon.reminder.database.entity.Category;
import com.project.semicolon.reminder.databinding.CategoryDialogBind;
import com.project.semicolon.reminder.databinding.MainFragmentBind;
import com.project.semicolon.reminder.listeners.OnListItemClickListener;
import com.project.semicolon.reminder.utils.AppExecutors;
import com.project.semicolon.reminder.utils.Logger;
import com.project.semicolon.reminder.utils.SharedHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements OnListItemClickListener,
        SwipeController.OnRecyclerViewSwipedListener {
    private static final String TAG = "MainFragment";
    private MainFragmentBind mainFragmentBind;
    private DatabaseHelper db;
    private int categoryTheme = Category.THEME_RED;
    private Category categoryModel;
    private GenericAdapter<Category> categoryAdapter;
    private List<Category> categoryList = new ArrayList<>();


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DatabaseHelper.getInstance(getContext());
        categoryModel = new Category();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainFragmentBind = MainFragmentBind.inflate(inflater, container, false);
        return mainFragmentBind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainFragmentBind.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCategoryDialog(R.string.new_category, R.string.create,
                        null,
                        -1);

            }
        });
        categoryAdapter = new GenericAdapter<>(R.layout.list_item_category);
        SwipeController swipeController = new SwipeController(getContext(), categoryAdapter);
        swipeController.setOnRecyclerViewSwipedListener(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(mainFragmentBind.categoryRecycler);


        categoryAdapter.setOnListItemClickListener(this);
        mainFragmentBind.categoryRecycler.setAdapter(categoryAdapter);
        fetchCategories();
    }

    private void fetchCategories() {
        db.categoryDao().getAll().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryList.clear();
                categoryList.addAll(categories);

                if (!categories.isEmpty()) {
                    mainFragmentBind.emptyState.setVisibility(View.GONE);
                    categoryAdapter.setItems(categoryList);
                    return;

                }

                mainFragmentBind.emptyState.setVisibility(View.VISIBLE);


            }
        });
    }

    private void displayCategoryDialog(@StringRes int title, @StringRes int positiveText,
                                       final Category category, final int position) {

        final CategoryDialogBind dialogBind = CategoryDialogBind.inflate(getLayoutInflater());
        if (category != null) {
            dialogBind.title.setText(category.getTitle());
            setCategoryTheme(dialogBind, category.getTheme());
        } else {
            dialogBind.title.setText("");
            setCategoryTheme(dialogBind, categoryTheme);

        }

        dialogBind.colorRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryTheme(dialogBind, Category.THEME_RED);
            }
        });

        dialogBind.colorTeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryTheme(dialogBind, Category.THEME_TEAL);
            }
        });

        dialogBind.colorBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryTheme(dialogBind, Category.THEME_BLUE);
            }
        });

        dialogBind.colorGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryTheme(dialogBind, Category.THEME_GREEN);
            }
        });

        dialogBind.colorAmber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryTheme(dialogBind, Category.THEME_AMBER);
            }
        });

        dialogBind.colorCyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryTheme(dialogBind, Category.THEME_CYAN);
            }
        });


        final AlertDialog dialog = new MaterialAlertDialogBuilder(getContext())
                .setTitle(title)
                .setView(dialogBind.getRoot())
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = dialogBind.title.getText().toString();
                        if (title.isEmpty()) {
                            title = "Untitled";
                        }

                        categoryModel.setTitle(title);

                        if (category != null) {
                            category.setTitle(title);
                            category.setTheme(categoryTheme);
                            updateCategory(category, position);

                        } else {
                            saveCategory(title);

                        }


                    }
                }).create();

        dialog.show();


    }

    private void updateCategory(final Category category, final int position) {
        Log.d(TAG, "updateCategory: Category id: " + category.getId());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.categoryDao().updateCategoryById(category.getTitle(),
                        category.getTheme(), category.getId());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Edited", Toast.LENGTH_SHORT).show();
                        categoryAdapter.editItem(position, category);
                    }
                });

            }
        });


    }

    private void setCategoryTheme(CategoryDialogBind dialogBind, int theme) {
        if (theme != categoryTheme) {
            getThemeView(dialogBind, categoryTheme).setImageResource(0);
        }

        getThemeView(dialogBind, theme).setImageResource(R.drawable.ic_checked);
        categoryTheme = theme;
    }

    private void saveCategory(String title) {
        categoryModel.setTitle(title);
        categoryModel.setTheme(categoryTheme);
        categoryModel.setCreatedAt(System.currentTimeMillis());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.categoryDao().insert(categoryModel);
            }
        });


    }

    private ImageView getThemeView(CategoryDialogBind dialogBind, int theme) {
        switch (theme) {
            case Category.THEME_AMBER:
                return dialogBind.colorAmber;
            case Category.THEME_BLUE:
                return dialogBind.colorBlue;
            case Category.THEME_GREEN:
                return dialogBind.colorGreen;
            case Category.THEME_TEAL:
                return dialogBind.colorTeal;
            case Category.THEME_CYAN:
                return dialogBind.colorCyan;
            default:
                return dialogBind.colorRed;
        }
    }

    @Override
    public void onItemClicked(View view, int position) {
        Category category = categoryList.get(position);
        SharedHelper.save(getContext(), "theme", category.getTheme());
        Intent intent = new Intent(getContext(), NoteActivity.class);
        intent.putExtra("categoryModel", category);
        startActivity(intent);


    }

    @Override
    public void onDeleteSwiped(final int position) {
        final Category category = categoryList.get(position);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int deletedRaw = db.categoryDao().deleteCategory(category.getId());
                Logger.v("deleted raw: " + deletedRaw);
                if (deletedRaw == 1) {


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            categoryAdapter.deleteItem(position);
                            Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
    }

    @Override
    public void onEditSwiped(int position, RecyclerView.ViewHolder viewHolder) {
        Category category = categoryList.get(position);


        displayCategoryDialog(R.string.edit_category,
                R.string.edit,
                category, position);

    }
}
