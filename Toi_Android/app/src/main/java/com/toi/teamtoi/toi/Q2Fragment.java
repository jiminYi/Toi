package com.toi.teamtoi.toi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Q2Fragment extends Fragment {
    private static final String ARG_QUSETION = "question";
    private String question;

    public Q2Fragment() {

    }

    public static Q2Fragment newInstance(String q) {
        Q2Fragment fragment = new Q2Fragment();
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
        View view = inflater.inflate(R.layout.fragment_q2, container, false);
        if(MainActivity.refreshMenu != null) {
            MainActivity.refreshMenu.setEnabled(false);
            MainActivity.refreshMenu.setVisible(false);
        }
        TextView tvQuestion = (TextView) view.findViewById(R.id.tv_q2);
        ImageView iv1 = (ImageView) view.findViewById(R.id.iv_help2_1);
        TextView ex_iv1 = (TextView) view.findViewById(R.id.tv_answer2_1);
        ImageView iv2 = (ImageView) view.findViewById(R.id.iv_help2_2);
        TextView ex_iv2 = (TextView) view.findViewById(R.id.tv_answer2_2);
        ImageView iv3 = (ImageView) view.findViewById(R.id.iv_help2_3);
        TextView ex_iv3 = (TextView) view.findViewById(R.id.tv_answer2_3);
        ImageView iv4 = (ImageView) view.findViewById(R.id.iv_help2_4);
        TextView ex_iv4 = (TextView) view.findViewById(R.id.tv_answer2_4);

        if(question.equals("q3")) {
            tvQuestion.setText("Q3. 화장실 별칭은 어떻게 정해진것 인가요?");
            iv1.setImageResource(R.drawable.ms_front);
            iv1.getLayoutParams().height = 600;
            ex_iv1.setText(" - 위의 빨간 별표 위치의 화장실은 '명신관앞'이라는 별칭을 갖습니다.");
            iv2.setImageResource(R.drawable.ms_back);
            iv2.getLayoutParams().height = 600;
            ex_iv2.setText(" - 위의 빨간 별표 위치의 화장실은 '명신관뒤'이라는 별칭을 갖습니다.");
            iv3.setImageResource(R.drawable.ms_sh_across);
            iv3.getLayoutParams().height = 600;
            ex_iv3.setText(" - 위의 빨간 별표 위치의 화장실은 '새힘맞은편'이라는 별칭을 갖습니다.");
            iv4.setImageResource(R.drawable.sh);
            iv4.getLayoutParams().height = 600;
            ex_iv4.setText(" - 위의 빨간 별표 위치의 화장실은 '새힘관'이라는 별칭을 갖습니다.");
        } else if(question.equals("q4")) {
            tvQuestion.setText("Q4. 즐겨찾기 기능은 어떻게 사용하나요");
            iv1.setImageResource(R.drawable.q4_1);
            iv1.getLayoutParams().height = 1200;
            ex_iv1.setText(" - 별 모양의 버튼을 누르면 즐겨찾기에 추가됩니다.");
            iv2.setImageResource(R.drawable.q4_2);
            iv2.getLayoutParams().height = 1200;
            ex_iv2.setText(" - 즐겨찾기에 추가되면 별 모양의 버튼의 배경색이 노란색이 됩니다. 즐겨찾기 제거를 원할 경우 노란색 별 버튼은 누르면 됩니다.");
            iv3.setImageResource(R.drawable.q4_3);
            iv3.getLayoutParams().height = 1200;
            ex_iv3.setText(" - 즐겨찾기 리스트는 메뉴에서 즐겨찾기를 누른 후에");
            iv4.setImageResource(R.drawable.q4_4);
            iv4.getLayoutParams().height = 1200;
            ex_iv4.setText(" - 위와 같이 즐겨찾기 리스트를 확인 할 수 있습니다.");
        }
        return view;
    }
}
