package ru.zadli.ulsu_collaborating.timetable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;

import ru.zadli.ulsu_collaborating.timetable.adapters.LaunchRVAdapter;

import static com.android.volley.Request.Method.GET;

public class LaunchActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    TextView student_name;
    RecyclerView rv;
    TextView hello_text;
    ImageView day_image;
    TextView week_now;
    TextView weather_max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            super.onCreate(savedInstanceState); //Если пользователь не авторизован, показываем layout авторизации для доступа к бд
            setTitle("Авторизация");
            createResponse();
            signIn();
        } else {
            FirebaseDatabase timetable_db = FirebaseDatabase.getInstance();
            timetable_db.getReference(); //Парсим бд тут, потому что так быстрее

            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            setTheme(R.style.AppTheme);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.launch_activity);


            student_name = findViewById(R.id.student_name);
            rv = findViewById(R.id.rv_grid);
            hello_text = findViewById(R.id.hello_text);
            day_image = findViewById(R.id.day_image);
            week_now = findViewById(R.id.week_now);
            weather_max = findViewById(R.id.temperature_max);

            student_name.setText(Objects.requireNonNull(currentUser).getDisplayName());

            Calendar calendar = Calendar.getInstance();
            if (calendar.get(Calendar.HOUR_OF_DAY) > 6 && calendar.get(Calendar.HOUR_OF_DAY) < 20) {
                day_image.setImageResource(R.drawable.solnyshko);
                hello_text.setText("Доброе утро,");
            } else if (calendar.get(Calendar.HOUR_OF_DAY) > 5 && calendar.get(Calendar.HOUR_OF_DAY) < 8) {
                day_image.setImageResource(R.drawable.solnyshko);
                hello_text.setText("Добрый день,");
            } else {
                day_image.setImageResource(R.drawable.luna);
                hello_text.setText("Доброй ночи,");
            }

            if (calendar.get(Calendar.WEEK_OF_YEAR) % 2 == 0) {
                if (Calendar.SATURDAY == calendar.get(Calendar.DAY_OF_WEEK) | Calendar.SUNDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
                    week_now.setText("Сейчас 1ая неделя, но смотри 2ую неделю");
                } else {
                    week_now.setText("Сейчас 1ая неделя");
                }
            } else {
                if (Calendar.SATURDAY == calendar.get(Calendar.DAY_OF_WEEK) | Calendar.SUNDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
                    week_now.setText("Сейчас 2ая неделя, но смотри 1ую неделю");
                } else {
                    week_now.setText("Сейчас 2ая неделя");
                }
                //TODO: replace this horrible code
            }

            Volley.newRequestQueue(this) //Парсим погоду
                    .add(new JsonObjectRequest(GET,
                                    "https://api.openweathermap.org/data/2.5/onecall?lat=54.316667&lon=48.366667&units=metric&appid=7460a393d941c2dcc3666c20ca581223",
                                    null,
                                    new Response.Listener<JSONObject>() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                JSONArray main = response.getJSONArray("daily");
                                                JSONObject current = response.getJSONObject("current");
                                                JSONObject daily = main.getJSONObject(0);
                                                JSONObject temp = daily.getJSONObject("temp");
                                                weather_max.setText("На улцие: " + current.getString("temp") + "°C" + "  min: " + temp.getString("min") + "°C   max:" + temp.getString("max") + "°C");
                                                rv.setLayoutManager(new GridLayoutManager(LaunchActivity.this, 2));
                                                LaunchRVAdapter launchRVAdapter = new LaunchRVAdapter(LaunchActivity.this);
                                                rv.setAdapter(launchRVAdapter);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            })
                    );
        }
    }

    private void createResponse() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 123);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(Objects.requireNonNull(account).getIdToken());

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            recreate();
                        } else {
                            Toast.makeText(LaunchActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}