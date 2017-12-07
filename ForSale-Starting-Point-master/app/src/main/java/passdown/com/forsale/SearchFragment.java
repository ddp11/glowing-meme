package passdown.com.forsale;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import passdown.com.forsale.models.Post;

public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";
    private EditText searchText;
    private String search;

    ListView listViewPosts;
    List<Post> postList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        searchText = view.findViewById(R.id.editText);


        listViewPosts = (ListView)view.findViewById(R.id.listViewPosts);
        postList = new ArrayList<>();


        Button mSearch = view.findViewById(R.id.Search);
        mSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                search = searchText.getText().toString();
                if(!isEmpty(search)){
                    search(search);
                }else {
                    Toast.makeText(getActivity(), "please complete all required fields", Toast.LENGTH_SHORT).show();
                }
            }

        });

        return view;
    }

    public void search(final String search){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList.clear();
                //Gets the List of Users
                for(DataSnapshot Users : dataSnapshot.getChildren()) {
                    //Gets a single Users
                    for (DataSnapshot uid : Users.getChildren()) {
                        //Gets the List of Books
                        for (DataSnapshot posts : uid.getChildren()) {
                            //Gets a single Book
                            for (DataSnapshot book : posts.getChildren()) {
                                Toast.makeText(getActivity(), "Found a thing.", Toast.LENGTH_SHORT).show();
                                Post post = new Post(book.child("post_id").getValue().toString(),
                                        book.child("user_id").getValue().toString(),
                                        book.child("image").getValue().toString(),
                                        book.child("title").getValue().toString(),
                                        book.child("description").getValue().toString(),
                                        book.child("price").getValue().toString(),
                                        book.child("country").getValue().toString(),
                                        book.child("state_province").getValue().toString(),
                                        book.child("city").getValue().toString(),
                                        book.child("contact_email").getValue().toString());
                                if (post.getTitle().equalsIgnoreCase(search)) {
                                    postList.add(post);
                                }
                            }

                        }

                    }
                }
                if(postList.isEmpty()){
                    Toast.makeText(getActivity(), "No results found!", Toast.LENGTH_SHORT).show();
                }
                PostList adapter = new PostList(getActivity(), postList);
                listViewPosts.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    private boolean isEmpty(String string){
        return string.equals("");
    }

    /*@Override
    public void onStart() {
        super.onStart();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList.clear();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Post post = postSnapshot.getValue(Post.class);
                    postList.add(post);
                }
                PostList adapter = new PostList(getActivity(), postList);
                listViewPosts.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }*/
}