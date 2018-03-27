package com.bit.cse;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class changeDb extends Fragment implements nameFragment.OnFragmentInteractionListener, daySem.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static Boolean pendingWRITE_EXTERNAL_STORAGEpermission=false;
    int WRITE_EXTERNAL_STORAGE=1;
    Button setDb,updateDb;
    ArrayList<String> link_list=new ArrayList<String>();
    ArrayList<String> fileDirExistingList=new ArrayList<String>();//path list
    ArrayList<String> fireBaseFileNameList=new ArrayList<String>();
    ArrayList<String> name_list=new ArrayList<String>();
    private OnFragmentInteractionListener mListener;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    FirebaseStorage storage;
    File mediaStorageDir;
    StorageReference temp;
    ArrayList<String> pathList=new ArrayList<String>();
    int count=0;
    File databasesDir;
    RecyclerView recyclerView;
    ProgressDialog progress;
    TextView toolbarText,curretnDbText;
String selected="20170";
    public changeDb() {
        // Required empty public constructor
    }

    public static changeDb newInstance(String param1, String param2) {
        changeDb fragment = new changeDb();
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

        View view=inflater.inflate(R.layout.fragment_change_db, container, false);
        databasesDir = new File("/data/data/" + getContext().getPackageName(),"DBFILES");
        updateDb=(Button)view.findViewById(R.id.update_button);
        progress=new ProgressDialog(getContext(),R.style.MyTheme);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view_radio);

        curretnDbText=(TextView)view.findViewById(R.id.changeDbCurrentDbText);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(layoutManager);
        toolbarText=(TextView)getActivity().findViewById(R.id.toolbarText);
        toolbarText.setText("Change Time Table");
        fileDirExistingList.clear();
        existingFilesList();
        SampleAdapter adapter=new SampleAdapter();
        recyclerView.setAdapter(adapter);
        final   MainActivity m=new MainActivity();
        final   DatabaseHelper d=new DatabaseHelper(getContext());

        if(DatabaseHelper.DB_NAME!=null)
            curretnDbText.setText("Current timetable:"+DatabaseHelper.DB_NAME);
        setDb=(Button)view.findViewById(R.id.buttonSetTimeTable);
        setDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper.DB_NAME=selected;
                Log.v("changedb123",selected);
                daySem fragment = new daySem();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();




            }
        });
        updateDb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
                    checkpermission();
                    if (pendingWRITE_EXTERNAL_STORAGEpermission) {
                        if (isInternetAvailable(getContext()))
                            update();
                        else
                            Toast.makeText(getContext(), "Network Error" + "\nPlease Check Internet Connectivity!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    update();
                }
            }
        });


        return view;
    }


    private void checkAndCopyDatabase() {
        for(int i=0;i<pathList.size();i++)
        {
            String DB_NAME = name_list.get(i);
            String DB_PATH = databasesDir.getPath()+File.separator;
            String myPath = DB_PATH + DB_NAME;
            FileInputStream myInput = null;
            try {
                myInput = new FileInputStream(pathList.get(i));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String outFileName = DB_PATH + DB_NAME;
            FileOutputStream myOutput = null;
            try {
                myOutput = new FileOutputStream(outFileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[10];
            int length;
            try {
                while ((length = myInput.read(buffer)) > 0) {
                    try {
                        myOutput.write(buffer, 0, length);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                myOutput.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                myOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                myInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //Delete files in internal memory after copying files to package folder
        File[] filesInDirDownloaded =mediaStorageDir.listFiles();
        for(int j=0;j<filesInDirDownloaded.length;j++)
            filesInDirDownloaded[j].delete();
        mediaStorageDir.delete();


    }
    public void checkpermission()
    {
        int result=0;
        result = ContextCompat.checkSelfPermission(getContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_EXTERNAL_STORAGE);


        }
        else {
            pendingWRITE_EXTERNAL_STORAGEpermission=true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {

            pendingWRITE_EXTERNAL_STORAGEpermission=true;
            if(isInternetAvailable(getContext()))
                update();
            else
                Toast.makeText(getContext(),"Network Error"+"\nPlease Check Internet Connectivity!!!",Toast.LENGTH_SHORT).show();
        }
        else
        {

            new AlertDialog.Builder(getContext())
                    .setTitle("Permission required")
                    .setMessage("Grant the permission to continue the process")
                    .setCancelable(true)
                    .setPositiveButton("Grant",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            checkpermission();
                        }
                    })
                    .setNegativeButton("Later",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            daySem fragment = new daySem();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment)
                                    .commit();
                        }
                    })
                    .show();

        }
    }
    void update()
    {
        progress.setTitle("Updating");

        progress.show();
        fileDirExistingList.clear();
        existingFilesList();
        link_list.clear();
        pathList.clear();
        name_list.clear();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        storage = FirebaseStorage.getInstance();
        mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Databases");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {

            }
        }

        mFirebaseDatabase.child("dbFiles").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    String name = noteDataSnapshot.getKey();
                    String link = noteDataSnapshot.getValue(String.class);
                    fireBaseFileNameList.add(name);
                    int i = 0;
                    //Code block to filter files alerady downloaded if not add to download list(link list)
                    for (i = 0; i < fileDirExistingList.size(); i++) {
                        if (fileDirExistingList.get(i).equals(name)) {
                            break;
                        }
                    }
                    if (i == fileDirExistingList.size()) {
                        link_list.add(link);
                        name_list.add(name);
                    }

                }
                //End
                //Deleting files not in firebase but in internal directory
                ArrayList<String> fileDirExistingList_temp = new ArrayList<String>();
                fileDirExistingList_temp = fileDirExistingList;
                fileDirExistingList_temp.removeAll(fireBaseFileNameList);

                if (fileDirExistingList_temp.size() > 0) {
                    for (int i = 0; i < fileDirExistingList_temp.size(); i++) {
                        Log.v("Delete", "" + new File(databasesDir.getPath() + File.separator + fileDirExistingList_temp.get(i)).getName() + "\n");
                        new File(databasesDir.getPath() + File.separator + fileDirExistingList_temp.get(i)).delete();
                    }
                }
                //End
                //refreshing to display list
                existingFilesList();
                count = 0;

                if (link_list.size() > 0) {
                    for (int i = 0; i < link_list.size(); i++) {
                        temp = storage.getReferenceFromUrl(link_list.get(i));
                        File localFile = new File(mediaStorageDir.getPath() + File.separator + name_list.get(i));
                        temp.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                count++;
                                if (count == link_list.size()) {
                                    checkAndCopyDatabase();
                                    progress.dismiss();
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.detach(changeDb.this).attach(changeDb.this).commit();

                                }
                            }
                        });
                        //add downloaded files path
                        pathList.add(localFile.getPath());
                    }
                } else {
                    progress.dismiss();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(changeDb.this).attach(changeDb.this).commit();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progress.dismiss();
                Toast.makeText(getContext(),"Error in updating",Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(changeDb.this).attach(changeDb.this).commit();


            }

        });

    }
    public boolean isInternetAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }
    void existingFilesList()
    {
        File[] filesInDir =databasesDir.listFiles();
        for(int i=0;i<filesInDir.length;i++)
        {
            fileDirExistingList.add(filesInDir[i].getName());

        }
    }
    public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder> {


        private int lastCheckedPosition = 0;



        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.radio_button, null);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.changeDbText.setText(fileDirExistingList.get(position));

            holder.radioButton.setChecked(position == lastCheckedPosition);
        }

        @Override
        public int getItemCount() {
            return fileDirExistingList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            RadioButton radioButton;
            TextView changeDbText;
            LinearLayout ll;
            public ViewHolder(View itemView) {
                super(itemView);
                ll=(LinearLayout) itemView.findViewById(R.id.changeDBLinear);
                radioButton=(RadioButton)itemView.findViewById(R.id.radioButton);
                changeDbText=(TextView)itemView.findViewById(R.id.changeDbText);
                //Added to handle radio button check
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lastCheckedPosition = getAdapterPosition();

                        selected=fileDirExistingList.get(lastCheckedPosition);

                        notifyItemRangeChanged(0, fileDirExistingList.size());

                    }
                });
                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lastCheckedPosition = getAdapterPosition();

                        selected=fileDirExistingList.get(lastCheckedPosition);

                        notifyItemRangeChanged(0, fileDirExistingList.size());
                    }
                });

            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
