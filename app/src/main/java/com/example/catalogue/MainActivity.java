package com.example.catalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button;
    Button buttonUseNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button2);
        buttonUseNow = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();

            }
        });

        buttonUseNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity2();
            }
        });
    }

    private void openNewActivity2() {
        Intent intent = new Intent(this, TaskListActivity.class);

        // Toast.makeText(MainActivity.this,  editName.getText(), Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }

    public void openNewActivity(){
        Intent intent = new Intent(this, LoginActivity.class);

        // Toast.makeText(MainActivity.this,  editName.getText(), Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "Welcome to login", Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }
}
