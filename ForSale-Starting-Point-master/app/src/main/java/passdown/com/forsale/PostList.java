package passdown.com.forsale;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import passdown.com.forsale.models.Post;

public class PostList extends ArrayAdapter<Post>{
    private Activity context;
    private List<Post> postList;

    public PostList(Activity context, List<Post> postList){
        super(context, R.layout.list_layout, postList);
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.textView12);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textView13);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textView14);
        TextView textViewLocation = (TextView) listViewItem.findViewById(R.id.textView15);

        Post post = postList.get(position);

        textViewTitle.setText(post.getTitle());
        textViewDescription.setText(post.getDescription());
        textViewPrice.setText(post.getPrice());
        textViewLocation.setText(post.getCountry() + post.getState_province() + post.getCity());

        return listViewItem;
    }
}