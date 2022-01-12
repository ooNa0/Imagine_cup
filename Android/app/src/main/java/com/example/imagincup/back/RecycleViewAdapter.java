package com.example.imagincup.back;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imagincup.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.CustomViewHolder> {

    private CardView cardView;
    private Context context;
    private List<Integer> items;
    private int i;

    public RecycleViewAdapter(Context context, List<Integer> items, int i) {
        this.context = context;
        this.items = items;
        this.i = i;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_day_record, parent, false);

        cardView = view.findViewById(R.id.card_view);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Integer item = items.get(position);
        holder.title.setText(item + "");
        holder.content.setText(item + "content");
        if(item == 3){
            cardView.setCardBackgroundColor(Color.parseColor("#CC82DBD7"));
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;

        public CustomViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_item_detail);
            content = itemView.findViewById(R.id.card_item_emotion);
        }
    }

}


//    private final ArrayList<DataModel> dataModelArrayList;
//    private Context context;
//    private OnPostListener onPostListener;
//    String getContentsNum;
//    int pos;
//
//    private final String TAG = "myRecyclerViewAdapter";
//
//    // 생성자: 생성자에서 데이터 리스트 객체를 전달받음.
//    public RecycleViewAdapter(ArrayList<DataModel> dataModelArrayList, Context context) {
//        this.dataModelArrayList = dataModelArrayList;
//        this.context = context;
//        // 어댑터에서 액티비티 액션을 가져올 때 context가 필요한데 어댑터에는 context가 없다.
//        // 선택한 액티비티에 대한 context를 가져올 때 필요하다.
//    }

