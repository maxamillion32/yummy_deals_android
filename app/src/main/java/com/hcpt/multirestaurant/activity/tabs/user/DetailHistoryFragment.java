package com.hcpt.multirestaurant.activity.tabs.user;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcpt.multirestaurant.BaseFragment;
import com.hcpt.multirestaurant.R;
import com.hcpt.multirestaurant.activity.tabs.MainUserActivity;
import com.hcpt.multirestaurant.adapter.DetailHistoryAdapter;
import com.hcpt.multirestaurant.config.GlobalValue;
import com.hcpt.multirestaurant.modelmanager.ErrorNetworkHandler;
import com.hcpt.multirestaurant.modelmanager.ModelManager;
import com.hcpt.multirestaurant.modelmanager.ModelManagerListener;
import com.hcpt.multirestaurant.network.ParserUtility;
import com.hcpt.multirestaurant.object.Order;

public class DetailHistoryFragment extends BaseFragment implements
		OnClickListener {
	private View view;
	private ListView lvDetail;
	private ArrayList<Order> list;
	private ImageView imgBack;
	private DetailHistoryAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_detail_history, container,
				false);
		initUI(view);
		initControl();

		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (hidden) {
			// do when hidden
		} else {
			showListDetail();
		}
	}

	private void showListDetail() {
		ModelManager.getListDetailOrder(getCurrentActivity(), GlobalValue.KEY_ORDER_ID, true,
				new ModelManagerListener() {

					@Override
					public void onSuccess(Object object) {
						// TODO Auto-generated method stub
//						Toast.makeText(getCurrentActivity(), "ok" , Toast.LENGTH_LONG)
//						.show();
						list=new ArrayList<Order>();
						String json = (String) object;
						list.addAll(ParserUtility.parseListDetailOrder(json));
						
						adapter = new DetailHistoryAdapter(getCurrentActivity(), list);
						lvDetail.setAdapter(adapter);

					}

					@Override
					public void onError(VolleyError error) {
						// TODO Auto-generated method stub
						Toast.makeText(self, ErrorNetworkHandler.processError(error), Toast.LENGTH_LONG).show();
					}
				});
		// TODO Auto-generated method stub

	}

	private void initControl() {
		// TODO Auto-generated method stub
		imgBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainUserActivity activity = (MainUserActivity) getCurrentActivity();
				activity.backFragment(MainUserActivity.HISTORY_PAGE);
			}
		});
	}

	private void initUI(View view) {
		// TODO Auto-generated method stub

		imgBack = (ImageView) view.findViewById(R.id.btnBack);
		lvDetail = (ListView) view.findViewById(R.id.lvDetailHistory);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
