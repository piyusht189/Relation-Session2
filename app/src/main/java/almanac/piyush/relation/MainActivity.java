package almanac.piyush.relation;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends ListActivity {


    String[] names = {
            "Mr.Rocking Daddy",
            "Mrs.Special Mommy",
            "Ms.Crazy Sister",
            "Mr.Cool Brother",
            "Mr.Super Grandpa",
            "Mrs.Lovely Grandma",
            
};
    String[] relationship = {
      "Dad","Mom","Sister","Brother","Grandfather","Grandmother"
    };

    Integer[] imageIDs = {
            R.drawable.dad,
            R.drawable.mom,
            R.drawable.sis,
            R.drawable.bro,
            R.drawable.grandpa,
            R.drawable.grandma,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn=(Button) findViewById(R.id.logoutbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dir = getFilesDir();
                File file = new File(dir, "auth_verify.txt");
                file.delete();
                Intent p=new Intent(MainActivity.this,login.class);
                startActivity(p);
                finish();
            }
        });

        ArrangedListAdapter adapter = new ArrangedListAdapter(this, names,relationship, imageIDs);
        setListAdapter(adapter);


    }




}
