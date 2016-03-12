package almanac.piyush.relation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class login extends AppCompatActivity {
    RequestQueue requestQueue;
    String showUrl = "http://auntkitchen.in/gdg_bbsr_piyush/abcdefghij/auth.php";
    String loginUrl = "http://auntkitchen.in/gdg_bbsr_piyush/abcdefghij/login.php";
    String out = "";
    String em,pas,check;
    String emailsend,pass,nam;
    EditText loginemail;
    EditText loginpassword;
    String email;
    String password;
    String  success;
    String out1 = "";
    String name,rname;
    private ProgressDialog pDialog;

    JSONParsor jsonParser = new JSONParsor();
    JSONObject json;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String verify=loadData2();
        if(verify.equals("1"))
        {
            Intent g = new Intent(login.this,MainActivity.class);
            Toast.makeText(login.this, "Welcome to GDG ", Toast.LENGTH_LONG).show();
            startActivity(g);
            finish();
        }

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        ImageButton imgbtnsignup = (ImageButton) findViewById(R.id.signupbutton);
        ImageButton imgbtnlogin = (ImageButton) findViewById(R.id.loginbuton);

        imgbtnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText signupname = (EditText) findViewById(R.id.signupname);
                final EditText signupemail = (EditText) findViewById(R.id.signupemail);
                final EditText signuppassword = (EditText) findViewById(R.id.signuppassword);
                final EditText signupconfirmpassword = (EditText) findViewById(R.id.signupconfirmpassword);
                emailsend=signupemail.getText().toString();
                pass=signuppassword.getText().toString();
                nam=signupname.getText().toString();
                if(signupname.getText().toString().equals("")||signupemail.getText().toString().equals("")||signuppassword.getText().toString().equals("")||signupconfirmpassword.getText().toString().equals("")) {
                    Toast.makeText(login.this, "Kindly,Fill all Fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                            if (signuppassword.getText().toString().equals(signupconfirmpassword.getText().toString())) {

                                                Toast.makeText(login.this,"All fields validitation Complete, pushing information to server...",Toast.LENGTH_SHORT).show();
                                                Toast.makeText(login.this, "Checking internet...", Toast.LENGTH_SHORT).show();
                                                if (isNetworkAvailable()) {
                                                    Toast.makeText(login.this,"Internet Connected!!",Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(login.this,"Registering sequence initiated, registering you in.....",Toast.LENGTH_LONG).show();
                                                    StringRequest request = new StringRequest(Request.Method.POST, showUrl, new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {

                                                            if (response.equals("1")) {
                                                                Toast.makeText(login.this, "Email already Registered!", Toast.LENGTH_LONG).show();
                                                            } else if (response.equals("0")) {
                                                                Toast.makeText(login.this, "Registered successfully!", Toast.LENGTH_LONG).show();

                                                                signupconfirmpassword.setText("");
                                                                signupemail.setText("");
                                                                signupname.setText("");
                                                                signuppassword.setText("");



                                                            } else {
                                                                Toast.makeText(login.this, "Check the internet!", Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {

                                                            Toast.makeText(login.this,error.toString(), Toast.LENGTH_LONG).show();
                                                        }
                                                    }) {

                                                        @Override
                                                        protected Map<String, String> getParams() throws AuthFailureError {
                                                            Map<String, String> parameters = new HashMap<String, String>();
                                                            parameters.put("name", nam);
                                                            parameters.put("email", emailsend);
                                                            parameters.put("password", pass);
                                                            return parameters;

                                                        }
                                                    };


                                                    requestQueue.add(request);




                                                } else {
                                                    Toast.makeText(login.this, "Internet Not Connected !", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            else{
                                                Toast.makeText(login.this,"Password Mismatched!!",Toast.LENGTH_LONG).show();
                                            }




                        }

                }



        });

        imgbtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginemail = (EditText) findViewById(R.id.loginemail);
                loginpassword = (EditText) findViewById(R.id.loginpassword);
                password=loginpassword.getText().toString();
                email=loginemail.getText().toString();
                if(loginemail.getText().toString().equals("")|| loginpassword.getText().toString().equals(""))
                {
                    Toast.makeText(login.this,"Kindly Fill all the Fields !",Toast.LENGTH_SHORT).show();
                }
                else{

                    if(isNetworkAvailable())
                    {
                        new Attemptlogin().execute();

                    }
                    else
                    {
                        Toast.makeText(login.this,"No internet!!",Toast.LENGTH_SHORT).show();

                    }



                }}
        });

    }


    class Attemptlogin extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(login.this);
            pDialog.setMessage("Logging You in....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {


            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("email", email));
                params.add(new BasicNameValuePair("password", password));
                Log.d("request!", "starting");
                json = jsonParser.makeHttpRequest(loginUrl, "POST", params);
                // Log.d("login attempt",json.toString());

                success = json.getString("auth");





            }catch(JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String message) {
            pDialog.dismiss();



            if (success.toString().equals("0")) {

                Intent ii = new Intent(login.this, MainActivity.class);
                saveData2();
                startActivity(ii);
                Toast.makeText(login.this,"Welcome to GDG ",Toast.LENGTH_LONG).show();
                finish();
            } else if (success.toString().equals("1")) {


                Toast.makeText(login.this,"Register first!",Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(login.this,"Check Internet!!",Toast.LENGTH_LONG).show();

            }

        }

    }




    protected void saveData2(){
        String FILENAME1 = "auth_verify.txt";
        String verifyme="1";

        try {
            FileOutputStream fos1 = getApplication().openFileOutput(FILENAME1, Context.MODE_PRIVATE);
            fos1.write(verifyme.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    protected String loadData2() {
        String FILENAME1 = "auth_verify.txt";

        try {
            out="";
            FileInputStream fis1 = getApplication().openFileInput(FILENAME1);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(fis1));
            String sLine1 = null;

            while (((sLine1 = br1.readLine()) != null)) {
                out += sLine1;
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return out;
    }
    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try
        {
            trimCache(getApplicationContext());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }


}

