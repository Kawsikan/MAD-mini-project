package com.example.catalogue;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class DiaryFragment extends Fragment implements diaryAdapter.OnItemClickListener {
    private FloatingActionButton diaryAdd;
    private TextView txt;
    private View v;
    private RecyclerView recyclerView;
    List<Diary> mDiary;
    FirebaseStorage mStorage;
    DatabaseReference ref,mDatabaseRef;
    diaryAdapter mAdapter;


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_diary, container, false);


        recyclerView = (RecyclerView)v.findViewById(R.id.diary_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        mDiary = new ArrayList<>();
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Diary");
        mAdapter = new diaryAdapter(v.getContext(),mDiary);
        recyclerView.setAdapter(mAdapter);


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mDiary.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Diary diary = postSnapshot.getValue(Diary.class);
                    diary.setId(postSnapshot.getKey());
                    mDiary.add(diary);

                }
                mAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(v.getContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        mAdapter.setOnItemClickListener(DiaryFragment.this);










        diaryAdd = v.findViewById(R.id.diaryAddBtn);


        diaryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), diaryAdd.class);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onItemClick(int position) {

        Toast.makeText(v.getContext(), "Open Diary", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(v.getContext(),diaryshow.class);
        Diary selectedDiary = mDiary.get(position);
        String selectedKey =  selectedDiary.getId();
        String title = selectedDiary.getTitle();
        String date = selectedDiary.getDate();
        String time = selectedDiary.getTime();
        String des = selectedDiary.getDescription();


        intent.putExtra("title",title);
        intent.putExtra("date",date);
        intent.putExtra("time",time);
        intent.putExtra("des",des);

        startActivity(intent);

    }

    @Override
    public void onWhateverClick(int position) {

        Intent intent = new Intent(v.getContext(),diaryUpdate.class);
        Diary selectedDiary = mDiary.get(position);
        String selectedKey =  selectedDiary.getId();
        String title = selectedDiary.getTitle();
        String date = selectedDiary.getDate();
        String time = selectedDiary.getTime();
        String des = selectedDiary.getDescription();

        intent.putExtra("key",selectedKey);
        intent.putExtra("title",title);
        intent.putExtra("date",date);
        intent.putExtra("time",time);
        intent.putExtra("des",des);

        startActivity(intent);
        Toast.makeText(v.getContext(), "Edited", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {

        Diary selectedDiary = mDiary.get(position);
        String selectedKey =    selectedDiary.getId();
        mDatabaseRef.child(selectedKey).removeValue();
        Toast.makeText(v.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
    }
}