//    // 뷰홀더
//    // 이 부분에서 super를 통해 상속을 받았다.
//    // 이 RecyclerView 에 뷰 holder 에서 상속을 받아서 거기에 아이템 값을 찾아와야 한다.
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        public TextView tvTitleCV, tvDateCV, tvContentsCV, tvNickNameCV, tvHitCV, tvCommentCount, tvCategoryCV;
//        public ImageView ivImageCV;
//        public ImageButton ibLike;
//
//        // 뷰홀더
//        public MyViewHolder(View view) {
//            super(view);
//            this.ivImageCV = (ImageView) view.findViewById(R.id.ivImageCV);
//            this.tvCategoryCV = (TextView) view.findViewById(R.id.tvCategoryCV);
//            this.tvTitleCV = (TextView) view.findViewById(R.id.tvTitleCV);
//            this.tvContentsCV = (TextView) view.findViewById(R.id.tvContentsCV);
//            this.tvNickNameCV = (TextView) view.findViewById(R.id.tvNickNameCV);
//            this.tvHitCV = (TextView) view.findViewById(R.id.tvHitCV);
//            this.tvCommentCount = (TextView) view.findViewById(R.id.tvCommentCount);
//            this.tvDateCV = (TextView) view.findViewById(R.id.tvDateCV);
//
//            // 리사이클러뷰의 각 아이템을 재사용하는 MyViewHolder 에서 각 아이템에 대한 클릭 리스너를 달 수 있다. : 필요시 사용
//            // MyViewHolder 가 리사이클러뷰의 각 뷰 항목을 만드는 역할을 하기 때문에, 여기서 작업을 해야한다.
//            view.setClickable(true);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    pos = getAdapterPosition();
//
//                    if (pos != RecyclerView.NO_POSITION) {
//
//                        // 데이터 리스트로부터 아이템 데이터 참조.
//                        DataModel item = dataModelArrayList.get(pos);
//
//                        getContentsNum = item.getContentsNum();
//                        String getSection = item.getSection();
//                        String getCategory = item.getCategory();
//                        String getTitle = item.getTitle();
//                        String getNickName = item.getNickName();
//                        String getContents = item.getContents();
//                        Bitmap getImage = item.getImage();
//                        String getDate = item.getDate();
//                        String getEmail = item.getEmail();
//
//                        // 클릭한 리사이클러뷰의 내용을 인텐트에 담아서 이동할액티비티로 간다.
//                        Intent intent = new Intent(context, ContentsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.putExtra("getContentsNum", getContentsNum);
//                        intent.putExtra("getSection", getSection);
//                        intent.putExtra("getCategory", getCategory);
//                        intent.putExtra("getTitle", getTitle);
//                        intent.putExtra("getNickName", getNickName);
//                        intent.putExtra("getContents", getContents);
//                        intent.putExtra("getImage", getImage);
//                        intent.putExtra("getDate", getDate);
//                        intent.putExtra("getEmail", getEmail);
//                        context.startActivity(intent);
//                    }
//                }
//            });
//        }
//    }
//
//    // 리스트 뷰가 어댑터에 연결된 다음 이쪽에서 뷰 홀더를 최초로 만들어 냄.
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d(TAG, "태그 onCreateViewHolder 들어옴");
//
//        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false); //view연결
//        MyViewHolder holder = new MyViewHolder(cardView);
//        return holder;
//
//        // 각각의 아이템을 위한 뷰를 담고있는 뷰홀더 객체를 반환한다.
//        // (각 아이템을 위한 XML 레이아웃을 이용해 뷰 객체를 만들었고 이걸 뷰홀더에서 참조할 수 있도록 위에 만들어 놓음)
//    }
//
//    // 각 아이템들에 대한 실제적인 매칭해주는 곳
//    // onBindViewHolder() - position 서로 결합되는 경우 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
//    // View 의 내용을 해당 포지션의 데이터로 바꿉니다.
//    // 각각의 아이템을 위한 뷰의 xml 레이아웃 호출(즉, 뷰홀더가 각각의 아이템을 위한 뷰를 담아주기 위한 용도인데, 뷰와 아이템이 합쳐질 때 호출)
//    // 적절한 데이터를 가져와서 뷰 소유자의 레이아웃을 채우기 위해서 사용(뷰홀더에 각 아이템의 데이터를 설정해 놓았음.)
//    @Override
//    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.MyViewHolder holder, int position) {
//
//        // 각 위치에 문자열 세팅
//        // 객체가 있는 배열에 담아서 어댑터 쪽으로 쏜다. 그걸 onBindViewHolder 가 받아서 Glide가 load하는 형태이다.
//
//        DataModel dataModelPosition = dataModelArrayList.get(position); // 데이터 리스트 객체에서 어떤거 가져올지 위치로 추출
//        holder.tvCategoryCV.setText(dataModelPosition.getCategory());
//        holder.tvTitleCV.setText(dataModelPosition.getTitle());
//        holder.tvContentsCV.setText(dataModelPosition.getContents());
//        holder.tvNickNameCV.setText(dataModelPosition.getNickName());
//        holder.tvHitCV.setText(dataModelPosition.getHit());
//        holder.tvCommentCount.setText(dataModelPosition.getComment());
//        holder.tvDateCV.setText(dataModelPosition.getDate());
//        holder.tvCommentCount.setText(dataModelPosition.getComment());
//
//        context = holder.itemView.getContext();
//
//        /* 리사이클러뷰의 버튼을 클릭할 때 실행될 것들을 적어준다. */
//        holder.ibLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "리사이클러뷰의 좋아요 버튼이 눌렸습니다.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Glide : 작성하면 이미지 load가 가능해진다. holder시 삽입될 것이다.
//        // override() : 해당 사이즈로 이미지를 늘리거나 줄이는 것.
//        // fitCenter() : 해당 이미지뷰의 크기가 지정되어 있을 경우 이미지 뷰의 크기에 이미지를 맞추는 것
//        Glide.with(context).load(dataModelArrayList.get(position).getImage()).fitCenter().into(holder.ivImageCV);
//    }
//
//    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
//    @Override
//    public int getItemCount() {
//        //  삼항연산자 arrayList 가 null이면 왼쪽꺼 실행 아니면 오른쪽거 실행
//        return (dataModelArrayList != null ? dataModelArrayList.size() : 0);
//    }

//}
