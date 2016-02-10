package almanac.piyush.relation;

import android.app.ListActivity;
import android.os.Bundle;

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

        ArrangedListAdapter adapter = new ArrangedListAdapter(this, names,relationship, imageIDs);
        setListAdapter(adapter);


    }




}
