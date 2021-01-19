package com.example.bloodbank.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bloodbank.R;
import com.example.bloodbank.Utils.Endpoints;
import com.example.bloodbank.Utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText numberEt, passwordEt;
    Button btn_btn_submit;
    TextView signUpText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        numberEt=findViewById(R.id.username);
        passwordEt=findViewById(R.id.password_login);
        btn_btn_submit=findViewById(R.id.btn_submit);
        signUpText=findViewById(R.id.signUpText);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        btn_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberEt.setError(null);
                passwordEt.setError(null);
                String number=numberEt.getText().toString();
                String password=passwordEt.getText().toString();
                if(isValid(number,password))
                {
                    login(number,password);
                }

            }
        });
    }

    private void login(final String number, final String password) {

        StringRequest stringRequest =new StringRequest(Request.Method.POST, Endpoints.login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Success"))
                {
                    Toast.makeText(LoginActivity.this, response , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    LoginActivity.this.finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Something went wrong:", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY",error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("number",number);
                params.put("password",password);

                return  params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private boolean isValid(String number,String password)
    {
        if(number.isEmpty())
        {
            showMessage("Empty Mobile Number");
            numberEt.setError("Empty Mobile Number");
            return false;
        }
        else if (password.isEmpty())
        {
            showMessage("Empty Password");
            passwordEt.setError("Empty Password");
            return false;
        }
        return true;
    }
    private void showMessage(String msg)
    {
        Toast.makeText(this, msg , Toast.LENGTH_SHORT).show();
    }
}