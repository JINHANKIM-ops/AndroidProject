package com.jmh.androidproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link listtab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class listtab2 extends Fragment {

    //---데이터 잘 불러오는지 임시 확인하는 코드---
    String[] names = {"김진한", "정미혜", "정용훈", "박민수", "이상길", "강한솔", "서진현", "아이유", "임창정"};
    String[] reviews = {"저는 클래식 공연을 좋아하는데 공연을 한 눈에 볼 수 있는 앱이 있어서 너무 좋아요 공연 예매시 강추입니다!!",
            " 요즘 시국이 시국인지라ㅠㅠ공연을 보러가기 고민되는 시기인데 라이브로 피아노 독주회를 즐길 수 있음에 감사합니다. 집에서 피아노 독주 라이브를 즐길 수 있어서 무료한 생활에 생기를 불어넣어 주는 것 같았습니다.",
            "너무 좋은 캐스트라서 망설임 없이 예매 했습니다! 좋아하는 배우들이 무대에서 열정적으로 공연하는 모습을 보니 오랜만에 저도 생활에 활기가 생긴 것 같아 너무 좋습니다! 재공, 삼공관람 의사도 최고치 입니다!!!",
            "디즈니 인 콘서트 정말 재밌게 관람 했습니다. 애니메이션이 나오면서 OST를 오케스트라가 연주하는데 정말 가슴이 웅장해지더라구요. 음악을 편하게 감상하며 동심으로 돌아간 기분이들이 들었습니다. 물론 저희 아이들도 무척이나 좋았어요.^^",
            "광화문 연가를 보고 리뷰를 남겨 봅니다. 서울 공연은 끝났지만 온라인으로 다른 지역 공연을 보실 수 있으니 꼭 추천드려요. 일단 여자배우 남자배우 할것 없이 발성이 정말 쩔어요...",
            "기다리고 기다리던 뮤지컬 비틀 쥬스를 봤습니다.  후 훗 장성화  배우님은 저의 최애 배우여서 편애 했을 수 도 있지만 정말 비틀 쥬스 캐릭터 찰떡이였어요.ㅎㅎ 연출이 정말 신기할정도로 대단 했습니다.",
            "마리 앙투아네트를 봤는데요. 이 날의 캐스트에 악셀 폰 페르젠 백작역의 이창섭님이 있었어요! 제가 비투비 노래를 엄청 좋아해요! 그 중에서도 이창섭 님의 목소리를 너무 너무 좋아하는뎅!!! 뮤지컬에서 보니 너무 너무 좋았어요 키키 ",
            "크으 뮤지컬 레드북 가히 최고라 말할 수 있음. 보다가 몇 번이고 벅차오름. 이렇게 재밌는 걸 뮤덕들만 즐기다니... 너무함. 모든 분들의 목소리 하나하나가 아름다웠음. 정말 행복하고 잊지 못할 뮤지컬이었다고 단언 할수있음...",
            " 리그 오브 레전드 라이브 : 디 오케스트라 80인이 넘는 연주자와 가수가 오며 무대를 꽉채웠습니다. 거대한 규모의 공연이 뿜어내는 호흡과 열기를 실로 오랜만에 경험할 수 있었습니다.",
    };

    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public listtab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment listtab2.
     */
    // TODO: Rename and change types and number of parameters
    public static listtab2 newInstance(String param1, String param2) {
        listtab2 fragment = new listtab2();
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
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_listtab2, container, false);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_listtab2,container,false);

        RecyclerView recyclerView = view.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new ReviewAdapter(names, reviews) );

        return view;
    }
}