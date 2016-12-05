package com.toi.teamtoi.toi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Q1Fragment extends Fragment {
    private static final String ARG_QUSETION = "question";
    private String question;

    public Q1Fragment() {

    }

    public static Q1Fragment newInstance(String q) {
        Q1Fragment fragment = new Q1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUSETION, q);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            question = getArguments().getString(ARG_QUSETION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_q1, container, false);
        TextView question = (TextView) view.findViewById(R.id.tv_q);
        TextView answer = (TextView) view.findViewById(R.id.tv_answer);
        ImageView image = (ImageView) view.findViewById(R.id.iv_help1);
        if(this.question.equals("q1")) {
            question.setText("Q1. 화장실 위치는 어떻게 찾나요");
            answer.setText("화장실 위치는 리스트에서 화장실을 클릭하면 건물 단면 기준으로, 지도에서 확인하실 수 있습니다.(아래 예시 이미지)");
            image.setImageResource(R.drawable.q1);
            image.getLayoutParams().height = 1200;
        } else if(this.question.equals("q2")) {
            question.setText("Q2. 화장실 리스트의 정보는 무엇을 의미하나요?");
            answer.setText("좌측부터 층, 화장실 별칭, 대기시간, 사용중인 칸수/전체 칸수의 정보를 보여줍니다.");
            image.setImageResource(R.drawable.q2);
            image.getLayoutParams().height = 1800;
        } else if(this.question.equals("q5")) {
            question.setText("Q5. 가까운 화장실 기능은 어떻게 사용하나요?");
            answer.setText("가까운 화장실 기능을 실행하면 비콘을 이용하여 현재 사용자의 위치를 알기 위해 블루투스 권한 요청에 '예'를 눌러주세요. 그리고 현재 위치가 파악되면 현재 위치에서 가까운 화장실의 목록이 나타납니다.");
            image.setImageResource(R.drawable.q5);
        }
        return view;
    }
}
