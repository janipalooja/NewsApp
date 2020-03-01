package jani.palooja.newsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class PopUp extends Activity {

    Button searchButton;
    EditText searchEditText;
    String keyword;
    CheckBox ilta_sanomat;
    boolean ilta_sanomat_checked;
    CheckBox yle_uutiset;
    boolean yle_uutiset_checked;
    CheckBox helsingin_sanomat;
    boolean helsingin_sanomat_checked;
    CheckBox kaleva;
    boolean kaleva_checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window);

        searchEditText = findViewById(R.id.searchEditText);
        ilta_sanomat = findViewById(R.id.ilta_sanomat);
        yle_uutiset = findViewById(R.id.yle_uutiset);
        helsingin_sanomat = findViewById(R.id.helsingin_sanomat);
        kaleva = findViewById(R.id.kaleva);

        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = searchEditText.getText().toString();
                ilta_sanomat_checked = ilta_sanomat.isChecked();
                yle_uutiset_checked = yle_uutiset.isChecked();
                helsingin_sanomat_checked = helsingin_sanomat.isChecked();
                kaleva_checked = kaleva.isChecked();

                Intent intent = new Intent();
                intent.putExtra("keyword", keyword);
                intent.putExtra("ilta_sanomat_checked", ilta_sanomat_checked);
                intent.putExtra("yle_uutiset_checked", yle_uutiset_checked);
                intent.putExtra("helsingin_sanomat_checked", helsingin_sanomat_checked);
                intent.putExtra("kaleva_checked", kaleva_checked);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = (int) (dm.widthPixels * 0.9);
        int height = (int) (dm.heightPixels * 0.45);

        getWindow().setLayout(width, height);
    }
}
