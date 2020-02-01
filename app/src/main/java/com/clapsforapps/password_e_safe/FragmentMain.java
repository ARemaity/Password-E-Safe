package com.clapsforapps.password_e_safe;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.clapsforapps.password_e_safe.SharedPrefs.isInApp;

/**
 * Created by ALI on 1/4/2017.
 */

public class FragmentMain extends Fragment implements View.OnClickListener, RecyclerViewAdapter.RecyclerViewOnItemClickListener {

    private Toolbar tbFragmentMain;
    private FloatingActionButton fabAdd;
    private RecyclerView rvPasswords, rvNewPassword;
    private RecyclerViewAdapter rvAdapter, newPasswordAdapter;
    private ArrayList<Password> passwords = new ArrayList<Password>();
    private SharedPreferences settings;
    private AlertDialog newPasswordDialog;
    private FragmentsListener fragmentsListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settings = getActivity().getSharedPreferences(SharedPrefs.PREFS_NAME, MODE_PRIVATE);
        fragmentsListener = (FragmentsListener)getActivity();
        tbFragmentMain = (Toolbar)getView().findViewById(R.id.tbFragmentMain);
        ((AppCompatActivity)getActivity()).setSupportActionBar(tbFragmentMain);

        fabAdd = (FloatingActionButton)getView().findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(this);

        rvPasswords = (RecyclerView) getView().findViewById(R.id.rvPasswords);

        rvAdapter = new RecyclerViewAdapter((ArrayList) passwords, this, RecyclerViewAdapter.ROWS, getActivity());
        rvPasswords.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPasswords.setAdapter(rvAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabAdd:
                addNewPassword();
                break;
        }
    }

    @Override
    public void onItemClicked(View v, int position) {
        if(v.getId() == R.id.llGridItem){
            isInApp = true;
            switch (position){
                case 0:
                    fragmentsListener.onInsertRequested(Database.TYPE_GOOGLE);
                    break;
                case 1:
                    fragmentsListener.onInsertRequested(Database.TYPE_FACEBOOK);
                    break;
                case 2:
                    fragmentsListener.onInsertRequested(Database.TYPE_TWITTER);
                    break;
                case 3:
                    fragmentsListener.onInsertRequested(Database.TYPE_INSTAGRAM);
                    break;
                case 4:
                    fragmentsListener.onInsertRequested(Database.TYPE_MICROSOFT);
                    break;
                case 5:
                    fragmentsListener.onInsertRequested(Database.TYPE_SNAPCHAT);
                    break;
                case 6:
                    fragmentsListener.onInsertRequested(Database.TYPE_LINKEDIN);
                    break;
                case 7:
                    fragmentsListener.onInsertRequested(Database.TYPE_CUSTOM);
                    break;
            }
            newPasswordDialog.dismiss();
        }
        else if(v.getId() == R.id.llRowItem)
            fragmentsListener.onPasswordSelected(passwords.get(position));
    }

    public void getPasswords(int sortBy){
        //if(getActivity() == null)
            //Log.wtf("Activity", "Null");
        Database db = ((MainActivity)getActivity()).getDb();
        Cursor cursor = db.getAllPasswords(sortBy);
        cursor.moveToFirst();
        passwords.clear();
        while (!cursor.isAfterLast()) {
            passwords.add(new Password(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getInt(6)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        rvAdapter.notifyDataSetChanged();
    }

    public void notifyDataSetChanged(){
        rvAdapter.notifyDataSetChanged();
    }

    private void addNewPassword(){
        newPasswordAdapter = new RecyclerViewAdapter(null, this, RecyclerViewAdapter.GRID, getActivity());

        AlertDialog.Builder newPasswordBuilder = new AlertDialog.Builder(getActivity());
        View newPasswordView = getActivity().getLayoutInflater().inflate(R.layout.new_password, null);

        rvNewPassword = (RecyclerView) newPasswordView.findViewById(R.id.rvNewPassword);
        rvNewPassword.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        rvNewPassword.setAdapter(newPasswordAdapter);

        newPasswordBuilder.setView(newPasswordView);
        newPasswordDialog = newPasswordBuilder.create();

        newPasswordDialog.show();
    }
}
