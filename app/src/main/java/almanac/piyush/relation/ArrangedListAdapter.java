package almanac.piyush.relation;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;


public class ArrangedListAdapter extends ArrayAdapter<String>{
    private final Activity context;
    private final String[] names;
    private final Integer[] imageIds;
    private final String[] relationship;
    public Firebase myFirebaseRef;
    TextView txtName;
    TextView txtRelation;
    ImageView imageView;
    int pos;


    public ArrangedListAdapter(
            Activity context, String[] names,String[] relationship, Integer[] imageIds) {
        super(context, R.layout.row, names);
        this.context = context;
        this.names = names;
        this.imageIds = imageIds;
        this.relationship = relationship;
        Firebase.setAndroidContext(getContext());
        myFirebaseRef = new Firebase("https://relationalmanac.firebaseio.com/");

    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.row, null, true);
        pos=position;
           txtName = (TextView) rowView.findViewById(R.id.NameofPerson);
           txtRelation = (TextView) rowView.findViewById(R.id.Relation);
           imageView = (ImageView) rowView.findViewById(R.id.image);



           txtName.setText(names[position]);
            txtRelation.setText(relationship[position]);
            imageView.setImageResource(imageIds[position]);

        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtName.setText(String.valueOf(dataSnapshot.child("Relations").child(String.valueOf(pos)).child("Name").getValue()));
                txtRelation.setText(String.valueOf(dataSnapshot.child("Relations").child(String.valueOf(pos)).child("Relation").getValue()));
                Picasso.with(getContext()).load(String.valueOf(dataSnapshot.child("Relations").child(String.valueOf(pos)).child("Image").getValue())).into(imageView);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
             return rowView;
    }
}
