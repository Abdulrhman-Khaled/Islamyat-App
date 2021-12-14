package com.bodykh.IslamyatApp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class EverDayTasks extends AppCompatActivity {

    private ListView mListView;
    private List<Comment> mTweets;
    private Comment mComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ever_day_tasks);
        setTitle("الْمَهَامّ اليَوْمِيَّة - Daily Tasks");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mListView = findViewById(R.id.list);

        mTweets = generateData();
        refreshList();

        mListView.setLongClickable(true);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                deleteOne(pos);
                return true;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                modifyOne(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.deleteall:
                clearDialog();
                return true;
            case R.id.addtodo:
                addDialog();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                recreate();

        }
        return super.onOptionsItemSelected(item);
    }


    private void deleteOne(int pos) {
        final int position = pos;
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("انهاء المهمة");
        alert.setMessage("هل انهيت هذه المهمه بنجاح؟");

        alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                deleteOnePos(position);
                refreshList();
            }
        });

        alert.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    private void clearDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("حذف جميع المهام");
        alert.setMessage("هل تريد حذف جميع المهام؟");

        alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                deleteAll();
                refreshList();
            }
        });

        alert.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    private void addDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("مهمة جديدة");
        alert.setMessage("قم بإضافة اسم و وصف المهمة:");

        // Create TextView
        final EditText name = new EditText(this);
        name.setHint("اسم المهمة - Task Name");

        final EditText text = new EditText(this);
        text.setHint("وصف المهمة - Task Description");


        // Checkbox
        final CheckBox importantCheck = new CheckBox(this);
        importantCheck.setText("مهمة مميزة - Important Task");


        Context context = getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(70, 0, 70, 0);

        layout.addView(name, layoutParams);
        layout.addView(text, layoutParams);
        layout.addView(importantCheck, layoutParams);

        alert.setView(layout);

        alert.setPositiveButton("إضافة", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String important;
                if (importantCheck.isChecked()) {
                    important = "y";
                } else {
                    important = "n";
                }
                if (name.getText().toString().trim().length() > 0) {
                    mComment = new Comment(name.getText().toString(), text.getText().toString(), important);
                    AddItem(mComment);
                    refreshList();
                }
            }
        });

        alert.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    private void modifyOne(final int position) {

        mComment = mTweets.get(position);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("تعديل المهمة");
        alert.setMessage("قم بتعديل اسم او وصف المهمة:");

        // Create TextView
        final EditText name = new EditText(this);
        name.setText(mComment.getPseudo());

        final EditText text = new EditText(this);
        text.setText(mComment.getText());

        // Checkbox
        final CheckBox importantCheck = new CheckBox(this);
        importantCheck.setText("مهمة مميزة - Important Task");

        if (mComment.getImportant().equals("y")) {
            importantCheck.setChecked(true);
        }

        Context context = getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(70, 0, 70, 0);

        layout.addView(name, layoutParams);
        layout.addView(text, layoutParams);
        layout.addView(importantCheck, layoutParams);

        alert.setView(layout);


        alert.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String important;
                if (importantCheck.isChecked()) {
                    important = "y";
                } else {
                    important = "n";
                }
                if (name.getText().toString().trim().length() > 0) {
                    mComment = new Comment(name.getText().toString(), text.getText().toString(), important);
                    ModifyItem(position, mComment);
                    refreshList();
                }
            }
        });

        alert.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();


    }

    // LIST REFRESH
    private void refreshList() {
        RowAdapter adapter = new RowAdapter(EverDayTasks.this, mTweets);
        mListView.setAdapter(adapter);
    }

    // GENERATE INITIAL DATA
    private List<Comment> generateData() {
        mTweets = new ArrayList<>();
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String myData = myPrefs.getString("myTodoData", null);

        if (myData != null) {
            try {
                JSONArray jsonArray = new JSONArray(myData);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String data = jsonArray.getString(i);
                    String[] splitData = data.split("\\.");

                    mTweets.add(new Comment(splitData[0], splitData[1], splitData[2]));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return mTweets;
    }

    // JSON SAVE & ACTIONS
    private void ModifyItem(int position, Comment e) {
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String myData = myPrefs.getString("myTodoData", null);

        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(myData);
            jsonArray.remove(position);
            jsonArray.put(e.getPseudo() + "." + e.getText() + "." + e.getImportant());
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        mTweets.remove(position);
        mTweets.add(e);

        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString("myTodoData", jsonArray != null ? jsonArray.toString() : null);
        editor.apply();
    }

    private void AddItem(Comment e) {
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String myData = myPrefs.getString("myTodoData", null);

        JSONArray jsonArray = null;
        if (myData == null) {
            jsonArray = new JSONArray();
            jsonArray.put(e.getPseudo() + "." + e.getText() + "." + e.getImportant());
            mTweets.add(e);
        } else {
            try {
                jsonArray = new JSONArray(myData);
                jsonArray.put(e.getPseudo() + "." + e.getText() + "." + e.getImportant());
                mTweets.add(e);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString("myTodoData", jsonArray != null ? jsonArray.toString() : null);
        editor.apply();
    }

    private void deleteOnePos(int pos) {
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String myData = myPrefs.getString("myTodoData", null);

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(myData);

            jsonArray.remove(pos);
            mTweets.remove(pos);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString("myTodoData", jsonArray != null ? jsonArray.toString() : null);
        editor.apply();
    }

    private void deleteAll() {
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        JSONArray jsonArray = new JSONArray();
        mTweets = new ArrayList<>();

        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString("myTodoData", jsonArray.toString());
        editor.apply();
    }

}