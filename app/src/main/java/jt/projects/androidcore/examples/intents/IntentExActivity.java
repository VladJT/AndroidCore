package jt.projects.androidcore.examples.intents;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import jt.projects.androidcore.R;

public class IntentExActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SETTING_ACTIVITY = 99;
    private static final int REQUEST_CODE_ACTIVITY_GET_CONTACT = 98;
    private static final String TAG = "StylesActivity";

    private Account account;
    private TextInputEditText t;
    private TextView info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_intents_layout);
        account = new Account();

        info = findViewById(R.id.textViewExStyles);
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
                Toast.makeText(IntentExActivity.this, "Клик", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        account = data.getParcelableExtra("USER_NAME");
                        t.setText(account.getName() + " " + account.getSurName() + " " + account.getAge() + " " + account.getEmail());
                    }
                });

        // account settings
        t = findViewById(R.id.editTextExStyles);
        findViewById(R.id.buttonExStylesNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Чтобы стартовать активити, надо подготовить интент
                // В данном случае это будет явный интент, поскольку здесь передаётся класс активити
                Intent runSettings = new Intent(IntentExActivity.this, SettingsActivity.class);
                // Поскольку экстра-параметры — это по факту Bundle класс (см. третий урок), то и устанавливаются они
                //по таким же правилам. Нам нужна пара ключ-значение.
                account.setName(t.getText().toString());
                runSettings.putExtra("USER_NAME", account);
               // startActivityForResult(runSettings, REQUEST_CODE_SETTING_ACTIVITY);
                launchSomeActivity.launch(runSettings);
            }
        });


        // browser
        findViewById(R.id.buttonExStylesBrowser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri address = Uri.parse("https://geekbrains.ru");
                Intent linkInet = new Intent(Intent.ACTION_VIEW, address);
//                ActivityInfo activityInfo = linkInet.resolveActivityInfo(getPackageManager(), linkInet.getFlags());
//                if (activityInfo != null) {
//                    startActivity(linkInet);
//                }

                startActivity(linkInet);
            }
        });

        // send message
        findViewById(R.id.buttonExStylesSendMessageToContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "hello, world");
                intent.putExtra(Intent.EXTRA_SUBJECT, "hello_msg");

                Intent intentChooser = Intent.createChooser(intent, "SendReport");
                startActivity(intent);

            }
        });

        // get contact
        findViewById(R.id.buttonExStylesGetContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                PackageManager pm = getApplication().getPackageManager();
                if (!(pm.resolveActivity(intent, pm.MATCH_DEFAULT_ONLY) == null)) {
                    startActivityForResult(intent, REQUEST_CODE_ACTIVITY_GET_CONTACT);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        {
            if (requestCode != REQUEST_CODE_SETTING_ACTIVITY && requestCode != REQUEST_CODE_ACTIVITY_GET_CONTACT) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }

//            if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_SETTING_ACTIVITY) {
//                account = data.getParcelableExtra("USER_NAME");
//                t.setText(account.getName() + " " + account.getSurName() + " " + account.getAge() + " " + account.getEmail());
//            }

            if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ACTIVITY_GET_CONTACT) {
                if (data != null) {
                    Uri contact = data.getData();
                    String[] queryFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
                    Cursor c = getApplication().getContentResolver().query(contact, queryFields, null, null, null);
                    try {
                        if (c.getCount() == 0) {
                            return;
                        }
                        c.moveToFirst();
                        String name = c.getString(0);
                        info.setText(name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        c.close();
                    }
                }
            }

        }
    }
}
