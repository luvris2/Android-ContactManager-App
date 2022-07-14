package com.luvris2.contactmanagerapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.luvris2.contactmanagerapp.EditContact;
import com.luvris2.contactmanagerapp.MainActivity;
import com.luvris2.contactmanagerapp.R;
import com.luvris2.contactmanagerapp.data.DatabaseHandler;
import com.luvris2.contactmanagerapp.model.Contact;

import java.util.List;

// row 화면에 맵핑 할 어댑터 클래스 생성 순서, 주석의 번호순으로 확인하기
// 1. 어댑터 클래스 생성, RecyclerView.Adapter 상속, 추상 메소드 정의

// 5. 2번에서 생성한 ViewHolder 클래스를 RecyclerViewAdapter<>의 타입으로 설정 -> <ContactAdapter.ViewHolder>
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    // 4. 어댑터 클래스의 멤버변수와 생성자 정의
    Context context;
    List<Contact> contactList;
    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    // 6. RecyclerView.ViewHolder 메소드 구현, 어댑터 클래스 이름으로 변경 -> ContactAdapter.ViewHolder
    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row, parent, false);
        return new ContactAdapter.ViewHolder(view);
    }

    // 메모리에 있는 데이터를 화면에 표시하는 메소드
    // 7. RecyclerView.ViewHolder 어댑터 클래스 이름으로 변경 -> ContactAdapter.ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.txtName.setText(contact.name);
        holder.txtPhone.setText(contact.phone);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    // 2. ViewHolder 클래스 생성, 생성자 정의
    // row.xml 화면에 있는 뷰를 연결 시키는 클래스 생성하기
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtPhone;
        ImageView imgDelete;
        CardView cardView;

        // 3. 생성자에 뷰와 연결시키는 코드 작성
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            cardView = itemView.findViewById(R.id.cardView);

            // 카드뷰 클릭 이벤트
            cardView.setOnClickListener(view -> {
                // 유저가 몇번째 행을 클릭했는지 인덱스 저장
                int index = getAdapterPosition();

                // 인덱스의 저장된 데이터 호출
                Contact contact = contactList.get(index);

                // 수정하는 액티비티로 데이터 전달
                Intent intent = new Intent(context, EditContact.class);
                intent.putExtra("index", contact.id);
                context.startActivity(intent);
            });

            // 삭제모양 이미지 클릭 이벤트
            imgDelete.setOnClickListener(view -> {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("연락처 삭제");
                alert.setMessage("연락처를 삭제하시겠습니까?");

                // 대화상자 '확인 버튼' 이벤트
                alert.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 유저가 몇번째 행을 클릭했는지 인덱스 저장
                        int index = getAdapterPosition();

                        // 인덱스의 저장된 데이터 호출
                        Contact contact = contactList.get(index);

                        DatabaseHandler db = new DatabaseHandler(context);
                        // delete, name&phone dummy
                        db.deleteContact(contact);

                        // adapter에서 activity 리프레시
                        Intent intent = ((Activity)context).getIntent();
                        ((Activity)context).finish(); //현재 액티비티 종료 실시
                        ((Activity)context).overridePendingTransition(0, 0); //효과 없애기
                        ((Activity)context).startActivity(intent); //현재 액티비티 재실행 실시
                        ((Activity)context).overridePendingTransition(0, 0); //효과 없애기
                    }
                });

                // 대화상자 '취소 버튼' 이벤트
                alert.setNegativeButton("취소", null);
                // 대화상자 버튼을 누르지 않으면 화면이 넘어가지 않게 설정
                alert.setCancelable(false);
                // 대화상자 화면에 표시
                alert.show();
            });
        }
    }
}