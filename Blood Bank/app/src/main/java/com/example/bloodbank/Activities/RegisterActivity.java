package com.example.bloodbank.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bloodbank.R;
import com.example.bloodbank.Utils.Endpoints;
import com.example.bloodbank.Utils.VolleySingleton;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameEt, cityEt, blood_groupEt, passwordEt,numberET;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameEt=findViewById(R.id.name);
        cityEt=findViewById(R.id.city);
        blood_groupEt=findViewById(R.id.blood_group);
        passwordEt=findViewById(R.id.password);
        numberET=findViewById(R.id.number);
        btn_submit=findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,city,blood_group,password,number;
                name=nameEt.getText().toString();
                city=cityEt.getText().toString();
                blood_group=blood_groupEt.getText().toString();
                password=passwordEt.getText().toString();
                number=numberET.getText().toString();
                if(isValid(name,city,blood_group,number))
                {
                   register(name,city,blood_group,password,number);
                }

            }
        });
    }
    public void register(String name, String city, String blood_group, String password, String number)
    {
       StringRequest stringRequest =new StringRequest(Request.Method.POST, Endpoints.register_url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               if(response.equals("Success"))
               {
                   Toast.makeText(RegisterActivity.this, response , Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                   RegisterActivity.this.finish();
               }
               else
               {
                   Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
               }

           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(RegisterActivity.this, "Something went wrong:", Toast.LENGTH_SHORT).show();
               Log.d("VOLLEY",error.getMessage());

           }
       }) {
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
              Map<String,String> params = new HashMap<>();
              params.put("name",name);
              params.put("city",city);
              params.put("blood_group",blood_group);
              params.put("password",password);
              params.put("number",number);
             return  params;
           }
       };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



    private boolean isValid(String name, String city, String blood_group, String number)
    {
        List<String> valid_blood_groups=new ArrayList<>();
        valid_blood_groups.add("A+");
        valid_blood_groups.add("A-");
        valid_blood_groups.add("B+");
        valid_blood_groups.add("B-");
        valid_blood_groups.add("AB+");
        valid_blood_groups.add("AB-");
        valid_blood_groups.add("O+");
        valid_blood_groups.add("O-");
        if(name.isEmpty())
        {
            showMessage("Name is Empty");
            return false;
        }
        else if(city.isEmpty())
        {
            showMessage("City Name  is Required");
            return false;
        }
        else if(!valid_blood_groups.contains(blood_group))
        {
            showMessage("Blood Group  invalid choose from"+valid_blood_groups);
            return false;
        }
        else if(number.length()!=11)
        {
            showMessage("Invalid Mobile Number, It should be length of 11 digits");
            return false;
        }
      return true;
    }


    private void showMessage(String msg)
    {
        Toast.makeText(this, msg , Toast.LENGTH_SHORT).show();
    }

}