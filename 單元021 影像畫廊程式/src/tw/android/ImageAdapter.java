package tw.android;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private Context mContext;		// �x�s�{�����������ҡC
	private Integer[] miImgArr;		// �x�s�v���Y��id�}�C�C

	public ImageAdapter(Context context, Integer[] imgArr) {
		mContext = context;
		miImgArr = imgArr;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return miImgArr.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView v;

		// �p�GconvertView�Onull�A�N�إߤ@�ӷs��ImageView����C
		// �p�GconvertView���Onull�A�N���ƨϥΥ��C
		if (convertView == null) {
			v = new ImageView(mContext);
			v.setLayoutParams(new GridView.LayoutParams(90, 90));
			v.setScaleType(ImageView.ScaleType.CENTER_CROP);
			v.setPadding(10, 10, 10, 10);
		}
		else
			v = (ImageView) convertView;

		// ��n��ܪ��Y�ϩ��ImageView���󤤡C
        v.setImageResource(miImgArr[position]);
        
        return v;
	}

}
