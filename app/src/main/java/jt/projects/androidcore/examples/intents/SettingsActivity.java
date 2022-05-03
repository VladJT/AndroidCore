package jt.projects.androidcore.examples.intents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import jt.projects.androidcore.R;
import jt.projects.androidcore.examples.intents.Account;

public class SettingsActivity extends AppCompatActivity {
    private EditText editName;
    private EditText editSurname;
    private EditText editAge;
    private EditText editEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_settings_layout);

        editName = findViewById(R.id.editExSettingsName);
        editSurname = findViewById(R.id.editExSettingsSurname);
        editAge = findViewById(R.id.editExSettingsAge);
        editEmail = findViewById(R.id.editExSettingsEmail);

        // получить данные из Intent
        Account account = getIntent().getExtras().getParcelable("USER_NAME");

        editName.setText(account.getName());
        editSurname.setText(account.getSurName());
        editAge.setText(String.format(Locale.getDefault(), "%d",
                account.getAge()));
        editEmail.setText(account.getEmail());


        // settings
        Button b = findViewById(R.id.btnExSettingsReturn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentResult = new Intent();
                intentResult.putExtra("USER_NAME", createAccount());
                setResult(RESULT_OK, intentResult);
                finish();
            }
        });

    }

    private Account createAccount() {
        Account account = new Account(
                editName.getText().toString(),
                editSurname.getText().toString(),
                Integer.parseInt(editAge.getText().toString()),
                editEmail.getText().toString());
        return account;
    }
}
