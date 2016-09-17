package greggnod.budlabapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewToMembers(View view){
        setContentView(R.layout.members);
    }

    public void viewToCamera(View view){
        setContentView(R.layout.camera);
    }
}
