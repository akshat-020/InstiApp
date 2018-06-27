package in.ac.iitb.gymkhana.iitbapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import in.ac.iitb.gymkhana.iitbapp.Constants;
import in.ac.iitb.gymkhana.iitbapp.ItemClickListener;
import in.ac.iitb.gymkhana.iitbapp.R;
import in.ac.iitb.gymkhana.iitbapp.adapter.BodyAdapter;
import in.ac.iitb.gymkhana.iitbapp.data.Body;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link BodyRecyclerViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BodyRecyclerViewFragment extends Fragment {
    private static final String ARG_BODY = "bodies";

    private RecyclerView recyclerView;
    private BodyAdapter bodyAdapter;

    private List<Body> bodyList;

    public BodyRecyclerViewFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BodyRecyclerViewFragment newInstance(List<Body> arg_bodyList) {
        BodyRecyclerViewFragment fragment = new BodyRecyclerViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BODY, new Gson().toJson(arg_bodyList));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bodyList = new Gson().fromJson(getArguments().getString(ARG_BODY), new TypeToken<List<Body>>(){}.getType());
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.body_recycler_view);
        bodyAdapter = new BodyAdapter(bodyList, new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Body body = bodyList.get(position);
                BodyFragment bodyFragment = new BodyFragment();
                Bundle arguments=getArguments();
                arguments.putString(Constants.BODY_JSON,new Gson().toJson(body));
                bodyFragment.setArguments(getArguments());
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.framelayout_for_fragment, bodyFragment, bodyFragment.getTag());
                ft.addToBackStack(bodyFragment.getTag());
                ft.commit();
            }
        });
        recyclerView.setAdapter(bodyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_body_recycler_view, container, false);
    }


}