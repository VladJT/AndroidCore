package jt.projects.androidcore.examples;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import jt.projects.androidcore.R;

public class StylesActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SETTING_ACTIVITY = 99;
    private static final String TAG = "StylesActivity";

    private Account account;
    private TextInputEditText t;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_styles_layout);
        account = new Account();

        TextView info = findViewById(R.id.textViewExStyles);
        StringBuilder sb = new StringBuilder();
        sb.append("BaseContext: " + getBaseContext().getClass().getName() + "\n");
        sb.append("ApplicationContext: " + getApplicationContext().getClass().getName() + "\n");
        sb.append("this: " + this.getClass().getName() + "\n");
        try {
            info.setText(sb.toString());
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }


        findViewById(R.id.buttonExStylesClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StylesActivity.this, "Клик", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        t = findViewById(R.id.editTextExStyles);
        findViewById(R.id.buttonExStylesNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Чтобы стартовать активити, надо подготовить интент
                // В данном случае это будет явный интент, поскольку здесь передаётся класс активити
                Intent runSettings = new Intent(StylesActivity.this, SettingsActivity.class);
                // Поскольку экстра-параметры — это по факту Bundle класс (см. третий урок), то и устанавливаются они
                //по таким же правилам. Нам нужна пара ключ-значение.
                account.setName(t.getText().toString());
                runSettings.putExtra("USER_NAME", account);
                startActivityForResult(runSettings, REQUEST_CODE_SETTING_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        {
            if (requestCode != REQUEST_CODE_SETTING_ACTIVITY) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }
            if (resultCode == RESULT_OK) {
                account = data.getParcelableExtra("USER_NAME");
                t.setText(account.getName() + " " + account.getSurName() + " " + account.getAge() + " " + account.getEmail());
            }

        }
    }
}
