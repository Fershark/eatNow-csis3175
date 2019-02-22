package ca.douglascollege.eatnow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity implements View.OnClickListener {

    Button btnRegister;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NoActionBar);
        setContentView(R.layout.activity_welcome);

        btnRegister = (Button) findViewById(R.id.btnWelcomeRegister);
        btnLogin = (Button) findViewById(R.id.btnWelcomeLogin);
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnWelcomeRegister:
                break;
            case R.id.btnWelcomeLogin:
                break;
            default:
                break;
        }
    }
}
