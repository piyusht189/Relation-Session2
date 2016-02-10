package almanac.piyush.relation;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Created by Super-Nova on 09-02-2016.
 */
public class ArrangedListAdapter extends ArrayAdapter<String>{
    private final Activity context;
    private final String[] names;
    private final Integer[] imageIds;
    private final String[] relationship;

    public ArrangedListAdapter(
            Activity context, String[] names,String[] relationship, Integer[] imageIds) {
        super(context, R.layout.row, names);
        this.context = context;
        this.names = names;
        this.imageIds = imageIds;
        this.relationship = relationship;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.row, null, true);

           TextView txtName = (TextView) rowView.findViewById(R.id.NameofPerson);
           TextView txtRelation = (TextView) rowView.findViewById(R.id.Relation);
           ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
           txtName.setText(names[position]);
            txtRelation.setText(relationship[position]);
            imageView.setImageResource(imageIds[position]);
             return rowView;
    }
}
