package com.example.mobilerecruiter;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class Posts extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_posts, container, false);
        rv=view.findViewById(R.id.posts_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        fab = view.findViewById(R.id.posts_fab);
        searchActivity=view.findViewById(R.id.posts_search);

        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                addCandidate();
            }
        });

        rv.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rv, new ClickListener() {

            @Override
            public void onClick(View view, final int position) {

            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view ;
    }
    public void onStart() {
        super.onStart();
        NetworkService.getInstance()
                .getJSONApi()
                .getPosts()
                .enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        assert response.body() != null;
                        events = new ArrayList<>(response.body());
                        Post_adapter adapter=new Post_adapter(events);
                        rv.setAdapter(adapter);
                    }
                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
        if(searchActivity!=null){
            searchActivity.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText.toLowerCase());
                    return true;

                }
            });
        }else {
            mylist.clear();
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
