package com.example.mobilerecruiter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class Posts extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SharedPreferences preferences;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Post> events ;
    RecyclerView rv;
    private FloatingActionButton fab;
    private Intent candidate;
    ArrayList<Post> mylist=new ArrayList<>();
    private SearchView searchActivity;
    private OnFragmentInteractionListener mListener;
    public Posts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Posts.
     */
    // TODO: Rename and change types and number of parameters
    public static Posts newInstance(String param1, String param2) {
        Posts fragment = new Posts();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_posts, container, false);

        fab = view.findViewById(R.id.posts_fab);
        searchActivity=view.findViewById(R.id.posts_search);
        preferences = Objects.requireNonNull(getContext()).getSharedPreferences("myPrefs", MODE_PRIVATE);
        if(!preferences.getBoolean("is_admin",false)) {
            fab.setVisibility(View.GONE);
        }
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addCandidate();
            }
        });
        rv=view.findViewById(R.id.posts_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        return view ;
    }

    private void delete(int id) {
        NetworkService.getInstance().
                getJSONApi().
                deletePost(id).
                enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        setData();
                        Log.d("Success", "Success");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("Error",t.toString());
                    }
                });

    }

    public void onStart() {
        super.onStart();
        setData();
        if(searchActivity!=null){
            searchActivity.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if(newText.contains(" ")){
                        String[] temp=newText.split(" ");
                        for (String s : temp) {
                            search(s);
                        }
                    }else {
                        search(newText.toLowerCase());
                    }
                    return true;

                }
            });
        }else {
            mylist.clear();
        }
    }
    private void setData(){
        if(preferences.getBoolean("is_admin",false)) {

            NetworkService.getInstance()
                    .getJSONApi()
                    .getPosts()
                    .enqueue(new Callback<List<Post>>() {
                        @Override
                        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                            assert response.body() != null;
                            events = new ArrayList<>(response.body());
                            Post_adapter adapter = new Post_adapter(events);
                            rv.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<List<Post>> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        }
        else {
            NetworkService.getInstance()
                    .getJSONApi()
                    .getPostsById(preferences.getInt("id",1))
                    .enqueue(new Callback<List<Post>>() {
                        @Override
                        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                            if(response.isSuccessful()) {
                                assert response.body() != null;
                                events = new ArrayList<>(response.body());
                                Post_adapter adapter = new Post_adapter(events);
                                rv.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Post>> call, Throwable t) {
                            Log.d("Error", t.toString());
                        }
                    });
        }
    }
    private void search(String str) {
        mylist=new ArrayList<>();
        for(Post object:events){
            for (int i = 0; i < object.getSkills().size(); i++) {
                if (object.getSkills().get(i).toLowerCase().contains(str)||object.getF_name().toLowerCase().contains(str)||object.getL_name().toLowerCase().contains(str)) {
                        mylist.add(object);
                        i=object.getSkills().size();
                }

            }
        }
        Post_adapter adapterClass=new Post_adapter(mylist);
        rv.setAdapter(adapterClass);

    }

    private void addCandidate(){
        candidate = new Intent(getActivity(), Post_add.class);
        startActivity(candidate);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
